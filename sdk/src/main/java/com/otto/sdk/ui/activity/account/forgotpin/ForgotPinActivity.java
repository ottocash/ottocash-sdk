package com.otto.sdk.ui.activity.account.forgotpin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IForgotPinView;
import com.otto.sdk.model.api.request.ForgotPinRequest;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.presenter.ForgotPinPresenter;
import com.otto.sdk.ui.activity.account.registration.SetPinActivity;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.component.support.Connectivity;
import com.otto.sdk.ui.component.support.FormValidation;

import java.util.Objects;

import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static com.otto.sdk.IConfig.OC_SESSION_PHONE;

public class ForgotPinActivity extends AppActivity implements IForgotPinView {

    EditText edtPin;
    EditText edtConfirmPin;
    Button btnSave;
    ImageView ivBack;

    private boolean isFormValidationSuccess = false;
    private String phone_number;
    private String session_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

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

        //phone_number = CacheUtil.getPreferenceString(OC_SESSION_PHONE, ForgotPinActivity.this);
        session_otp = Objects.requireNonNull(getIntent().getExtras()).getString(IConfig.OC_SESSION_OTP);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValidationSuccess) {
                    onCallApiForgotPin();
                } else {
                    Toast.makeText(ForgotPinActivity.this, "Pin Tidak Sama", Toast.LENGTH_SHORT).show();
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


    private void onCallApiForgotPin() {
        final ForgotPinRequest model = new ForgotPinRequest();

        model.setPhone_number(CacheUtil.getPreferenceString(OC_SESSION_PHONE, ForgotPinActivity.this));
        model.setPin(edtPin.getText().toString());
        model.setOtp(session_otp);

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new ForgotPinPresenter(ForgotPinActivity.this) {
            @Override
            public void call() {
                getForgotPin(model);

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
    public void handleForgotPin(BaseResponse model) {

        if (model.getBaseMeta().getCode() == 200) {
            Intent intent = new Intent(ForgotPinActivity.this, DashboardSDKActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getBaseMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
