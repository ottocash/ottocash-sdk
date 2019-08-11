package com.otto.sdk.ui.activities.account.registration;

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

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.ui.activities.tac.TACMitraActivitySDK;
import com.otto.sdk.ui.component.support.Util;
import com.otto.sdk.ui.activities.account.formValidation.FormValidation;
import com.otto.sdk.ui.activities.tac.TACOttoCashActivitySDK;

public class RegistrationActivitySDK extends BaseActivitySDK implements View.OnClickListener {

    ImageView ivBack;
    TextView edtNoHp;
    EditText edtNameKtp;
    EditText edtEmail;
    CheckBox cbTACOttoCash;
    CheckBox cbTACMitra;
    Button btnBottom;
    AppCompatTextView tvTACOttoCash;
    AppCompatTextView tvTACMitra;

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
        btnBottom = findViewById(R.id.btnBottom);
        cbTACOttoCash = findViewById(R.id.cbTACOttoCash);
        cbTACMitra = findViewById(R.id.cbTACMitra);
        tvTACOttoCash = findViewById(R.id.tvTACOttoCash);
        tvTACMitra = findViewById(R.id.tvTACMitra);
        tvTACOttoCash.setText(Util.getHTMLContent(getString(R.string.sign_up_label_tac_link)));
        tvTACMitra.setText(Util.getHTMLContent(getString(R.string.sign_up_label_tac_link_mitra)));

        addTextWatcher(edtNameKtp);
        addTextWatcher(edtEmail);

        cbTACOttoCash.setOnClickListener(this);
        cbTACMitra.setOnClickListener(this);
        tvTACOttoCash.setOnClickListener(this);
        tvTACMitra.setOnClickListener(this);
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFormValidationSuccess) {
                    String name = edtNameKtp.getText().toString();
                    String email = edtEmail.getText().toString();

                    CacheUtil.putPreferenceString(IConfigSDK.SESSION_NAME, name, RegistrationActivitySDK.this);
                    CacheUtil.putPreferenceString(IConfigSDK.SESSION_EMAIL, email, RegistrationActivitySDK.this);

                    Intent intent = new Intent(RegistrationActivitySDK.this, SetPinActivitySDK.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivitySDK.this, "Data Anda Belum Benar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvTACOttoCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivitySDK.this, TACOttoCashActivitySDK.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        tvTACMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivitySDK.this, TACMitraActivitySDK.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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
        String phone = CacheUtil.getPreferenceString(IConfigSDK.SESSION_PHONE, RegistrationActivitySDK.this);
        edtNoHp.setText(phone);
    }


    @Override
    public void onClick(View v) {
        int TACOttoCash = cbTACOttoCash.getId();
        int TACMitra = cbTACMitra.getId();
        if (TACOttoCash == cbTACOttoCash.getId() && TACMitra == cbTACMitra.getId()) {
            validateRegisterForm();
        }

    }

    private void validateRegisterForm() {
        String name = edtNameKtp.getText().toString();
        String email = edtEmail.getText().toString();
        boolean TACOttoCash = cbTACOttoCash.isChecked();
        boolean TACMitra = cbTACMitra.isChecked();


        if (FormValidation.required(name) && FormValidation.validName(name)
                && FormValidation.required(email) && FormValidation.validEmail(email) && TACOttoCash && TACMitra) {
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
