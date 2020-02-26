package com.otto.sdk.ui.activity.kycupgrade.takeFotoSelfie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.kycupgrade.takeFotoKtp.PreviewFotoKtpDialog;
import com.otto.sdk.ui.activity.kycupgrade.takeFotoKtp.TakeFotoKtpActivity;
import com.otto.sdk.ui.component.support.DateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import app.beelabs.com.codebase.base.BaseActivity;


public class TakeFotoSelfieActivity extends AppActivity {

    private String TAG = TakeFotoSelfieActivity.class.getSimpleName();
    private boolean isSuccess = false;

    CameraKitView cameraKitView;
    ImageView imgCamera;
    ImageView ivBack;
    ImageView imgFlash;
    ImageView imgSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_foto_selfie);

        initContentUI();
        cameraKitView.setFacing(CameraKit.FACING_FRONT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void initContentUI() {

        cameraKitView = findViewById(R.id.cameraKitView);
        imgCamera = findViewById(R.id.imgCamera);
        ivBack = findViewById(R.id.ivBack);
        imgFlash = findViewById(R.id.imgFlash);
        imgSwitch = findViewById(R.id.imgSwitch);

        ivBack.setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        imgFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraKitView.setFlash(CameraKit.FLASH_ON);
            }
        });


        imgCamera.setOnClickListener(view -> {
            showProgress(TakeFotoSelfieActivity.this);
            cameraKitView.captureImage((cameraKitView, bytes) -> {

                // process save file
                String fileName = IConfig.FILE_NAME_FOTO_SELFIE + DateUtil.getTimestamp() + IConfig.EXTENSION_FILE_FOTO;
                try {
                    // check if folder foto is exist
                    File folderFoto = new File(Environment.getExternalStorageDirectory(), IConfig.FOLDER_FOTO);
                    if (!folderFoto.exists()) folderFoto.mkdirs();

                    // create file
                    File savedPhoto = new File(Environment.getExternalStorageDirectory(), fileName);

                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                    outputStream.write(bytes);
                    outputStream.close();

                    isSuccess = true;
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());

                    isSuccess = false;
                }

                // show message to user if process take image is finish
                runOnUiThread(() -> {
                    closeProgress();

                    if (!isSuccess || fileName.isEmpty()) {
                        Toast.makeText(TakeFotoSelfieActivity.this, "Failed to create file on storage...", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new Handler().postDelayed(() ->
                                    PreviewFotoSelfieDialog.showPageDialog(TakeFotoSelfieActivity.this, fileName, data -> actionFinish(fileName))
                            , 500);
                });
            });

        });

    }

    private void actionFinish(String fileName) {
        Intent intent = new Intent();
        intent.putExtra(IConfig.KEY_DATA, fileName);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    public static void showPageDialog(Context context, int requestCode) {
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(new Intent(context, TakeFotoSelfieActivity.class), requestCode);
        }
    }

}
