package com.otto.sdk.ui.activity.kycupgrade;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otto.sdk.AppActivity;
import com.otto.sdk.Flag;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.kycupgrade.takeFotoKtp.TakeFotoKtpActivity;

import java.util.List;

public class GoToUpgradeActivity extends AppActivity {
    Button btn_fotoKTP;
    ImageView ivback;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_next);

        //number = getIntent().getStringExtra(Flag.ACCOUNT_NUMBER);
        btn_fotoKTP = findViewById(R.id.btnSubmit);
        ivback = findViewById(R.id.ivBack);

        btn_fotoKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initpermission();
            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //number = getIntent().getStringExtra("account_number");
        //Log.i("ACCOUNT", "Account number : " + number);
    }

    private void initpermission() {
        Dexter.withActivity(this).
                withPermissions(Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(GoToUpgradeActivity.this, TakeFotoKtpActivity.class);
                            //intent.putExtra("account_number", number);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //finish();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }


                }).onSameThread().check();
    }
}