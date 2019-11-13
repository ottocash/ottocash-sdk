package com.otto.sdk.ui.activity.kycupgrade;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.otto.sdk.Flag;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import app.beelabs.com.codebase.base.BaseActivity;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;


@SuppressWarnings("deprecation")
public class CaptureFotoKTPActivity extends BaseActivity implements PhotoHandler.PhotoHandlerCallback {

    FrameLayout previewContainer;
    private Bundle bundle;
    private Camera camera;
    private CameraComponent cameraPreview;
    private boolean permissionsGranted = false;
    private Context myContext;

    private ImageView btnCam, ivFlash, ivback, ivSwitch;
    private UpgradeAccountRequest model;
    private String account;
    private boolean isFlashOn = false;
    private boolean isfront = true;
    int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int REQUEST_WRITE_STORAGE_REQUEST_CODE = 112;
    public String photoFileName = "photo.jpeg";
    String path;
    private File output=null;
    private static final int CONTENT_REQUEST=1337;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.activity_capture_ktp);
        initView();
        requestAppPermissions();
        myContext = this;
        previewContainer = findViewById(R.id.cPreview);
        btnCam = findViewById(R.id.btnCam);
        ivFlash = findViewById(R.id.iv_flash);
        ivback =  findViewById(R.id.ivBack);
        ivSwitch = findViewById(R.id.btnSwitch);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();

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

        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfront ) {
                    camera = Camera.open(camId);
                    camera.startPreview();

                } else {
                    camera = Camera.open(camId + 1);
                    camera.startPreview();

                }


            }
        });
    }


    private void onFlashOff() {
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
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
//        onCallApiUpgrade();
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
                        camera = getCameraInstance();
                        if (!isDeviceSupportCamera() || camera == null)
                            onBackPressed();
                        boolean hasFrontCamera = myContext.getPackageManager()
                                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
                        cameraPreview = new CameraComponent(myContext, camera, hasFrontCamera);

                        previewContainer.addView(cameraPreview);
                        previewContainer.setBackgroundResource(R.drawable.frame_capture_ktp);

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
//            camera.stopPreview();
            camera.release();
            camera = null;
        }
        if (cameraPreview != null) {
            previewContainer.removeView(cameraPreview);
            cameraPreview = null;
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CHILD");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d("CHILD", "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent i=new Intent(Intent.ACTION_VIEW);

                i.setDataAndType(Uri.fromFile(output), "image/jpeg");
                startActivity(i);
                finish();
            }
        }
    }


    private void captureImage() {
//        Uri capturedImageUri=null;
//        Calendar cal = Calendar.getInstance();
//        File file = new File(Environment.getExternalStorageDirectory(),  (cal.getTimeInMillis()+".jpg"));
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//                System.out.println("FILE TERSIMPAN");
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }else{
//            file.delete();
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        capturedImageUri = Uri.fromFile(file);

//        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);

        File dir=
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        output=new File(dir, "CameraContentDemo.jpeg");
        Intent i=new Intent(Intent.ACTION_VIEW);

        i.setDataAndType(Uri.fromFile(output), "image/jpeg");



        output.mkdirs();
        if (!output.exists() && !output.mkdirs()) {
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
                    camera.takePicture(null, null, new PhotoHandler(output, IConfig.CAMERA_KTP_SELFIE_TYPE,
                            Camera.CameraInfo.CAMERA_FACING_FRONT, CaptureFotoKTPActivity.this));
                    camera.setPreviewCallback(null);
                }
            });
        } else {
            camera.takePicture(null, null, new PhotoHandler(output, IConfig.CAMERA_KTP_SELFIE_TYPE,
                    Camera.CameraInfo.CAMERA_FACING_FRONT, this));
            camera.setPreviewCallback(null);

        }
        String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/CameraContentDemo.jpeg";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int iss = 0; iss < files.length; iss++)
        {
            Log.d("FilesNAMEEEE", "FileName:" + files[iss].getName());
        }

        Intent intent = new Intent(CaptureFotoKTPActivity.this, KTPResultViewActivity.class);
        intent.putExtra("path", files[0].getPath());
        System.out.println("HASIL PATH " + files[0].getPath());

        startActivity(intent);


    }

//    private void onCallApiUpgrade() {
//        model = new UpgradeAccountRequest(
//                IConfig.SESSION_ACCOUNT_NUMBER, IConfig.SESSION_ID_CARD, IConfig.SESSION_PASSPORT_PHOTO, CaptureFotoKTPActivity.this);
//
//        showApiProgressDialog(OttoCashSdk.getAppComponent(), new UpgradePresenter(CaptureFotoKTPActivity.this) {
//            @Override
//            public void call() {
//                getUpgrade(model);
//
//            }
//        }, "Loading");
//    }

    @Override
    public void getData(Intent data) {
        final String ktp = CameraUtil.convertImageToBase64(CameraUtil.getPhotoCameraResult(data,
                IConfig.BASE64_CAMERA_PERSONA_KEY, this));

//        model.setIdCard(ktp);
//        bundle.remove("model");
        /*Intent intent = new Intent(CaptureFotoKTPActivity.this, KTPResultViewActivity.class);
        intent.putExtra(Flag.ID_CARD, ktp);
        startActivity(intent);
*/
        /*btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CaptureFotoKTPActivity.this, KTPResultViewActivity.class);
                intent.putExtra();
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
}
