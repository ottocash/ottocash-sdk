package com.otto.sdk.ui.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PaymentSuccessActivity extends BaseActivity {

    private String nominalTransferToFriend;
    private int total;

    ImageButton ivClose;
    ImageButton ivCloseReceipt;
    NestedScrollView nestedScrollView;

    TextView tvTitleReceipt;
    TextView tvDescTitleReceipt;
    TextView tvTitlePaymentReceipt;
    TextView tvPaymentValue;

    private String pinTransferToFriend = "P2P";
    private String pinReviewCheckout = "ReviewCheckout";
    private String keyPinTransferToFriend;
    private String keyPinReviewCheckout;
    private BottomSheetBehavior mBottomSheetBehaviour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        initComponent();
        initPaymentValue();
        billPaymentSuccess();
    }

    private void initPaymentValue() {
        total = CacheUtil.getPreferenceInteger(IConfig.SESSION_TOTAL, PaymentSuccessActivity.this);

        Bundle extras = getIntent().getExtras();
        nominalTransferToFriend = extras.getString(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND);
        keyPinReviewCheckout = extras.getString(IConfig.KEY_PIN_CHECKOUT);
        keyPinTransferToFriend = extras.getString(IConfig.KEY_PIN_TRANSFER_TO_FRIEND);

        if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
            tvPaymentValue.setText(UiUtil.formatMoneyIDR(total));
        } else if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
            tvTitleReceipt.setText("TRANSFER BERHASIL");
            tvDescTitleReceipt.setText(R.string.desc_title_receipt);
            tvTitlePaymentReceipt.setText("TOTAL TRANSFER");
            tvPaymentValue.setText(UiUtil.formatMoneyIDR(Long.parseLong(nominalTransferToFriend)));
        }

    }

    private void initComponent() {
        ivClose = findViewById(R.id.iv_close);
        ivCloseReceipt = findViewById(R.id.iv_close_receipt);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
        tvTitleReceipt = findViewById(R.id.tvTitleReceipt);
        tvDescTitleReceipt = findViewById(R.id.tvDescTitleReceipt);
        tvTitlePaymentReceipt = findViewById(R.id.tvTitlePaymentReceipt);

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
                Intent intent = new Intent(PaymentSuccessActivity.this, DashboardSDKActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentSuccessActivity.this, DashboardSDKActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }


    public void billPaymentSuccess() {
        ivClose.setVisibility(View.VISIBLE);
        int peekHeightPx = getResources().getDimensionPixelSize(R.dimen.peek_height);
        mBottomSheetBehaviour.setPeekHeight(peekHeightPx);
    }
}
