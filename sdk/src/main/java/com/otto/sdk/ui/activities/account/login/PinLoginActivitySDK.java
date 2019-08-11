package com.otto.sdk.ui.activities.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IAuthViewSDK;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.response.LoginResponseSDK;
import com.otto.sdk.model.api.response.RegisterResponseSDK;
import com.otto.sdk.presenter.AuthPresenterSDK;
import com.otto.sdk.presenter.manager.SessionManager;
import com.poovam.pinedittextfield.LinePinField;


public class PinLoginActivitySDK extends BaseActivitySDK implements IAuthViewSDK {

    LinePinField lineField;
    private LoginRequest model;

    private String phone;

    private AuthPresenterSDK authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);

        initComponent();
        addTextWatcher(lineField);
    }

    private void initComponent() {
        lineField = findViewById(R.id.lineField);
    }

    private void onCallApiSetPin(final String pin) {
        phone = CacheUtil.getPreferenceString(IConfigSDK.SESSION_PHONE, PinLoginActivitySDK.this);
        model = new LoginRequest(String.valueOf(CacheUtil.getPreferenceString(
                IConfigSDK.SESSION_PHONE, this)), pin);
        model.setPhone(phone);
        model.setPin(pin);


        showApiProgressDialog(OttoCashSDK.getAppComponent(), new AuthPresenterSDK(PinLoginActivitySDK.this) {
            @Override
            public void call() {
                getLogin(model);
            }
        }, "Loading");
    }


    @Override
    public void handleLogin(LoginResponseSDK data) {
        if (data.getMeta().getCode() == 200) {
            SessionManager.putSessionLogin(true, PinLoginActivitySDK.this);

            int user_id = data.getData().getId();
            String account_number = data.getData().getAccountNumber();
            String name = data.getData().getName();
            String phone = data.getData().getPhone();

            boolean isLogin = data.getMeta().isStatus();
            CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfigSDK.SESSION_IS_LOGIN)),
                    isLogin, PinLoginActivitySDK.this);

            CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_USER_ID, user_id, PinLoginActivitySDK.this);
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_ACCOUNT_NUMBER, account_number, PinLoginActivitySDK.this);
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_NAME, name, PinLoginActivitySDK.this);
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_PHONE, phone, PinLoginActivitySDK.this);

            Intent intent = new Intent(PinLoginActivitySDK.this, OtpLoginActivitySDK.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(IConfigSDK.SESSION_PHONE, phone);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void handleRegister(RegisterResponseSDK model) {

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