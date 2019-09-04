package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class TransferToFriendReviewActivity extends BaseActivity {

    ImageView ivBack;
    Button btnPay;
    TextView tvBill;

    TextView tvTitleReview;
    TextView tvSubReview;
    TextView tvTitlePayment;
    TextView tvTitleDestination;
    TextView tvTitleNoTujuan;
    TextView tvTitleJumlahUang;
    TextView tvPembayaranMitra;
    TextView tvBiayaLayanan;
    TextView tvTotalBayar;

    private String nominal;
    private String destinationPhone;
    private String nameContact;
    private String numberContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend_review);

        initComponent();
        initView();
    }

    private void initComponent() {

        ivBack = findViewById(R.id.ivBack);
        btnPay = findViewById(R.id.btnPay);
        tvBill = findViewById(R.id.tvBill);

        tvTitleReview = findViewById(R.id.tv_title_review);
        tvSubReview = findViewById(R.id.tv_sub_review);
        tvTitlePayment = findViewById(R.id.tv_title_payment);
        tvTitleDestination = findViewById(R.id.tv_title_tujuan);
        tvTitleNoTujuan = findViewById(R.id.tv_title_no_tujuan);
        tvTitleJumlahUang = findViewById(R.id.tv_title_jumlah_uang);
        tvPembayaranMitra = findViewById(R.id.tvPembayaranMitra);
        tvBiayaLayanan = findViewById(R.id.tvBiayaLayanan);
        tvTotalBayar = findViewById(R.id.tvTotalBayar);

        tvTitleReview.setText("DETAIL TRANSFER");
        tvSubReview.setText("Transfer");
        tvTitlePayment.setText("Transfer Uang");
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nominal = extras.getString(IConfig.KEY_NOMINAL);
            destinationPhone = extras.getString(IConfig.KEY_DESTINATION);
            nameContact = extras.getString(IConfig.KEY_NAME_CONTACT);
            numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
        }

        tvBill.setText(nominal);
        tvPembayaranMitra.setText(nominal);
        tvTotalBayar.setText(nominal);
        tvTitleDestination.setText("Tujuan : " + nameContact);
        tvTitleNoTujuan.setText("No Tujuan : " + numberContact);
        tvTitleJumlahUang.setText("Jumlah Uang : " + nominal);

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
