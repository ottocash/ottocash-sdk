package com.otto.sdk.ui.activity.kycupgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.component.support.DateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class TakeFotoKtpActivity extends AppActivity {

    CameraKitView cameraKitView;
    ImageView imgCamera;

    private boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_foto_ktp);

        cameraKitView.setFacing(CameraKit.FACING_BACK);
        initContentUI();
    }


    private void initContentUI(){

        cameraKitView = findViewById(R.id.cameraKitView);
        imgCamera = findViewById(R.id.imgCamera);


        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        //Log.e(TAG, e.getMessage());

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
                                        PreviewFotoKtpSignUpDialog.showPageDialog(TakeFotoKtpActivity.this, fileName, data -> actionFinish(fileName))
                                , 500);
                    });
                });
            }
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




}
