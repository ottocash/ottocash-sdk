package com.otto.sdk.ui.activity.account.activation;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
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
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.presenter.manager.SessionManager;
import com.otto.sdk.ui.component.support.DeviceId;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class PinLoginActivity extends AppActivity implements IAuthView {

    ImageView ivBack;
    TextView errorMessage;
    LinePinField lineField;
    String messagePIN;
    private LoginRequest model;

    private String phone;
    private String errorMessagePin = "PIN yang Anda masukkan salah.";
    private AuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);

        getLastKnownLocation();
        initComponent();
        addTextWatcher(lineField);
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        errorMessage = findViewById(R.id.errorMessage);
        lineField = findViewById(R.id.lineField);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    /**
     * Call Api Login
     */
    private void onCallApiLogin() {
        phone = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, PinLoginActivity.this);
        final LoginRequest loginRequest = new LoginRequest();

        loginRequest.setPhone(phone);
        loginRequest.setPin(lineField.getText().toString());
        loginRequest.setLatitude(String.valueOf(getMyLastLocation().getLatitude()));
        loginRequest.setLongitude(String.valueOf(getMyLastLocation().getLongitude()));
        loginRequest.setDevice_id(DeviceId.getDeviceID(this));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthPresenter(PinLoginActivity.this) {
            @Override
            public void call() {
                getLogin(loginRequest);
            }
        }, "Loading");
    }


    /**
     * Handle Response Api Login
     */
    @Override
    public void handleLogin(LoginResponse data) {
        if (data.getMeta().getCode() == 200) {
            SessionManager.putSessionLogin(true, PinLoginActivity.this);

            int user_id = data.getData().getId();
            String account_number = data.getData().getAccountNumber();
            String name = data.getData().getName();
            String phone = data.getData().getPhone();

            boolean need_otp = data.getData().getNeed_otp();

            boolean isLogin = data.getMeta().isStatus();
            CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_IS_LOGIN)),
                    isLogin, PinLoginActivity.this);

            CacheUtil.putPreferenceInteger(IConfig.OC_SESSION_USER_ID, user_id, PinLoginActivity.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCOUNT_NUMBER, account_number, PinLoginActivity.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_NAME, name, PinLoginActivity.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_PHONE, phone, PinLoginActivity.this);

            Intent intent = new Intent(PinLoginActivity.this, OtpLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(IConfig.OC_SESSION_PHONE, phone);
            intent.putExtra(IConfig.OC_NEED_OTP, need_otp);
            startActivity(intent);
            finish();
        } else if (data.getMeta().getCode() == 400) {
            messagePIN = data.getMeta().getMessage();
            if (messagePIN.equals(errorMessagePin)) {
                initErrorInvalid();
            }
        } else {
            Toast.makeText(this, data.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initErrorInvalid() {
        errorMessage.setText(getString(R.string.invalid_pin));
        errorMessage.setTextColor(ContextCompat.getColor(this, R.color.Blue_2E70B1));
    }

    @Override
    public void handleRegister(RegisterResponse model) {

    }

    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    onCallApiLogin();
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

}