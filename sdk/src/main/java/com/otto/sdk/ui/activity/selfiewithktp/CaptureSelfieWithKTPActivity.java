package com.otto.sdk.ui.activity.selfiewithktp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.CameraPreview;
import com.otto.sdk.ui.activity.kycupgrade.KTPResultViewActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;

import app.beelabs.com.codebase.base.BaseActivity;


public class CaptureSelfieWithKTPActivity extends BaseActivity {

    FrameLayout cameraPreview;
    private Camera mCamera;
    private CameraPreview mPreview;
    private boolean permissionsGranted = false;
    private Context myContext;
    private ImageView ivCapture, ivFlash, ivback, switchCamera;
    private String ktp, number;
    private boolean isFlashOn = false;
    public static Bitmap bitmap;
    private boolean cameraFront = false;
    private android.hardware.Camera.PictureCallback mPicture;
    public static String base64String;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_with_ktpcamera_kit);
        initpermission();
        myContext = this;
        //ktp = getIntent().getStringExtra("base64");
        Log.i("KTP", "ktp : " + ktp);
        ktp = KTPResultViewActivity.getBase64;
        number = getIntent().getStringExtra("account_number");
        Log.i("ACCOUNT", "Account number : " + number);

        ivback = findViewById(R.id.ivBack);
        cameraPreview = findViewById(R.id.cPreview);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivCapture = findViewById(R.id.btnCam);
        ivCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCamera.takePicture(null, null, getPictureCallback());
            }
        });


        ivFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    isFlashOn = false;
                    onFlashOff();
                } else {
                    isFlashOn = true;
                    onFlashOn();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCamera = getCameraInstance();
    }
    private void initpermission() {
        Dexter.withActivity(this).
                withPermissions(Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            initView();
                        }


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }


                }).onSameThread().check();

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


    private void initView() {
        myContext = this;

        boolean hasFrontCamera = getApplication().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
        mCamera = getCameraInstance();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //mCamera = Camera.open();
        //requestAppPermissions();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    mCamera = Camera.open(camIdx);
                    mCamera.setDisplayOrientation(90);

                } catch (RuntimeException e) {
                    Log.e("", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        cameraPreview = (FrameLayout) findViewById(R.id.cPreview);
        mPreview = new CameraPreview(myContext, mCamera, hasFrontCamera);
        cameraPreview.addView(mPreview);
        cameraPreview.setBackgroundResource(R.drawable.background_selfie2);

        ivFlash = findViewById(R.id.iv_flash);
        ivFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isFlashOn) {
                    isFlashOn = false;
                    onFlashOff();
                } else {
                    isFlashOn = true;
                    onFlashOn();
                }

            }


        });


        ivCapture = (ImageView) findViewById(R.id.btnCam);
        ivCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mCamera.takePicture(null, null, mPicture);
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }

            }
        });

        switchCamera = (ImageView) findViewById(R.id.btnSwitch);
        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the number of cameras
                int camerasNumber = Camera.getNumberOfCameras();
                if (camerasNumber > 1) {
                    //release the old camera instance
                    //switch camera, from the front and the back and vice versa

                    releaseCamera();
                    chooseCamera();
                } else {

                }
            }
        });

        mCamera.startPreview();


    }


    private int findFrontFacingCamera() {

        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;

    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;

            }

        }
        return cameraId;
    }

    public void onResume() {

        super.onResume();
        int cameraId = findFrontFacingCamera();

        if (mCamera == null) {
            /*mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);*/

            if (cameraId >= 0) {
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
            Log.d("nu", "null");
        } else {
            Log.d("nu", "no null");
        }

    }


    public void chooseCamera() {
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                getBase64String(bitmap);

                Log.i("BASE64", "ini base : " + getBase64String(bitmap));
                Log.i("BITMAP", "itu bitmap" + bitmap);
                Intent intent = new Intent(CaptureSelfieWithKTPActivity.this, ResultSelfieWithKtpActivity.class);
                //intent.putExtra("base64selfie", getBase64String(bitmap));
                //intent.putExtra("base64", ktp);
                intent.putExtra("account_number", number);

                startActivity(intent);
            }
        };
        return picture;
    }

    private String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        byte[] imageBytes = baos.toByteArray();

         base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }

    private void onFlashOff() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(params);

    }

    private void onFlashOn() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(params);
        mCamera.startPreview();
    }


}
