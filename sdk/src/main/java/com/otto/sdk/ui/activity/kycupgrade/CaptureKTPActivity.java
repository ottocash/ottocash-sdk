package com.otto.sdk.ui.activity.kycupgrade;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.otto.sdk.R;
import com.otto.sdk.ui.activity.CameraPreview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import app.beelabs.com.codebase.base.BaseActivity;


public class CaptureKTPActivity extends BaseActivity {
    private int REQUEST_WRITE_STORAGE_REQUEST_CODE = 112;
    private CameraPreview mPreview;
    private android.hardware.Camera.PictureCallback mPicture;
    private ImageView ivCapture, switchCamera, ivback, ivFlash;
    private Context myContext;
    private FrameLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap = null;
    private Camera mCamera;
    private boolean permissionsGranted = false;
    private boolean isFlashOn = false;
    private String number;
    public static String base64String;
    private File output;
    private int orientation = 113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_ktp);
        initView();
        myContext = this;
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

        number = getIntent().getStringExtra("account_number");
        Log.i("ACCOUNT", "Account number : " + number);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCamera = getCameraInstance();
    }


    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    private void initView() {
        myContext = this;

        boolean hasFrontCamera = getApplication().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);
        mCamera = getCameraInstance();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //mCamera = Camera.open();
        //requestAppPermissions();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
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
        cameraPreview.setBackgroundResource(R.drawable.frame_capture_ktp);

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

    private Bitmap resize(Bitmap image) {
        int maxHeight = 576;
        int maxWidth = 1024;

        int width = image.getWidth();
        int height = image.getHeight();
        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (ratioMax > 1) {
            finalWidth = (int) ((float) maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) ((float) maxWidth / ratioBitmap);
        }
        image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
        return image;
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
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    public static Bitmap rotateImage(Bitmap bitmap) throws IOException {
        String rootPath = Environment.getExternalStorageDirectory() + File.separator;
        String photoDir = "photos";
        String pictureDirPath = rootPath + File.separator + photoDir;
        final File pictureDir = new File(pictureDirPath);
        pictureDir.mkdirs();
        int rotate = 0;
        ExifInterface exif;
        exif = new ExifInterface(pictureDirPath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    private Camera.PictureCallback getPictureCallback() {

        /*File dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        output = new File(dir, "CameraContentDemo.jpeg");
        Intent i = new Intent(Intent.ACTION_VIEW);

        i.setDataAndType(Uri.fromFile(output), "image/jpeg");*/


        final Camera.PictureCallback picture = new Camera.PictureCallback() {


            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //mCamera.setDisplayOrientation(270);


                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                getBase64String(bitmap);

                Log.i("BASE64", "ini base : " + getBase64String(bitmap));
                Log.i("BITMAP", "itu bitmap" + bitmap);
                Intent intent = new Intent(CaptureKTPActivity.this, KTPResultViewActivity.class);
                intent.putExtra("account_number", number);
                startActivity(intent);

                /*BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;
                options.inDither = false; // Disable Dithering mode
                options.inPurgeable = true; // Tell to gc that whether it needs free
                // memory, the Bitmap can be cleared
                options.inInputShareable = true; // Which kind of reference will be
                // used to recover the Bitmap
                // data after being clear, when
                // it will be used in the future
                options.inTempStorage = new byte[32 * 1024];
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

                // others devices
                if (bitmap.getHeight() < bitmap.getWidth()) {
                    orientation = 90;
                } else {
                    orientation = 0;
                }

                Bitmap bMapRotate;
                if (orientation != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(orientation);
                    bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                } else
                    bMapRotate = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
                            bitmap.getHeight(), true);


                FileOutputStream out;
                try {
                    out = new FileOutputStream(
                            String.format("/sdcard/DCIM/test/screen.jpg"));
                    bMapRotate.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    if (bMapRotate != null) {
                        bMapRotate.recycle();
                        bMapRotate = null;
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mCamera.startPreview();*/
            }
        };

        return picture;
    }

    /*public static Bitmap rotateImage(Bitmap bitmap) throws IOException {
        int rotate = 0;
        ExifInterface exif;
        exif = new ExifInterface(path);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }*/


    private String getBase64String(Bitmap bitmap) {

        //BitmapFactory.Options options = new BitmapFactory.Options();
        //bitmap = bitmap.decodeFile("ottoCash", options);
        bitmap = resize(bitmap);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);


        byte[] imageBytes = baos.toByteArray();

        base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions() && hasCameraPermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        //android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        //android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                }, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }


    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }


}
