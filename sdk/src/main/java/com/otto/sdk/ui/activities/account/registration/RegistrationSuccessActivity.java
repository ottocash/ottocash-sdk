//package com.otto.sdk.ui.activities.account.registration;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.otto.sdk.R;
//import com.otto.sdk.ui.activities.dashboard.DashboardActivity;
//
//import app.beelabs.com.codebase.base.BaseActivity;
//
//public class RegistrationSuccessActivity extends BaseActivity {
//
//    Button btnBottom;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration_success);
//
//        initComponent();
//        initContent();
//    }
//
//    private void initComponent() {
//        btnBottom = findViewById(R.id.btnBottom);
//    }
//
//    private void initContent() {
//        btnBottom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegistrationSuccessActivity.this, DashboardActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//}
