package com.otto.sdk.ui.activity.account.activation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.tnc.TncMitraActivity;
import com.otto.sdk.ui.activity.tnc.TncOttoCashActivity;
import com.otto.sdk.ui.component.support.FormValidation;
import com.otto.sdk.ui.component.support.UiUtil;

import java.util.List;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class ActivationActivity extends AppActivity implements View.OnClickListener {

    CheckBox cboxTACOttoCash;
    CheckBox cboxTACMitra;
    AppCompatTextView tvTACOttoCash;
    AppCompatTextView tvTACMitra;
    Button btnAktifkan;
    TextView tvDesc;
    ImageView ivBack;

    private boolean isFormValidationSuccess = false;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        initComponent();
        initContent();

        initPermissionMultiple(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        });
    }

    private void initComponent() {
        cboxTACOttoCash = findViewById(R.id.cboxTACOttocash);
        cboxTACMitra = findViewById(R.id.cboxTACMitra);
        cboxTACOttoCash.setOnClickListener(this);
        cboxTACMitra.setOnClickListener(this);
        tvTACOttoCash = findViewById(R.id.tvTACOttocash);
        tvTACMitra = findViewById(R.id.tvTACMitra);
        btnAktifkan = findViewById(R.id.btnAktifkan);
        tvTACOttoCash.setText(UiUtil.getHTMLContent(getString(R.string.sign_up_label_tac_link)));
        tvTACMitra.setText(UiUtil.getHTMLContent(getString(R.string.sign_up_label_tac_link_mitra)));
        tvDesc = findViewById(R.id.tvDesc);
        ivBack = findViewById(R.id.ivBack);
    }

    private void initContent() {

        phone = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, ActivationActivity.this);
        tvDesc.setText("Aktivasi Akun " + phone + " OttoCash Kamu di MITRAKU ?");

        tvTACOttoCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivationActivity.this, TncOttoCashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        tvTACMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivationActivity.this, TncMitraActivity.class);
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

        btnAktifkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFormValidationSuccess) {
                    Intent intent = new Intent(ActivationActivity.this, PinLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ActivationActivity.this, "Mohon Checklist Box TAC", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        int TACOttoCash = cboxTACOttoCash.getId();
        int TACMitra = cboxTACMitra.getId();
        if (TACOttoCash == cboxTACOttoCash.getId() && TACMitra == cboxTACMitra.getId()) {
            validateCboxTAC();
        }

    }

    private void validateCboxTAC() {
        boolean TACOttoCash = cboxTACOttoCash.isChecked();
        boolean TACMitra = cboxTACMitra.isChecked();

        if (FormValidation.required(phone) && TACOttoCash && TACMitra) {
            isFormValidationSuccess = true;
            btnAktifkan.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selector));
        } else {
            isFormValidationSuccess = false;
            btnAktifkan.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selected_bg));
        }
    }
}
