package com.otto.sdk.ui.activities.account.activation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.otto.sdk.interfaces.IAuthView;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.presenter.manager.SessionManager;
import com.otto.sdk.ui.activities.account.registration.OtpRegistrationActivity;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PinLoginActivity extends BaseActivity implements IAuthView {

    ImageView ivBack;
    TextView errorMessage;
    LinePinField lineField;
    private LoginRequest model;

    private String phone;
    private String errorMessagePin = "Invalid Token";
    private String messagePIN;
    private AuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);

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


    private void onCallApiSetPin(final String pin) {
        phone = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, PinLoginActivity.this);
        model = new LoginRequest(String.valueOf(CacheUtil.getPreferenceString(
                IConfig.SESSION_PHONE, this)), pin);
        model.setPhone(phone);
        model.setPin(pin);


        showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthPresenter(PinLoginActivity.this) {
            @Override
            public void call() {
                getLogin(model);
            }
        }, "Loading");
    }


    @Override
    public void handleLogin(LoginResponse data) {
        if (data.getMeta().getCode() == 200) {
            SessionManager.putSessionLogin(true, PinLoginActivity.this);

            int user_id = data.getData().getId();
            String account_number = data.getData().getAccountNumber();
            String name = data.getData().getName();
            String phone = data.getData().getPhone();
            messagePIN = data.getMeta().getMessage();


            boolean isLogin = data.getMeta().isStatus();
            CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_IS_LOGIN)),
                    isLogin, PinLoginActivity.this);

            CacheUtil.putPreferenceInteger(IConfig.SESSION_USER_ID, user_id, PinLoginActivity.this);
            CacheUtil.putPreferenceString(IConfig.SESSION_ACCOUNT_NUMBER, account_number, PinLoginActivity.this);
            CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, PinLoginActivity.this);
            CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, PinLoginActivity.this);

            Intent intent = new Intent(PinLoginActivity.this, OtpLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(IConfig.SESSION_PHONE, phone);
            startActivity(intent);
            finish();
        } else if (messagePIN.equals(errorMessagePin)) {
            initErrorInvalid();
        } else {
            Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    private void initErrorInvalid() {
        errorMessage.setText(getString(R.string.invalid_pin));
        errorMessage.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
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
                    onCallApiSetPin(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }
}