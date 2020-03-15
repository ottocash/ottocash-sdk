package com.otto.sdk.ui.activity.payment.pede;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.core.widget.NestedScrollView;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.payment.otto.PaymentSuccessOttoActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PaymentSuccessPedeActivity extends BaseActivity {

    ImageView ivBack;
    TextView tvTransactionTujuan;
    TextView tvTransactionAmount;
    TextView tvTransactionIdTujuan;
    TextView tvTransactionPelanggan;
    TextView tvTransactionDate;
    TextView tvTransactionID;
    TextView tvTransactionAmount2;
    TextView tvTransactionAdminFee;
    TextView tvTransactionTotal;

    private String nameTujuanTransfer;
    private String numberContact;

    String dateTransaction;
    String serviceTypeTransaction;
    String nominalTransaction;
    String destinationAccountNumberTransaction;
    String descriptionTransaction;
    String statusTransaction;
    String referenceNumberTransaction;

    private String pinTransferToFriend = "P2P";
    private String pinReviewCheckout = "ReviewCheckout";
    private String keyPinTransferToFriend;
    private String keyPinReviewCheckout;


    TextView tvPaymentValue;
    private int total;


    ImageButton ivClose;
    ImageButton ivCloseReceipt;
    NestedScrollView nestedScrollView;
    private BottomSheetBehavior mBottomSheetBehaviour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_pede);


        initComponent();
        contentUI();
        initDisplayPaymentValue();
        billPaymentSuccess();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PaymentSuccessPedeActivity.this, DashboardSDKActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;
    }


    public void billPaymentSuccess() {
        //ivClose.setVisibility(View.VISIBLE);
        int peekHeightPx = getResources().getDimensionPixelSize(R.dimen.peek_height);
        mBottomSheetBehaviour.setPeekHeight(peekHeightPx);
    }

    private void initComponent() {
        ivClose = findViewById(R.id.iv_close);
        ivCloseReceipt = findViewById(R.id.iv_close_receipt);
        nestedScrollView = findViewById(R.id.nestedScrollView);

        ivBack = findViewById(R.id.ivBack);
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
        tvTransactionTujuan = findViewById(R.id.tvTransactionTujuan);
        tvTransactionAmount = findViewById(R.id.tvTransactionAmount);
        tvTransactionIdTujuan = findViewById(R.id.tvTransactionIdTujuan);
        tvTransactionPelanggan = findViewById(R.id.tvTransactionPelanggan);
        tvTransactionDate = findViewById(R.id.tvTransactionDate);
        tvTransactionID = findViewById(R.id.tvTransactionID);
        tvTransactionAmount2 = findViewById(R.id.tvTransactionAmount2);
        tvTransactionAdminFee = findViewById(R.id.tvTransactionAdminFee);
        tvTransactionTotal = findViewById(R.id.tvTransactionTotal);

        mBottomSheetBehaviour = BottomSheetBehavior.from(nestedScrollView);
        mBottomSheetBehaviour.setPeekHeight(0);
        mBottomSheetBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
//                if (newState == BottomSheetBehavior.STATE_EXPANDED)
//                    ivCloseReceipt.setVisibility(View.VISIBLE);
//                else
//                    ivCloseReceipt.setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        ivCloseReceipt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PaymentSuccessPedeActivity.this, DashboardSDKActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });

//        ivClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PaymentSuccessPedeActivity.this, DashboardSDKActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });

    }

    private void contentUI() {
        total = CacheUtil.getPreferenceInteger(IConfig.OC_SESSION_TOTAL, PaymentSuccessPedeActivity.this);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        nominalTransaction = extras.getString(IConfig.NOMINAL_TRANSACTION);
        keyPinReviewCheckout = extras.getString(IConfig.KEY_PIN_CHECKOUT);
        keyPinTransferToFriend = extras.getString(IConfig.KEY_PIN_TRANSFER_TO_FRIEND);

        numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
        nameTujuanTransfer = extras.getString(IConfig.KEY_ACCOUNT_NAME_TUJUAN);

        dateTransaction = extras.getString(IConfig.DATE_TRANSACTION);
        serviceTypeTransaction = extras.getString(IConfig.SERVICE_TYPE_TRANSACTION);
        nominalTransaction = extras.getString(IConfig.NOMINAL_TRANSACTION);
        destinationAccountNumberTransaction = extras.getString(IConfig.DESTINATION_ACCOUNT_NUMBER_TRANSACTION);
        descriptionTransaction = extras.getString(IConfig.DESCRIPTION_TRANSACTION);
        referenceNumberTransaction = extras.getString(IConfig.REFERENCE_NUMBER_TRANSACTION);
        statusTransaction = extras.getString(IConfig.STATUS_TRANSACTION);


        if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
            tvPaymentValue.setText(UiUtil.formatMoneyIDR((total)));
        } else if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
            tvPaymentValue.setText(nominalTransaction);
        }
    }

    private void initDisplayPaymentValue() {
        Bundle extras = getIntent().getExtras();
        dateTransaction = extras.getString(IConfig.DATE_TRANSACTION);
        serviceTypeTransaction = extras.getString(IConfig.SERVICE_TYPE_TRANSACTION);
        nominalTransaction = extras.getString(IConfig.NOMINAL_TRANSACTION);
        destinationAccountNumberTransaction = extras.getString(IConfig.DESTINATION_ACCOUNT_NUMBER_TRANSACTION);
        descriptionTransaction = extras.getString(IConfig.DESCRIPTION_TRANSACTION);
        referenceNumberTransaction = extras.getString(IConfig.REFERENCE_NUMBER_TRANSACTION);
        statusTransaction = extras.getString(IConfig.STATUS_TRANSACTION);
        nameTujuanTransfer = extras.getString(IConfig.KEY_ACCOUNT_NAME_TUJUAN);
        numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);

        keyPinReviewCheckout = extras.getString(IConfig.KEY_PIN_CHECKOUT);
        keyPinTransferToFriend = extras.getString(IConfig.KEY_PIN_TRANSFER_TO_FRIEND);


        if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
            tvTransactionIdTujuan.setText("ID Tujuan : " + nameTujuanTransfer + " (" + numberContact + ")");
            tvTransactionPelanggan.setText("Nama Pelanggan : ");

            tvTransactionTujuan.setText("Transfer ke " + nameTujuanTransfer);
            tvTransactionAmount.setText(nominalTransaction);

            tvTransactionDate.setText(dateTransaction);
            tvTransactionID.setText("ID Transaksi : " + referenceNumberTransaction);

            tvTransactionAmount2.setText(nominalTransaction);
            tvTransactionTotal.setText(nominalTransaction);

        } else if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
            tvTransactionIdTujuan.setText("ID Tujuan : " + nameTujuanTransfer + " (" + numberContact + ")");
            tvTransactionPelanggan.setText("ID Transaksi : " + referenceNumberTransaction);

            tvTransactionTujuan.setText("Transfer ke " + nameTujuanTransfer);
            tvTransactionAmount.setText(nominalTransaction);

            tvTransactionDate.setText(dateTransaction);
            tvTransactionID.setVisibility(View.GONE);

            tvTransactionAmount2.setText(nominalTransaction);
            tvTransactionTotal.setText(nominalTransaction);
        }
    }


}
