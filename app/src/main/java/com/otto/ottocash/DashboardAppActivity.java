package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCash;
import com.otto.sdk.model.api.response.PaymentData;
import com.otto.sdk.ui.activity.SdkActivity;
import com.otto.sdk.ui.activity.kycupgrade.IntroductionUpgradeActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardAppActivity extends SdkActivity {

    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;
    @BindView(R.id.lyWidgetSdk)
    LinearLayout lyWidgetSdk;
    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;
    @BindView(R.id.btnClearCache)
    Button btnClearCache;

    private String account_number;
    private final String KEY_EMONEY_BALANCE = "EMONEY";
    private String saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_app);
        ButterKnife.bind(this);

        onSetupAccountClick();

        if (saldo != null) {
            Log.i("respon", " " + saldo);
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(saldo)));
        }

        onEmoneyBalanceWidget();
        lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OttoCash.onCallOttoCashDashboard(DashboardAppActivity.this,
                        CacheUtil.getPreferenceString(IConfig.SESSION_PHONE,
                                DashboardAppActivity.this));
            }
        });
    }


    public void onEmoneyBalanceWidget() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            String emoney = extras.getString(KEY_EMONEY_BALANCE);
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(emoney)));
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OttoCash.onCallOttoCashDashboard(DashboardAppActivity.this,
                            CacheUtil.getPreferenceString(IConfig.SESSION_PHONE,
                                    DashboardAppActivity.this));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OttoCash.REQ_OTTOCASH_PAYMENT) {
            Intent intent = new Intent();
            if (data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA) != null) {
                PaymentData paymentData = data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA);
                Toast.makeText(this, paymentData.getReferenceNumber(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.btnCheckOut)
    public void onCheckOut() {
        Toast.makeText(DashboardAppActivity.this, "Pembayaran OttoCash", Toast.LENGTH_SHORT).show();
        OttoCash.onCallPayment(this, account_number, 1);
    }

    @OnClick(R.id.btnClearCache)
    public void onClearCache() {
        OttoCash.onLogoutOttoCash(this);
        Intent intent = new Intent(DashboardAppActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.btnUpgrade)
    public void onUpgrade(){
        startActivity(new Intent(this, IntroductionUpgradeActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onSetupAccountClick() {
        boolean checkIsExistingPhoneNumber = Boolean.parseBoolean(String.valueOf(CacheUtil.getPreferenceBoolean(String.valueOf(
                IConfig.SESSION_CHECK_PHONE_NUMBER), DashboardAppActivity.this)));

        if (checkIsExistingPhoneNumber) {
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

}

