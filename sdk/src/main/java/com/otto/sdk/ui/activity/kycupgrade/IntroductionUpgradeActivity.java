package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class IntroductionUpgradeActivity extends BaseActivity {

    Button btnCancel;
    Button btnNext;
    ImageView ivback;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_upgrade);
        initComponent();

        number = getIntent().getStringExtra("account_number");
        Log.i("ACCOUNT", "Account number : " + number);

    }

    public void initComponent() {
        btnCancel = findViewById(R.id.btn_nanti_saja);
        btnNext = findViewById(R.id.btn_next_upgrade);
        ivback = findViewById(R.id.ivBack);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroductionUpgradeActivity.this, GoToUpgradeActivity.class);
                intent.putExtra("account_number", number);
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

}