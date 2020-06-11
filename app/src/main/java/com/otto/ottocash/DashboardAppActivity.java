package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCash;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.interfaces.ISdkView;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.presenter.SdkResourcePresenter;
import com.otto.sdk.ui.activity.SdkActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardAppActivity extends SdkActivity implements ISdkView {

    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;
    @BindView(R.id.lyWidgetSdk)
    LinearLayout lyWidgetSdk;
    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;
    @BindView(R.id.btnClearCache)
    Button btnClearCache;

//    @BindView(R.id.btnUpgrade)
//    Button btnUpgrade;

    private String account_number;
    String saldo_ottocash;
    String isEmpty = "";
    boolean mDoubleBackToExitPressedOnce = false;
    boolean checkIsExistingPhoneNumber = false;

    private Boolean session_active = false;
    private Boolean session_need_otp = false;
    private Boolean sessionLogin = false;
    private SdkResourcePresenter sdkResourcePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_app);
        ButterKnife.bind(this);

        onCreateToken();
        onGetSaldoOttoCash();
    }


    @Override
    protected void onResume() {
        onCreateToken();
        onGetSaldoOttoCash();
        super.onResume();
    }


    private void onGetSaldoOttoCash() {
        saldo_ottocash = OttoCash.getBalance(this);
        if (saldo_ottocash.equals(isEmpty)) {
            tvSaldoOttoCash.setText("Aktivasi Akun");
        } else {
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDRString(saldo_ottocash));
        }
    }


    /**
     * CHECK PHONE NUMBER IS EXISTING IN OTTOCASH
     */
    public void onCheckPhoneNumber() {

        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
        account_number = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, DashboardAppActivity.this);
        model.setPhone(account_number);

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new SdkResourcePresenter(DashboardAppActivity.this) {
            @Override
            public void call() {
                getCheckPhone(model);

            }
        }, "Loading");
    }


    /**
     * CCREATE TOKEN
     */
    public void onCreateToken() {

        String client_id = CacheUtil.getPreferenceString(IConfig.OC_SESSION_CLIENT_ID, DashboardAppActivity.this);
        String client_secret = CacheUtil.getPreferenceString(IConfig.OC_SESSION_CLIENT_SECRET, DashboardAppActivity.this);

        final CreateTokenRequest token = new CreateTokenRequest();
        token.setGrant_type("client_credentials");
        token.setClient_id(client_id);
        token.setClient_secret(client_secret);

        sdkResourcePresenter = ((SdkResourcePresenter) BasePresenter.getInstance(DashboardAppActivity.this, new SdkResourcePresenter(DashboardAppActivity.this)));
        sdkResourcePresenter.doCreateToken(token);
    }


    /**
     * RESPONSE REQUEST FROM CALL API CHECK PHONE NUMBER
     */
    @Override
    public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
        if (model.getMeta().getCode() == 200) {

            checkIsExistingPhoneNumber = model.getData().isIs_existing();
            sessionLogin = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, DashboardAppActivity.this);
            session_active = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_IS_ACTIVE, DashboardAppActivity.this);
            session_need_otp = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_NEED_OTP, DashboardAppActivity.this);


            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkIsExistingPhoneNumber) {
                        if (sessionLogin && session_active) {
                            goDashboardSDK();
                        } else {
                            goActivation();
                        }
                    } else {
                        goRegistration();
                    }
                }
            });

        }
    }


    /**
     * RESPONSE REQUEST FROM CALL API CREATE TOKEN
     */
    @Override
    public void handleToken(CreateTokenResponse model) {
        if (model.getMeta().getCode() == 200) {

            String accessToken = model.getData().getClient().getAccessToken();
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, accessToken, DashboardAppActivity.this);

            onCheckPhoneNumber();
        } else {
            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.btnCheckOut)
    public void onCheckOut() {

        if (sessionLogin && session_active) {
            startActivity(new Intent(this, CheckOutActivity.class));
        } else {
            Toast.makeText(DashboardAppActivity.this, "Login terlebih dahulu...", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(DashboardAppActivity.this, "Pembayaran OttoCash", Toast.LENGTH_SHORT).show();
        //OttoCash.onCallPayment(this, account_number, 1);
    }

    @OnClick(R.id.btnClearCache)
    public void onClearCache() {
        CacheUtil.putPreferenceBoolean(String.valueOf(IConfig.OC_SESSION_CHECK_PHONE_NUMBER), false, DashboardAppActivity.this);
        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, false, DashboardAppActivity.this);
        CacheUtil.clearPreference(this);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

//    @OnClick(R.id.btnUpgrade)
//    public void onUpgrade() {
//        startActivity(new Intent(this, TopUpActivity.class));
//        //Toast.makeText(DashboardAppActivity.this, "Pembayaran OttoCash", Toast.LENGTH_SHORT).show();
//        //OttoCash.onCallPayment(this, account_number, 1);
//    }


    @Override
    public void onBackPressed() {
        doubleBackToExit();
    }

    private void doubleBackToExit() {
        if (mDoubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.mDoubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.main_activity_msg_double_back_to_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> mDoubleBackToExitPressedOnce = false, 2000);
    }

}

