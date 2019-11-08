package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.otto.sdk.Flag;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.selfiewithktp.CaptureSelfieWithKTPActivity;

import app.beelabs.com.codebase.base.BaseActivity;

public class ActivityUpgradeNext extends BaseActivity {
    Button btn_fotoKTP;
    ImageView ivback;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_next);

        number = getIntent().getStringExtra(Flag.ACCOUNT_NUMBER);

        btn_fotoKTP = findViewById(R.id.btn_fotoKTP);
        ivback = findViewById(R.id.ivBack);



        btn_fotoKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityUpgradeNext.this, CaptureFotoKTPActivity.class);
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
    }


}
