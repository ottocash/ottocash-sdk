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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.otto.sdk.R;
import com.otto.sdk.ui.activity.kycupgrade.CameraPreview;
import com.otto.sdk.ui.activity.payment.ReviewCheckoutActivity;

import app.beelabs.com.codebase.base.BaseActivity;


public class CaptureSelfieWithKTPActivity extends BaseActivity {

    private int REQUEST_WRITE_STORAGE_REQUEST_CODE = 112;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private ImageView capture, switchCamera, ivback, btnCam;
    private Context myContext;
    private FrameLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;
//    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_with_ktpcamera_kit);
//        initView();

        ivback = findViewById(R.id.ivBack);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCam = findViewById(R.id.btnCam);
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaptureSelfieWithKTPActivity.this, ResultSelfieWithKtpActivity.class);
                startActivity(intent);
            }
        });


    }


/*    private void initView() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestAppPermissions();
        myContext = this;
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
        ivback = findViewById(R.id.ivBack);


        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);
        cameraPreview.setBackgroundResource(R.drawable.ic_selfie);


        capture = (ImageView) findViewById(R.id.btnCam);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
                    mCamera.takePicture(null, null, mPicture);
//                } catch (Exception e) {
//                    Log.e("tag", e.getMessage());
//                }

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
        if (mCamera == null) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
            Log.d("nu", "null");
        } else {
            Log.d("nu", "no null");
        }

    }

    public void chooseCamera() {
        //if the camera preview is the front
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
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
                Intent intent = new Intent(CaptureSelfieWithKTPActivity.this, ResultSelfieWithKtpActivity.class);
                startActivity(intent);
            }
        };
        return picture;
    }

    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions() && hasCameraPermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        //android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        //android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                }, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }


    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }*/
}
