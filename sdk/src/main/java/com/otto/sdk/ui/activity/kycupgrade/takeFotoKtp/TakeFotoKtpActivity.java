package com.otto.sdk.ui.activity.kycupgrade.takeFotoKtp;

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
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.component.support.DateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class TakeFotoKtpActivity extends AppActivity {

    private String TAG = TakeFotoKtpActivity.class.getSimpleName();
    private boolean isSuccess = false;

    CameraKitView cameraKitView;
    ImageView imgCamera;
    ImageView ivBack;
    ImageView imgFlash;


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
                String fileName = IConfig.FILE_NAME_FOTO_KTP + DateUtil.getTimestamp() + IConfig.EXTENSION_FILE_FOTO;
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

                    new Handler().postDelayed(() ->
                                    PreviewFotoKtpDialog.showPageDialog(TakeFotoKtpActivity.this, fileName, data -> actionFinish(fileName))
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
            ((Activity) context).startActivityForResult(new Intent(context, TakeFotoKtpActivity.class), requestCode);
        }
    }

}