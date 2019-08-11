package com.otto.sdk.ui.activities.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivity;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IOtpView;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.OtpResponse;
import com.otto.sdk.model.api.response.OtpVerificationResponse;
import com.otto.sdk.presenter.OtpPresenter;
import com.otto.sdk.ui.activities.account.activation.ActivationSuccessActivity;
import com.poovam.pinedittextfield.LinePinField;


import cn.iwgang.countdownview.CountdownView;

import static com.otto.sdk.IConfig.SESSION_PHONE;

public class OtpLoginActivity extends BaseActivity implements IOtpView {

    ImageView ivBack;
    CountdownView countdownView;
    TextView tvResend;
    TextView tvNoHpOtp;
    LinePinField lineField;

    private OtpVerificationRequest modelOtpVerification;
    private OtpRequest modelOtpRequest;
    private boolean isCountdownFinish = false;

    private String phone;
    private OtpPresenter otpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        initComponent();
        setupCountdownview();
        addTextWatcher(lineField);

        phone = CacheUtil.getPreferenceString(String.valueOf(IConfig.SESSION_PHONE), OtpLoginActivity.this);
        tvNoHpOtp.setText("6 Digit kode OTP telah dikirimkan ke nomor " + phone + ", Silahkan cek HP Anda");
    }


    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        countdownView = findViewById(R.id.countdown_view);
        tvResend = findViewById(R.id.tvResend);
        lineField = findViewById(R.id.lineField);
        tvNoHpOtp = findViewById(R.id.tvNoHpOtp);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setupCountdownview() {
        int otpDuration = 150000;
        countdownView.start(otpDuration);
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                isCountdownFinish = true;
                updateCountdownview();
            }
        });
    }

    private void updateCountdownview() {
        countdownView.setVisibility(View.GONE);
        countdownView.setVisibility(View.VISIBLE);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCountdownview();
                onCallApiOTPRequest();
            }
        });
    }


    private void onCallCallVerificationOTP(final String otp) {

        modelOtpVerification = new OtpVerificationRequest(CacheUtil.getPreferenceInteger(IConfig.SESSION_USER_ID, OtpLoginActivity.this));
        modelOtpVerification.setOtpCode(lineField.getText().toString());

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new OtpPresenter(OtpLoginActivity.this) {
            @Override
            public void call() {
                getOtpVerification(modelOtpVerification);

            }
        }, "Loading");
    }

    private void onCallApiOTPRequest() {
        modelOtpRequest = new OtpRequest(CacheUtil.getPreferenceString(SESSION_PHONE, OtpLoginActivity.this));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new OtpPresenter(OtpLoginActivity.this) {
            @Override
            public void call() {
                getOtpRequest(modelOtpRequest);

            }
        }, "Loading");
    }


    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    onCallCallVerificationOTP(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    @Override
    public void handleRequestOtp(OtpResponse model) {
    }

    @Override
    public void handleVerificationOtp(OtpVerificationResponse model) {
        if (model.getBaseMeta().getCode() == 200) {
            Intent intent = new Intent(OtpLoginActivity.this, ActivationSuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getBaseMeta().getCode() + ":" + model.getBaseMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}