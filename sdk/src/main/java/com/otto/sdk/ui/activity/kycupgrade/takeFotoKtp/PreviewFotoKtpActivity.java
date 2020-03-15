package com.otto.sdk.ui.activity.kycupgrade.takeFotoKtp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.kycupgrade.takeFotoSelfie.TakeFotoSelfieActivity;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class PreviewFotoKtpActivity extends AppActivity {

    ImageView iv_avatar;
    ImageView ivBack;
    Button btnSubmit;
    Button btnFotoUlang;
    Button btnBatal;

    private String base64Ktp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_foto_ktp);

        iv_avatar = findViewById(R.id.iv_avatar);
        ivBack = findViewById(R.id.ivBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnFotoUlang = findViewById(R.id.btnFotoUlang);
        btnBatal = findViewById(R.id.btnBatal);


        base64Ktp = CacheUtil.getPreferenceString(IConfig.KEY_BASE64_KTP, this);
        Glide.with(this)
                .load(base64Ktp)
                .centerCrop()
                .into(iv_avatar);


        initAction();
    }

    private void initAction() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnFotoUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(PreviewFotoKtpActivity.this, DashboardSDKActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewFotoKtpActivity.this, TakeFotoSelfieActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PreviewFotoKtpActivity.this, TakeFotoKtpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
