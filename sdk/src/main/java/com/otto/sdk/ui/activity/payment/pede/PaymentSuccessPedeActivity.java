package com.otto.sdk.ui.activity.payment.pede;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.core.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PaymentSuccessPedeActivity extends BaseActivity {

    private String nominalTransferToFriend;
    private int total;

    ImageButton ivClose;
    ImageButton ivCloseReceipt;
    NestedScrollView nestedScrollView;

    TextView tvTitleReceipt;
    TextView tvDescTitleReceipt;
    TextView tvTitlePaymentReceipt;
    TextView tvPaymentValue;

    TextView tv_id_transaction;
    TextView tv_jumlah_pembayaran;
    TextView tv_date;
    TextView tv_paket;
    TextView tv_harga_paket;
    TextView tv_total_pembayaran;

    private String pinTransferToFriend = "P2P";
    private String pinReviewCheckout = "ReviewCheckout";
    private String keyPinTransferToFriend;
    private String keyPinReviewCheckout;
    private BottomSheetBehavior mBottomSheetBehaviour;

    private String receiptReferenceNumber;
    private String receiptDate;
    private String numberContact;
    private String nameContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_pede);

        initComponent();
        initDisplayPaymentValue();
        billPaymentSuccess();
    }

    @Override
    public void onBackPressed() {
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
        ivClose.setVisibility(View.VISIBLE);
        int peekHeightPx = getResources().getDimensionPixelSize(R.dimen.peek_height);
        mBottomSheetBehaviour.setPeekHeight(peekHeightPx);
    }

    private void initComponent() {
        ivClose = findViewById(R.id.iv_close);
        ivCloseReceipt = findViewById(R.id.iv_close_receipt);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
        tvTitleReceipt = findViewById(R.id.tvTitleReceipt);
        tvDescTitleReceipt = findViewById(R.id.tvDescTitleReceipt);
        tvTitlePaymentReceipt = findViewById(R.id.tvTitlePaymentReceipt);
        tv_id_transaction = findViewById(R.id.tv_id_transaction);
        tv_jumlah_pembayaran = findViewById(R.id.tv_jumlah_pembayaran);
        tv_date = findViewById(R.id.tv_date);
        tv_paket = findViewById(R.id.tv_paket);
        tv_harga_paket = findViewById(R.id.tv_harga_paket);
        tv_total_pembayaran = findViewById(R.id.tv_total_pembayaran);


        mBottomSheetBehaviour = BottomSheetBehavior.from(nestedScrollView);
        mBottomSheetBehaviour.setPeekHeight(0);
        mBottomSheetBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED)
                    ivCloseReceipt.setVisibility(View.VISIBLE);
                else
                    ivCloseReceipt.setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        ivCloseReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentSuccessPedeActivity.this, DashboardSDKActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentSuccessPedeActivity.this, DashboardSDKActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void initDisplayPaymentValue() {
        total = CacheUtil.getPreferenceInteger(IConfig.SESSION_TOTAL, PaymentSuccessPedeActivity.this);

        Bundle extras = getIntent().getExtras();
        nominalTransferToFriend = extras.getString(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND);
        keyPinReviewCheckout = extras.getString(IConfig.KEY_PIN_CHECKOUT);
        keyPinTransferToFriend = extras.getString(IConfig.KEY_PIN_TRANSFER_TO_FRIEND);

        receiptReferenceNumber = extras.getString(IConfig.KEY_REFERENCE_NUMBER_P2P);
        receiptDate = extras.getString(IConfig.KEY_DATE_P2P);
        numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
        nameContact = extras.getString(IConfig.KEY_NAME_CONTACT);

        if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
            tvPaymentValue.setText(UiUtil.formatMoneyIDR(total));
        } else if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
            tvTitleReceipt.setText("TRANSFER BERHASIL");
            tvDescTitleReceipt.setText(R.string.desc_title_receipt);
            tvTitlePaymentReceipt.setText("TOTAL TRANSFER");
            tvPaymentValue.setText(nominalTransferToFriend);
            tv_id_transaction.setText(receiptReferenceNumber);
            tv_jumlah_pembayaran.setText(nominalTransferToFriend);
            tv_date.setText(receiptDate);
            tv_paket.setText("Transfer Ke " + nameContact);
            tv_harga_paket.setText(nominalTransferToFriend);
            tv_total_pembayaran.setText(nominalTransferToFriend);
        }

    }
}
