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

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IOtpViewSDK;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.OtpResponseSDK;
import com.otto.sdk.model.api.response.OtpVerificationResponseSDK;
import com.otto.sdk.presenter.OtpPresenterSDK;
import com.otto.sdk.ui.activities.account.activation.ActivationSuccessActivitySDK;
import com.poovam.pinedittextfield.LinePinField;


import cn.iwgang.countdownview.CountdownView;

import static com.otto.sdk.IConfigSDK.SESSION_PHONE;

public class OtpLoginActivitySDK extends BaseActivitySDK implements IOtpViewSDK {

    ImageView ivBack;
    CountdownView countdownView;
    TextView tvResend;
    TextView tvNoHpOtp;
    LinePinField lineField;

    private OtpVerificationRequest modelOtpVerification;
    private OtpRequest modelOtpRequest;
    private boolean isCountdownFinish = false;

    private String phone;
    private OtpPresenterSDK otpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        initComponent();
        setupCountdownview();
        addTextWatcher(lineField);

        phone = CacheUtil.getPreferenceString(String.valueOf(IConfigSDK.SESSION_PHONE), OtpLoginActivitySDK.this);
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

        modelOtpVerification = new OtpVerificationRequest(CacheUtil.getPreferenceInteger(IConfigSDK.SESSION_USER_ID, OtpLoginActivitySDK.this));
        modelOtpVerification.setOtpCode(lineField.getText().toString());

        showApiProgressDialog(OttoCashSDK.getAppComponent(), new OtpPresenterSDK(OtpLoginActivitySDK.this) {
            @Override
            public void call() {
                getOtpVerification(modelOtpVerification);

            }
        }, "Loading");
    }

    private void onCallApiOTPRequest() {
        modelOtpRequest = new OtpRequest(CacheUtil.getPreferenceString(SESSION_PHONE, OtpLoginActivitySDK.this));

        showApiProgressDialog(OttoCashSDK.getAppComponent(), new OtpPresenterSDK(OtpLoginActivitySDK.this) {
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
    public void handleRequestOtp(OtpResponseSDK model) {
    }

    @Override
    public void handleVerificationOtp(OtpVerificationResponseSDK model) {
        if (model.getBaseMeta().getCode() == 200) {
            Intent intent = new Intent(OtpLoginActivitySDK.this, ActivationSuccessActivitySDK.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getBaseMeta().getCode() + ":" + model.getBaseMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}