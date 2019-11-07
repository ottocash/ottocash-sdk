package com.otto.sdk.ui.activity.selfiewithktp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.kycupgrade.CaptureKTPActivity;
import com.otto.sdk.ui.activity.kycupgrade.OttocashSuccesActivity;

public class ResultSelfieWithKtpActivity extends AppCompatActivity {

    private ImageView imageView, ivback;
    private static final String IMAGE_DIRECTORY = "/CustomImage";
    private Button btn_data_belum_sesuai, btnBacktomenu, btn_fotoKTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_selfie_with_ktp);

        imageView = findViewById(R.id.iv_avatar);
//        imageView.setImageBitmap(CaptureKTPActivity.bitmap);
        btn_data_belum_sesuai = findViewById(R.id.btn_data_belum_sesuai);
        ivback = findViewById(R.id.ivBack);
        btnBacktomenu = findViewById(R.id.btn_batalkan);
        btn_fotoKTP = findViewById(R.id.btn_fotoKTP);

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
                Intent intent = new Intent(ResultSelfieWithKtpActivity.this, OttocashSuccesActivity.class);
                startActivity(intent);
            }
        });
    }
}



