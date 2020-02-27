package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.ui.activity.SdkActivity;

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
    private String saldo_ottocash;
    private boolean mDoubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_app);
        ButterKnife.bind(this);
        
        onSetupAccountClick();
        onGetSaldoOttoCash();
    }


    @Override
    protected void onResume() {
        onSetupAccountClick();
        onGetSaldoOttoCash();
        super.onResume();
    }


    @Override
    protected void onStart() {
        onSetupAccountClick();
        onGetSaldoOttoCash();
        super.onStart();
    }

    /**
     * Call Function Check Account
     */
    private void onSetupAccountClick() {
        boolean checkIsExistingPhoneNumber = CacheUtil.getPreferenceBoolean(String.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER),
                DashboardAppActivity.this);

        if (checkIsExistingPhoneNumber) {
            lyWidgetSdk.setOnClickListener(v -> goActivation());
        } else {
            lyWidgetSdk.setOnClickListener(v -> goRegistration());
        }
    }


    /**
     * Call Function Get Saldo
     */
    private void onGetSaldoOttoCash() {
        saldo_ottocash = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, this);
        if (saldo_ottocash != null) {
            tvSaldoOttoCash.setText(((saldo_ottocash)));
            lyWidgetSdk.setOnClickListener(v -> goDashboardSDK());
        }else {
            tvSaldoOttoCash.setText("Aktivasi Akun");
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

//    @OnClick(R.id.btnCheckOut)
//    public void onCheckOut() {
//        Toast.makeText(DashboardAppActivity.this, "Pembayaran OttoCash", Toast.LENGTH_SHORT).show();
//        OttoCash.onCallPayment(this, account_number, 1);
//    }

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


}

