package com.otto.sdk.ui.activity.kycupgrade;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.Flag;
import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class UpgradeActivity extends BaseActivity {

    Button btnCancel;
    Button btnNext;
    ImageView ivback;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_plus);
        initComponent();

        number = getIntent().getStringExtra(Flag.ACCOUNT_NUMBER);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpgradeActivity.this, ActivityUpgradeNext.class);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void initComponent() {
        btnCancel = findViewById(R.id.btn_nanti_saja);
        btnNext = findViewById(R.id.btn_next_upgrade);
        ivback = findViewById(R.id.ivBack);


    }


}
