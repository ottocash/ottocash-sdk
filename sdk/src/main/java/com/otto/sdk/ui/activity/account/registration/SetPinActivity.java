package com.otto.sdk.ui.activity.account.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.component.support.FormValidation;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class SetPinActivity extends AppActivity {

    EditText edtPin;
    EditText edtConfirmPin;
    Button btnSave;
    ImageView ivBack;

    private boolean isFormValidationSuccess = false;

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

        addTextWatcher(edtPin);
        addTextWatcher(edtConfirmPin);
    }

    private void initContent() {

        btnSave.setOnClickListener(v -> {
            if (isFormValidationSuccess) {
                Intent intent = new Intent(SetPinActivity.this, OtpRegistrationActivity.class);
                CacheUtil.putPreferenceString(IConfig.OC_SESSION_PIN, edtPin.getText().toString(), SetPinActivity.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SetPinActivity.this, "Pin Tidak Sama", Toast.LENGTH_SHORT).show();
            }
        });

        ivBack.setOnClickListener(v -> onBackPressed());
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateForm();
            }

        });
    }
}
