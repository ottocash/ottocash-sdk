package com.otto.sdk.view.activities.account.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.view.activities.Util;
import com.otto.sdk.view.activities.account.formValidation.FormValidation;
import com.otto.sdk.view.activities.features.TermAndConditionActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    ImageView ivBack;
    TextView edtNoHp;
    EditText edtNameKtp;
    EditText edtEmail;
    CheckBox cboxPrivation;
    Button btnBottom;
    AppCompatTextView tvTac;

    private boolean isSignUpFormEnable = true;
    private boolean isFormValidationSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initComponent();
        initContent();
    }


    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        edtNoHp = findViewById(R.id.edtNoHp);
        edtNameKtp = findViewById(R.id.edtNameKtp);
        edtEmail = findViewById(R.id.edtEmail);
        cboxPrivation = findViewById(R.id.cboxPrivation);
        btnBottom = findViewById(R.id.btnBottom);
        tvTac = findViewById(R.id.tvTac);
        tvTac.setText(Util.getHTMLContent(getString(R.string.sign_up_label_tac_link)));

        addTextWatcher(edtNameKtp);
        addTextWatcher(edtEmail);

        cboxPrivation.setOnClickListener(this);
        tvTac.setOnClickListener(this);
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFormValidationSuccess) {
                    String name = edtNameKtp.getText().toString();
                    String email = edtEmail.getText().toString();

                    CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, RegistrationActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_EMAIL, email, RegistrationActivity.this);

                    Intent intent = new Intent(RegistrationActivity.this, SetPinActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Data Anda Belum Benar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvTac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, TermAndConditionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                RegistrationActivity.this.startActivity(intent);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initContent() {
        String phone = CacheUtil.getPreferenceString(String.valueOf(IConfig.SESSION_PHONE), RegistrationActivity.this);
        edtNoHp.setText(phone);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == cboxPrivation.getId()) {
            validateRegisterForm();
        }

    }

    private void validateRegisterForm() {
        String name = edtNameKtp.getText().toString();
        String email = edtEmail.getText().toString();
        boolean agreed = cboxPrivation.isChecked();


        if (FormValidation.required(name) && FormValidation.validName(name)
                && FormValidation.required(email) && FormValidation.validEmail(email) && agreed) {
            isFormValidationSuccess = true;
            btnBottom.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selector));
        } else {
            isFormValidationSuccess = false;
            btnBottom.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selected_bg));
        }
    }


    public void addTextWatcher(final EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isSignUpFormEnable) {
                    validateRegisterForm();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }


}
