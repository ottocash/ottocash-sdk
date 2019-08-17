package com.otto.sdk.ui.activities.account.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IAuthView;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.ui.component.support.FormValidation;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static com.otto.sdk.IConfig.SESSION_EMAIL;
import static com.otto.sdk.IConfig.SESSION_NAME;
import static com.otto.sdk.IConfig.SESSION_PHONE;

public class SetPinActivity extends BaseActivity implements IAuthView {

    EditText edtPin;
    EditText edtConfirmPin;
    Button btnSave;
    ImageView ivBack;

    private boolean isFormValidationSuccess = false;
    private RegisterRequest model;
    private AuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        initComponent();
        initContent();
    }

    private void initComponent() {
        edtPin = findViewById(R.id.edtPin);
        edtConfirmPin = findViewById(R.id.edtConfirmPin);
        btnSave = findViewById(R.id.btnSave);
        ivBack = findViewById(R.id.ivBack);

    }

    private void initContent() {
        addTextWatcher(edtPin);
        addTextWatcher(edtConfirmPin);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValidationSuccess) {
                    onCallApiSetPin();
                } else {
                    Toast.makeText(SetPinActivity.this, "Pin Tidak Sama", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onCallApiSetPin() {
        model = new RegisterRequest(
                CacheUtil.getPreferenceString(SESSION_PHONE, SetPinActivity.this),
                CacheUtil.getPreferenceString(SESSION_NAME, SetPinActivity.this),
                CacheUtil.getPreferenceString(SESSION_EMAIL, SetPinActivity.this));

        model.setPin(edtPin.getText().toString());
        model.setSecurityQuestionId("1");
        model.setAnswer("ˆ˜ÎØÅÒÒˆ");
        model.setPlatform("android");


        showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthPresenter(SetPinActivity.this) {
            @Override
            public void call() {
                getRegister(model);

            }
        }, "Loading");
    }


    private void validateForm() {
        String pin = edtPin.getText().toString();
        String pin2 = edtConfirmPin.getText().toString();

        if (FormValidation.required(pin) && FormValidation.validPin(pin)
                && FormValidation.required(pin2) && FormValidation.validPin(pin2) && (pin.equals(pin2))) {
            isFormValidationSuccess = true;
            btnSave.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selector));
        } else {
            isFormValidationSuccess = false;
            btnSave.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selected_bg));
        }
    }

    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateForm();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
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

            CacheUtil.putPreferenceInteger(IConfig.SESSION_USER_ID, user_id, SetPinActivity.this);
            CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, SetPinActivity.this);
            CacheUtil.putPreferenceString(IConfig.SESSION_ACCOUNT_NUMBER, account_number, SetPinActivity.this);
            CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, SetPinActivity.this);
            CacheUtil.putPreferenceString(IConfig.SESSION_EMAIL, email, SetPinActivity.this);


            Intent intent = new Intent(SetPinActivity.this, OtpRegistrationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
