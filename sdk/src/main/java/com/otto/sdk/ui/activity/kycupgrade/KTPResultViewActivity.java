package com.otto.sdk.ui.activity.kycupgrade;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.nfc.Tag;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.otto.sdk.Flag;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.account.registration.RegistrationSuccessActivity;
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
    private String ktp, number;
    private final String TAG = this.getClass().getSimpleName();



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

        ktp = getIntent().getStringExtra(Flag.ID_CARD);
        number = getIntent().getStringExtra(Flag.ACCOUNT_NUMBER);

        Log.i( TAG, "ktp : " + ktp );
        Log.i( number, "number : " );


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
                intent.putExtra(Flag.ID_CARD, ktp);
                intent.putExtra(Flag.ACCOUNT_NUMBER, number);
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
//        ivAvatar.setImageBitmap(CaptureKTPActivity.bitmap);
//
//        saveImage(CaptureKTPActivity.bitmap);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivAvatar.setImageBitmap(photo);
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);


        // have the object build the directory structure, if needed.


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
