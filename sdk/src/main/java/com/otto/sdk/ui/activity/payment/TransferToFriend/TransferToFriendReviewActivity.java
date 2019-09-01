package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class TransferToFriendReviewActivity extends BaseActivity {

    ImageView ivBack;
    Button btnPay;
    TextView tvBill;

    TextView tv_title_review;
    TextView tv_sub_review;
    TextView tv_title_payment;
    TextView tv_title_tujuan;
    TextView tv_title_no_tujuan;
    TextView tv_title_jumlah_uang;

    TextView tvPembayaranMitra;
    TextView tvBiayaLayanan;
    TextView tvTotalBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend_review);

        initComponent();
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        btnPay = findViewById(R.id.btnPay);
        tvBill = findViewById(R.id.tvBill);

        tv_title_review = findViewById(R.id.tv_title_review);
        tv_sub_review = findViewById(R.id.tv_sub_review);
        tv_title_payment = findViewById(R.id.tv_title_payment);
        tv_title_tujuan = findViewById(R.id.tv_title_tujuan);
        tv_title_no_tujuan = findViewById(R.id.tv_title_no_tujuan);
        tv_title_jumlah_uang = findViewById(R.id.tv_title_jumlah_uang);
        tvPembayaranMitra = findViewById(R.id.tvPembayaranMitra);
        tvBiayaLayanan = findViewById(R.id.tvBiayaLayanan);
        tvTotalBayar = findViewById(R.id.tvTotalBayar);

        tv_title_review.setText("DETAIL TRANSFER");
        tv_sub_review.setText("Transfer");
        tv_title_payment.setText("Transfer Uang");
        tv_title_tujuan.setText("Tujuan : JOHN");
        tv_title_no_tujuan.setText("No Tujuan:0878586859");
        tv_title_jumlah_uang.setText("Jumlah uang: Rp 125.000");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransferToFriendReviewActivity.this, TransferToFriendPinActivity.class);
                startActivity(intent);
            }
        });
    }
}
