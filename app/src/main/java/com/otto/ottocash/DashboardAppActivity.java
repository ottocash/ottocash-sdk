package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.presenter.manager.SessionManager;
import com.otto.sdk.support.UiUtil;
import com.otto.sdk.ui.activities.SdkActivitySDK;

import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardAppActivity extends SdkActivitySDK {

    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;
    @BindView(R.id.lyWidgetSdk)
    LinearLayout lyWidgetSdk;
    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;
    @BindView(R.id.btnClearCache)
    Button btnClearCache;

    public static String PackageName;
    private final String KEY_EMONEY_BALANCE = "EMONEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_app);
        ButterKnife.bind(this);

        PackageName = (this.getPackageName() + ".DashboardAppActivity");
        CacheUtil.putPreferenceString(IConfigSDK.SESSION_PACKAGE_NAME, PackageName, DashboardAppActivity.this);
        setupAccount();
        EmoneyBalanceWidget();
        initializeSDK();
    }

    private void EmoneyBalanceWidget() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            String emoney = extras.getString(KEY_EMONEY_BALANCE);
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(emoney)));
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goDashboardSDK();
                }
            });
        }
    }

    private void setupAccount() {
        boolean hasPhoneNumber = Boolean.parseBoolean(String.valueOf(CacheUtil.getPreferenceBoolean(String.valueOf(
                IConfigSDK.SESSION_CHECK_PHONE_NUMBER), DashboardAppActivity.this)));

        if (hasPhoneNumber) {
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goActivation();
                }
            });
        } else {
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goRegistration();
                }
            });
        }
    }


    @OnClick(R.id.btnCheckOut)
    public void onCheckOut() {
        Intent intent = new Intent(DashboardAppActivity.this, CheckOutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.btnClearCache)
    public void onClearCache() {
        SessionManager.getSessionLogin(false, DashboardAppActivity.this);
        Intent intent = new Intent(DashboardAppActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        DashboardAppActivity.this.startActivity(intent);
        finish();
    }
}
