package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otto.sdk.AppActivity;
import com.otto.sdk.R;

import java.util.List;

import app.beelabs.com.codebase.base.BaseActivity;

public class IntroductionUpgradeActivity extends AppActivity {

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

        initPermissionMultiple(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        });

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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();
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
