package com.otto.sdk.ui.activity.kycupgrade.takeFotoKtp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.component.support.Base64ImageUtil;
import com.otto.sdk.ui.component.support.DateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import app.beelabs.com.codebase.support.util.CacheUtil;


public class TakeFotoKtpActivity extends AppActivity {

    private String TAG = TakeFotoKtpActivity.class.getSimpleName();
    private boolean isSuccess = false;

    CameraKitView cameraKitView;
    ImageView imgCamera;
    ImageView ivBack;
    ImageView imgFlash;
    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_foto_ktp);


        initContentUI();

        initPermissionMultiple(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        });

        cameraKitView.setFacing(CameraKit.FACING_BACK);

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
            showProgress(TakeFotoKtpActivity.this);
            cameraKitView.captureImage((cameraKitView, bytes) -> {

                // process save file
                fileName = IConfig.FILE_NAME_FOTO_KTP + DateUtil.getTimestamp() + IConfig.EXTENSION_FILE_FOTO;
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
                        Toast.makeText(TakeFotoKtpActivity.this, "Failed to create file on storage...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    actionFinish();
                });
            });

        });

    }

    private void actionFinish() {
        // do something in here
        File fileImage = new File(Environment.getExternalStorageDirectory(), fileName);


        String base64Ktp = Base64ImageUtil.createImageBase64(fileImage);
        CacheUtil.putPreferenceString(IConfig.KEY_BASE64_KTP, base64Ktp, this);

        Intent intent = new Intent(this, PreviewFotoKtpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //finish();
    }

}