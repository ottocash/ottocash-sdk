package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.selfiewithktp.CaptureSelfieWithKTPActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class KTPResultViewActivity extends AppCompatActivity {

    private ImageView ivback, ivAvatar;
    private static final String IMAGE_DIRECTORY = "/CustomImage";
    private Button btn_data_belum_sesuai, btnBackHome, btn_fotoKTP;
    private String ktp, number, path;
    private final String TAG = this.getClass().getSimpleName();
    public static String getBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktpresult_view);
        btn_data_belum_sesuai = findViewById(R.id.btn_data_belum_sesuai);
        btn_data_belum_sesuai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Log.i(TAG, "ktp : " + ktp);
        number = getIntent().getStringExtra("account_number");
        getBase64 = CaptureKTPActivity.base64String;
        Log.i("ACCOUNT", "Account number : " + number);

        ivback = findViewById(R.id.ivBack);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_fotoKTP = findViewById(R.id.btn_fotoKTP);
        btn_fotoKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KTPResultViewActivity.this, CaptureSelfieWithKTPActivity.class);
                intent.putExtra("account_number", number);
                startActivity(intent);
            }
        });


        btnBackHome = findViewById(R.id.btn_batalkan);
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KTPResultViewActivity.this, DashboardSDKActivity.class);

                startActivity(intent);
            }
        });


        ivAvatar = findViewById(R.id.iv_avatar);
        ivAvatar.setImageBitmap(CaptureKTPActivity.bitmap);
        Log.i("BITMAP", "INI BITMAP" + CaptureKTPActivity.bitmap);
    }

    public static int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }
}
