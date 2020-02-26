package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.component.support.Base64ImageUtil;
import com.otto.sdk.ui.component.support.DownloadImageUtil;

import java.io.File;

import app.beelabs.com.codebase.base.BaseDialog;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PreviewFotoKtpSignUpDialog extends BaseDialog {

    ImageView iv_avatar;

    private UpdateFotoDialog callback;
    private String fileName;

    private PreviewFotoKtpSignUpDialog(@NonNull Context context, String fileName, UpdateFotoDialog callback) {
        super(context, 200);

        this.callback = callback;
        this.fileName = fileName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_preview_foto_ktp);

        iv_avatar =findViewById(R.id.iv_avatar);

        // do something in here
        File fileImage = new File(Environment.getExternalStorageDirectory(), fileName);

        String base64Ktp = Base64ImageUtil.createImageBase64(fileImage);
        CacheUtil.putPreferenceString(IConfig.KEY_BASE64_KTP, base64Ktp, getContext());

        if (fileImage.exists()) {
            new DownloadImageUtil(getContext())
                    .target(iv_avatar)
                    .start(fileImage);
        }

    }


    public static void showPageDialog(Context context, String fileName, UpdateFotoDialog callback) {
        new PreviewFotoKtpSignUpDialog(context, fileName, callback).show();
    }



//    private void onCallApiUpgrade() {
//
//        upgradeAccountRequest = new UpgradeAccountRequest(
//                passpor_photo, number, ktp);
//        showApiProgressDialog(OttoCashSdk.getAppComponent(), new UpgradePresenter(PreviewFotoKtpSignUpDialog.this) {
//            @Override
//            public void call() {
//              getUpgrade (upgradeAccountRequest);
//
//            }
//        }, "Loading");
//
//    }
//
//
//    @Override
//    public void handleUpgrade(UpgradeAccountResponse model) {
//        upgradeAccountResponse = model;
//        if (model.getMeta().getCode() == 200) {
//            Intent intent = new Intent(PreviewFotoKtpSignUpDialog.this, UpgradeSuccessActivity.class);
//            startActivity(intent);
//
//        } else {
//            Toast.makeText(this, model.getMeta().getMessage(),
//                    Toast.LENGTH_LONG).show();
//        }
//    }
}



