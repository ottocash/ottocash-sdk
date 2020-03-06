package com.otto.sdk.ui.activity.kycupgrade.takeFotoKtp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.kycupgrade.UpdateFotoDialog;
import com.otto.sdk.ui.activity.kycupgrade.takeFotoSelfie.TakeFotoSelfieActivity;
import com.otto.sdk.ui.component.support.Base64ImageUtil;
import com.otto.sdk.ui.component.support.DownloadImageUtil;

import java.io.File;

import app.beelabs.com.codebase.base.BaseDialog;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PreviewFotoKtpDialog extends BaseDialog{

    ImageView iv_avatar;
    ImageView ivBack;
    Button btnSubmit;
    Button btnFotoUlang;
    Button btnBatal;

    private UpdateFotoDialog callback;
    private String fileName;

    private PreviewFotoKtpDialog(@NonNull Context context, String fileName, UpdateFotoDialog callback) {
        super(context, 200);

        this.callback = callback;
        this.fileName = fileName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_preview_foto_ktp);

        iv_avatar = findViewById(R.id.iv_avatar);
        ivBack = findViewById(R.id.ivBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnFotoUlang = findViewById(R.id.btnFotoUlang);
        btnBatal = findViewById(R.id.btnBatal);

        // do something in here
        File fileImage = new File(Environment.getExternalStorageDirectory(), fileName);


        String base64Ktp = Base64ImageUtil.createImageBase64(fileImage);
        CacheUtil.putPreferenceString(IConfig.KEY_BASE64_KTP, base64Ktp, getContext());

        if (fileImage.exists()) {
            new DownloadImageUtil(getContext())
                    .target(iv_avatar)
                    .start(fileImage);
        }
        initAction();

    }


    public static void showPageDialog(Context context, String fileName, UpdateFotoDialog callback) {
        new PreviewFotoKtpDialog(context, fileName, callback).show();
    }


    private void initAction(){

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
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.updateAction(null);
                Intent intent = new Intent(getContext(), TakeFotoSelfieActivity.class);
                getContext().startActivity(intent);
                dismiss();
                //callback.updateAction(null);
                //Intent intent = new Intent(getContext(), TakeFotoSelfieActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //getContext().startActivity(intent);
            }
        });

    }



}



