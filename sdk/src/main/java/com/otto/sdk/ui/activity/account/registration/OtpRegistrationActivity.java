package com.otto.sdk.ui.activity.account.registration;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IOtpView;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.OtpResponse;
import com.otto.sdk.model.api.response.OtpVerificationResponse;
import com.otto.sdk.presenter.OtpPresenter;
import com.otto.sdk.ui.component.support.UiUtil;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;
import cn.iwgang.countdownview.CountdownView;

import static com.otto.sdk.IConfig.SESSION_PHONE;

public class OtpRegistrationActivity extends BaseActivity implements IOtpView {

    ImageView ivBack;
    CountdownView countdownView;
    TextView tvResend;
    TextView tvNoHpOtp;
    LinePinField lineField;

    private OtpVerificationRequest model;
    private OtpRequest modelOtpRequest;
    private boolean isCountdownFinish = false;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initComponent();
        setupCountdownview();
        addTextWatcher(lineField);

        phone = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, OtpRegistrationActivity.this);
        tvNoHpOtp.setText("6 Digit kode OTP telah dikirimkan ke nomor " + phone + ", Silahkan cek HP Anda");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        countdownView = findViewById(R.id.countdown_view);
        tvResend = findViewById(R.id.tvResend);
        lineField = findViewById(R.id.lineField);
        tvNoHpOtp = findViewById(R.id.tvNoHpOtp);
    }


    private void setupCountdownview() {
        int otpDuration = 180000;
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
        initEnableClickResendOtp();

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDisableClickResendOtp();
                setupCountdownview();
                onCallApiOTPRequest();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initEnableClickResendOtp() {
        tvResend.setEnabled(true);
        tvResend.setText(UiUtil.getHTMLContent(getString(R.string.resend_otp)));
    }

    private void initDisableClickResendOtp() {
        tvResend.setEnabled(false);
        tvResend.setText(UiUtil.getHTMLContent(getString(R.string.describe_qa_otp)));
    }


    private void onCallApiOTP(final String otp) {

        model = new OtpVerificationRequest(CacheUtil.getPreferenceInteger(IConfig.SESSION_USER_ID, OtpRegistrationActivity.this));
        model.setOtpCode(lineField.getText().toString());

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new OtpPresenter(this) {
            @Override
            public void call() {
                getOtpVerification(model);

            }
        }, "Loading");
    }

    private void onCallApiOTPRequest() {
        modelOtpRequest = new OtpRequest(CacheUtil.getPreferenceString(SESSION_PHONE, OtpRegistrationActivity.this));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new OtpPresenter(OtpRegistrationActivity.this) {
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
                    onCallApiOTP(charSequence.toString());
                    hideSoftKeyboard(lineField);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void handleRequestOtp(OtpResponse model) {

    }

    @Override
    public void handleVerificationOtp(OtpVerificationResponse model) {
        if (model.getBaseMeta().getCode() == 200) {
            Intent intent = new Intent(OtpRegistrationActivity.this, RegistrationSuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            saveSession();
        } else {
            Toast.makeText(this, model.getBaseMeta().getCode() + ":" + model.getBaseMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void saveSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("dataSesi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session", "aktivasi");
        editor.apply();
    }
}