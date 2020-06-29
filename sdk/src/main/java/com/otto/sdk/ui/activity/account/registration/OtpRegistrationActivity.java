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

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IAuthView;
import com.otto.sdk.interfaces.IOtpView;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.api.response.RequestOtpResponse;
import com.otto.sdk.model.api.response.VerifyOtpResponse;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.presenter.OtpPresenter;
import com.otto.sdk.ui.component.support.Connectivity;
import com.otto.sdk.ui.component.support.DeviceId;
import com.otto.sdk.ui.component.support.UiUtil;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;
import cn.iwgang.countdownview.CountdownView;

import static com.otto.sdk.IConfig.OC_SESSION_EMAIL;
import static com.otto.sdk.IConfig.OC_SESSION_NAME;
import static com.otto.sdk.IConfig.OC_SESSION_PHONE;

public class OtpRegistrationActivity extends AppActivity implements IOtpView, IAuthView {

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

        getLastKnownLocation();
        initComponent();
        setupCountdownview();
        addTextWatcher(lineField);

        phone = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, OtpRegistrationActivity.this);
        tvNoHpOtp.setText("6 Digit kode OTP telah dikirimkan ke nomor " + phone + ", Silahkan cek HP Anda");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        onCallApiOTPRequest();
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


    /**
     * Call Api Request Otp
     */
    private void onCallApiOTPRequest() {
        String phone = CacheUtil.getPreferenceString(OC_SESSION_PHONE, OtpRegistrationActivity.this);
        final OtpRequest otpRequest = new OtpRequest();
        otpRequest.setPhone(phone);

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new OtpPresenter(OtpRegistrationActivity.this) {
            @Override
            public void call() {
                getOtpRequest(otpRequest);

            }
        }, "Loading");
    }


    /**
     * Call Api Otp Verify
     */
    private void onCallApiOtpVerify() {
        String phone_number = CacheUtil.getPreferenceString(OC_SESSION_PHONE, OtpRegistrationActivity.this);
        final OtpVerificationRequest otpVerificationRequest = new OtpVerificationRequest();

        otpVerificationRequest.setPhone_number(phone_number);
        otpVerificationRequest.setOtp_code(lineField.getText().toString());

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new OtpPresenter(OtpRegistrationActivity.this) {
            @Override
            public void call() {
                getOtpVerification(otpVerificationRequest);
            }
        }, "Loading");
    }


    /**
     * Handle Response Call Request Otp
     */
    @Override
    public void handleOtpRequest(RequestOtpResponse model) {

    }


    /**
     * Handle Response Call Verify Otp
     */
    @Override
    public void handleOtpVerify(VerifyOtpResponse model) {
        if (model.getBaseMeta().getCode() == 200) {
            onCallApiRegistration();
            saveSession();
        } else {
            Toast.makeText(this, model.getBaseMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    onCallApiOtpVerify();
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


    private void saveSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("dataSesi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session", "aktivasi");
        editor.apply();
    }

    @Override
    public BaseActivity getCurrentActivity() {
        return OtpRegistrationActivity.this;
    }


    private void onCallApiRegistration() {
        if (Connectivity.isNetworkAvailable(this)) {
            final RegisterRequest model = new RegisterRequest(
                    CacheUtil.getPreferenceString(OC_SESSION_PHONE, OtpRegistrationActivity.this),
                    CacheUtil.getPreferenceString(OC_SESSION_NAME, OtpRegistrationActivity.this),
                    CacheUtil.getPreferenceString(OC_SESSION_EMAIL, OtpRegistrationActivity.this));

            model.setPin(CacheUtil.getPreferenceString(IConfig.OC_SESSION_PIN, OtpRegistrationActivity.this));
            model.setSecurityQuestionId("1");
            model.setAnswer("ˆ˜ÎØÅÒÒˆ");
            model.setLatitude(String.valueOf(getMyLastLocation().getLatitude()));
            model.setLongitude(String.valueOf(getMyLastLocation().getLongitude()));
            model.setDeviceId(DeviceId.getDeviceID(this));
            model.setPlatform("android");

            showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthPresenter(OtpRegistrationActivity.this) {
                @Override
                public void call() {
                    getRegister(model);

                }
            }, "Loading");
        }
    }

    @Override
    public void handleLogin(LoginResponse model) {

    }

    @Override
    public void handleRegister(RegisterResponse model) {
        if (model.getMeta().getCode() == 200) {

            int user_id = model.getData().getId();
            String phone = model.getData().getPhone();
            String account_number = model.getData().getAccountNumber();
            String name = model.getData().getName();
            String email = model.getData().getEmail();

            CacheUtil.putPreferenceInteger(IConfig.OC_SESSION_USER_ID, user_id, OtpRegistrationActivity.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_PHONE, phone, OtpRegistrationActivity.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCOUNT_NUMBER, account_number, OtpRegistrationActivity.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_NAME, name, OtpRegistrationActivity.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_EMAIL, email, OtpRegistrationActivity.this);

            Intent intent = new Intent(OtpRegistrationActivity.this, RegistrationSuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}