//package com.otto.sdk.ui.activities.payment;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.otto.sdk.R;
//import com.otto.sdk.base.BaseActivitySDK;
//import com.otto.sdk.ui.activities.dashboard.DashboardActivitySDK;
//
//
//public class PaymentSuccessActivity extends BaseActivitySDK {
//
//    Button btnBottom;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment_success);
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
//                Intent intent = new Intent(PaymentSuccessActivity.this, DashboardActivitySDK.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                PaymentSuccessActivity.this.startActivity(intent);
//            }
//        });
//    }
//}
