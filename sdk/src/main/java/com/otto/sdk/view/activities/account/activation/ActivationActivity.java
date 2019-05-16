package com.otto.sdk.view.activities.account.activation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.view.activities.Util;
import com.otto.sdk.view.activities.account.PinLoginActivity;
import com.otto.sdk.view.activities.account.registration.RegistrationActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class ActivationActivity extends BaseActivity implements View.OnClickListener {

    CheckBox cboxPrivation;
    AppCompatTextView tvTac;
    Button btnBottom;
    TextView tvDesc;
    ImageView ivBack;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        initComponent();
        initContent();
    }

    private void initComponent() {
        cboxPrivation = findViewById(R.id.cboxPrivation);
        tvTac = findViewById(R.id.tvTac);
        btnBottom = findViewById(R.id.btnBottom);
        tvTac.setText(Util.getHTMLContent(getString(R.string.sign_up_label_tac_link)));
        cboxPrivation.setOnClickListener(this);
        tvDesc = findViewById(R.id.tvDesc);
        ivBack = findViewById(R.id.ivBack);
    }

    private void initContent() {

        phone = CacheUtil.getPreferenceString(String.valueOf(IConfig.SESSION_PHONE), ActivationActivity.this);
        tvDesc.setText("Aktivasi Akun " + phone + " OttoCash Kamu di MITRAKU ?");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivationActivity.this, PinLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ActivationActivity.this.startActivity(intent);
            }
        });
    }


    private void validateActivate() {
        boolean agreed = cboxPrivation.isChecked();

        if (agreed) {
            btnBottom.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selector));
        } else {
            btnBottom.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selected_bg));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == cboxPrivation.getId()) {
            validateActivate();
        }

    }


}
