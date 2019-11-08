package com.otto.sdk.ui.activity.selfiewithktp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.otto.sdk.Flag;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.kycupgrade.CameraComponent;
import com.otto.sdk.ui.activity.kycupgrade.PhotoHandler;

import java.io.File;

import app.beelabs.com.codebase.base.BaseActivity;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;


public class CaptureSelfieWithKTPActivity extends BaseActivity implements PhotoHandler.PhotoHandlerCallback {

    FrameLayout previewContainer;
    private Bundle bundle;
    private Camera camera;
    private CameraComponent cameraPreview;
    private boolean permissionsGranted = false;
    private Context myContext;
    private ImageView btnCam, ivFlash, ivback;
    private String ktp, number;
    private boolean isFlashOn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_with_ktpcamera_kit);
        initView();
        myContext = this;
        previewContainer = findViewById(R.id.cPreview);
        btnCam = findViewById(R.id.btnCam);
        ivFlash = findViewById(R.id.iv_flash);
        ivback = findViewById(R.id.ivBack);
        ktp = getIntent().getStringExtra(Flag.ID_CARD);
        number = getIntent().getStringExtra(Flag.ACCOUNT_NUMBER);


        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                Intent intent = new Intent(CaptureSelfieWithKTPActivity.this, ResultSelfieWithKtpActivity.class);
                intent.putExtra(Flag.ID_CARD, ktp);
                intent.putExtra(Flag.ACCOUNT_NUMBER, number);
                startActivity(intent);
            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        ivFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    isFlashOn = false;
                    onFlashOff();
                }else {
                    isFlashOn = true;
                    onFlashOn();
                }


            }
        });

    }

    private void onFlashOff() {
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
//        camera.stopPreview();
//        camera.release();

    }

    private void onFlashOn() {
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }




    private void initView() {

        try {
            initializeCamera();
        } catch (Exception e) {
            Log.e("PHOTO ERROR:", e.getMessage());
        }
    }

    @Override
    public void getData(Intent data) {

    }

    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadCamera();
    }

    private void initializeCamera() throws Exception {
        Nammu.askForPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new PermissionCallback() {
                    @Override
                    public void permissionGranted() {
                        permissionsGranted = true;
                        camera = getCameraInstance();
                        if (!isDeviceSupportCamera() || camera == null)
                            onBackPressed();
                        boolean hasFrontCamera = myContext.getPackageManager()
                                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
                        cameraPreview = new CameraComponent(myContext, camera, hasFrontCamera);

                        previewContainer.addView(cameraPreview);
                        previewContainer.setBackgroundResource(R.drawable.ic_selfie);

//                        DisplayMetrics metrics = new DisplayMetrics();
//                        .getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    }

                    @Override
                    public void permissionRefused() {
                        Toast.makeText(myContext, "Not allowed to use camera", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }

    private boolean isDeviceSupportCamera() {
        return this.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    private void reloadCamera() {
        if (!permissionsGranted) {
            try {
                initializeCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (camera == null) {
            camera = getCameraInstance();
        }
        if (camera != null && cameraPreview == null) {
            boolean hasFrontCamera = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
            cameraPreview = new CameraComponent(this, camera, hasFrontCamera);
            previewContainer.addView(cameraPreview);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        if (cameraPreview != null) {
            previewContainer.removeView(cameraPreview);
            cameraPreview = null;
        }
    }

    private void captureImage() {
        String rootPath = Environment.getExternalStorageDirectory() + File.separator
                + getResources().getString(R.string.app_name);
        String photoDir = "photos";
        String pictureDirPath = rootPath + File.separator + photoDir;
        final File pictureDir = new File(pictureDirPath);
        pictureDir.mkdirs();
        if (!pictureDir.exists() && !pictureDir.mkdirs()) {
            Log.d("", "Can't create directory to save image.");
            return;
        }

        boolean hasAutoFocus = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);

        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            hasAutoFocus = false;
        }

        if (hasAutoFocus) {
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    camera.takePicture(null, null, new PhotoHandler(pictureDir, IConfig.CAMERA_KTP_SELFIE_TYPE,
                            Camera.CameraInfo.CAMERA_FACING_FRONT, CaptureSelfieWithKTPActivity.this));
                    camera.setPreviewCallback(null);
                }
            });
        } else {
            camera.takePicture(null, null, new PhotoHandler(pictureDir, IConfig.CAMERA_KTP_SELFIE_TYPE,
                    Camera.CameraInfo.CAMERA_FACING_FRONT, this));
            camera.setPreviewCallback(null);
        }
    }

}
