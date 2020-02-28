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
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.interfaces.ISdkView;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.presenter.SdkResourcePresenter;
import com.otto.sdk.ui.activity.SdkActivity;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardAppActivity extends SdkActivity implements ISdkView, IInquiryView {

    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;
    @BindView(R.id.lyWidgetSdk)
    LinearLayout lyWidgetSdk;
    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;
    @BindView(R.id.btnClearCache)
    Button btnClearCache;

    private String account_number;
    private String saldo_ottocash;
    boolean mDoubleBackToExitPressedOnce = false;
    boolean checkIsExistingPhoneNumber = false;

    private Boolean sessionLogin = false;
    private SdkResourcePresenter sdkResourcePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_app);
        ButterKnife.bind(this);

        //checkIsExistingPhoneNumber();
        //onGetSaldoOttoCash();
        saldo_ottocash = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, this);
        if (saldo_ottocash!=null){
            tvSaldoOttoCash.setText(saldo_ottocash);
        }else {
            tvSaldoOttoCash.setText("Aktivasi Akun");
        }

        onCheckPhoneNumber();
    }


    @Override
    protected void onResume() {
        //checkIsExistingPhoneNumber();
        //onGetSaldoOttoCash();
        saldo_ottocash = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, this);

        if (saldo_ottocash!=null){
            tvSaldoOttoCash.setText(saldo_ottocash);
        }else{
            tvSaldoOttoCash.setText("Aktivasi Akun");
        }

        onCheckPhoneNumber();
        super.onResume();
    }


    @Override
    protected void onStart() {
        //checkIsExistingPhoneNumber();
        //onGetSaldoOttoCash();
        saldo_ottocash = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, this);

        if (saldo_ottocash!=null){
            tvSaldoOttoCash.setText(saldo_ottocash);
        }else {
            tvSaldoOttoCash.setText("Aktivasi Akun");
        }


        onCheckPhoneNumber();
        super.onStart();
    }

//    /**
//     * Call Function Check Account
//     */
//    private void onSetupAccountClick() {
//        checkIsExistingPhoneNumber = CacheUtil.getPreferenceBoolean(String.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER),
//                DashboardAppActivity.this);
//
//        if (checkIsExistingPhoneNumber) {
//            lyWidgetSdk.setOnClickListener(v -> goActivation());
//        } else {
//            lyWidgetSdk.setOnClickListener(v -> goRegistration());
//        }
//    }


    /**
     * Call Function Get Saldo
     */
    private void onGetSaldoOttoCash() {
        saldo_ottocash = OttoCash.getBalance(this);
        if (saldo_ottocash != null) {
            tvSaldoOttoCash.setText(saldo_ottocash);
            lyWidgetSdk.setOnClickListener(v -> checkIsExistingPhoneNumber());
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
            onCreateToken();

            checkIsExistingPhoneNumber = model.getData().isIs_existing();
            sessionLogin = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, DashboardAppActivity.this);

            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkIsExistingPhoneNumber) {
                        if (sessionLogin) {
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

        } else {
            //Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
            //        Toast.LENGTH_LONG).show();
        }
    }


//    @Override
//    protected void onResume() {
//        saldo_ottocash = OttoCash.getBalance(this);
//        onSetupAccountClick();
//        onSaldoOttocash();
//        super.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        saldo_ottocash = OttoCash.getBalance(this);
//        onSetupAccountClick();
//        onSaldoOttocash();
//        super.onStart();
//    }
//
//    private void onSaldoOttocash() {
//        if (saldo_ottocash != null) {
//            tvSaldoOttoCash.setText(saldo_ottocash);
//            lyWidgetSdk.setOnClickListener(v -> goDashboardSDK());
//        }else {
//            tvSaldoOttoCash.setText("Aktivasi Akun");
//        }
//    }
//
//    private void onSetupAccountClick() {
//        boolean checkIsExistingPhoneNumber = CacheUtil.getPreferenceBoolean(String.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER),
//                DashboardAppActivity.this);
//
//        if (checkIsExistingPhoneNumber) {
//            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    goActivation();
//                }
//            });
//        } else {
//            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    goRegistration();
//                }
//            });
//        }
//    }


//    @Override
//    protected void onResume() {
//        tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(OttoCash.getBalance(this))));
//        super.onResume();
//    }


//    public void onSaldoOttocash() {
//        tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(OttoCash.getBalance(this))));
//
//        lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OttoCash.onCallOttoCashDashboard(DashboardAppActivity.this,
//                        CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE,
//                                DashboardAppActivity.this));
//            }
//        });
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == OttoCash.REQ_OTTOCASH_PAYMENT) {
//            if (data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA) != null) {
//                PaymentData paymentData = data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA);
//                Toast.makeText(this, paymentData.getReferenceNumber(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    @OnClick(R.id.btnCheckOut)
    public void onCheckOut() {
        startActivity(new Intent(this, CheckOutActivity.class));
        //Toast.makeText(DashboardAppActivity.this, "Pembayaran OttoCash", Toast.LENGTH_SHORT).show();
        //OttoCash.onCallPayment(this, account_number, 1);
    }

    @OnClick(R.id.btnClearCache)
    public void onClearCache() {
        CacheUtil.putPreferenceBoolean(String.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER), false, DashboardAppActivity.this);
        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, false, DashboardAppActivity.this);
        CacheUtil.clearPreference(this);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

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


    @Override
    public void handleInquiry(InquiryResponse model) {

    }
}

