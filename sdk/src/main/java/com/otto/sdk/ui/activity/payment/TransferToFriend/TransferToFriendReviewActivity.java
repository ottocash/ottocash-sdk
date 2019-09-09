package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.payment.PinPaymentActivity;
import com.otto.sdk.ui.activity.payment.ReviewCheckoutActivity;
import com.otto.sdk.ui.component.dialog.SaldoDialog;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

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

    private int grandTotal;
    private String nominalTransferToFriend;
    private String nameContact;
    private String numberContact;
    private String pinTransferToFriend = "P2P";

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
            nominalTransferToFriend = extras.getString(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND);
            grandTotal = UiUtil.removeAllCharacterNumbers(nominalTransferToFriend);
            nameContact = extras.getString(IConfig.KEY_NAME_CONTACT);
            numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
        }

        tvBill.setText(nominalTransferToFriend);
        tvPembayaranMitra.setText(nominalTransferToFriend);
        tvTotalBayar.setText(nominalTransferToFriend);
        tvTitleDestination.setText("Tujuan : " + nameContact);
        tvTitleNoTujuan.setText("No Tujuan : " + numberContact);
        tvTitleJumlahUang.setText("Jumlah Uang : " + nominalTransferToFriend);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TransferToFriendReviewActivity.this, PinPaymentActivity.class);
                intent.putExtra(IConfig.KEY_PIN_TRANSFER_TO_FRIEND, pinTransferToFriend);
                intent.putExtra(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND, nominalTransferToFriend);
                intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);
                intent.putExtra(IConfig.KEY_NAME_CONTACT, nameContact);
                startActivity(intent);

                /*int totalBalance = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, TransferToFriendReviewActivity.this));
                if (totalBalance < grandTotal) {
                    Toast.makeText(TransferToFriendReviewActivity.this, "Saldo Anda tidak mencukupi", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(TransferToFriendReviewActivity.this, PinPaymentActivity.class);
                    intent.putExtra(IConfig.KEY_PIN_TRANSFER_TO_FRIEND, pinTransferToFriend);
                    intent.putExtra(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND, nominalTransferToFriend);
                    intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);
                    intent.putExtra(IConfig.KEY_NAME_CONTACT, nameContact);
                    startActivity(intent);
                }*/


            }
        });

    }
}
