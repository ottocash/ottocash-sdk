package com.otto.sdk.ui.activity.selfiewithktp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IUpgradeView;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;
import com.otto.sdk.presenter.UpgradePresenter;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.kycupgrade.CaptureKTPActivity;
import com.otto.sdk.ui.activity.kycupgrade.KTPResultViewActivity;
import com.otto.sdk.ui.activity.kycupgrade.OttocashSuccesActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static com.otto.sdk.ui.activity.selfiewithktp.CaptureSelfieWithKTPActivity.bitmap;

public class ResultSelfieWithKtpActivity extends BaseActivity implements IUpgradeView {

    private ImageView ivAvatar, ivback;
    private static final String IMAGE_DIRECTORY = "/CustomImage";
    private Button btn_data_belum_sesuai, btnBacktomenu, btn_fotoKTP;
    private String passpor_photo,ktp, number;
    private UpgradeAccountRequest upgradeAccountRequest;
    private UpgradeAccountResponse upgradeAccountResponse;
//    Canvas canvas;
    Paint paint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_selfie_with_ktp);
        passpor_photo = CaptureSelfieWithKTPActivity.base64String;
        Log.i("SELFIE", "foto selfie : " + passpor_photo);

//        ktp = getIntent().getStringExtra("base64");
        ktp = CaptureKTPActivity.base64String;
        Log.i("KTP", "ktp : " + ktp);
        number = getIntent().getStringExtra("account_number");
        Log.i("ACCOUNT", "Account number : " + number);

        btn_data_belum_sesuai = findViewById(R.id.btn_data_belum_sesuai);
        ivback = findViewById(R.id.ivBack);
        btnBacktomenu = findViewById(R.id.btn_batalkan);
        btn_fotoKTP = findViewById(R.id.btn_fotoKTP);
        ivAvatar = findViewById(R.id.iv_avatar);

//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
//        Matrix mat = new Matrix();
//        mat.postRotate(90);
//
//        bitmap = bitmap.createBitmap(bitmap, 0, 0,
//                bitmap.getWidth(), bitmap.getHeight(),
//                mat, true);
//        int h = bitmap.getHeight();
//
//        Canvas canvas = new Canvas();
//        canvas.drawBitmap(bitmap, 10,10, paint);
//        canvas.drawBitmap(bitmap, 10,10 + h + 10, paint);
//
//        Matrix matrix = new Matrix();
//
//        matrix.postRotate(90);
//
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 260, 280, true);
//
//        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

//        Matrix matrix = new Matrix();
//        matrix.setRotate(90, 90,90);
//        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        ivAvatar.setImageBitmap(bitmap);

        btn_data_belum_sesuai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnBacktomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultSelfieWithKtpActivity.this, DashboardSDKActivity.class);
                startActivity(intent);
            }
        });

        btn_fotoKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallApiUpgrade();


            }
        });
    }

    private void onCallApiUpgrade() {

        upgradeAccountRequest = new UpgradeAccountRequest(
                passpor_photo, number, ktp);
        showApiProgressDialog(OttoCashSdk.getAppComponent(), new UpgradePresenter(ResultSelfieWithKtpActivity.this) {
            @Override
            public void call() {
              getUpgrade (upgradeAccountRequest);

            }
        }, "Loading");
    }


    @Override
    public void handleUpgrade(UpgradeAccountResponse model) {
        upgradeAccountResponse = model;
        if (model.getMeta().getCode() == 200) {
            Intent intent = new Intent(ResultSelfieWithKtpActivity.this, OttocashSuccesActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}



