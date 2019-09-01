package com.otto.sdk.ui.activity.payment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageButton;

import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class PaymentSuccessActivity extends BaseActivity {

    ImageButton ivClose;
    ImageButton ivCloseReceipt;
    NestedScrollView nestedScrollView;
    private BottomSheetBehavior mBottomSheetBehaviour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        initComponent();
        billPaymentSuccess();
    }

    private void initComponent() {
        ivClose = findViewById(R.id.iv_close);
        ivCloseReceipt = findViewById(R.id.iv_close_receipt);
        nestedScrollView = findViewById(R.id.nestedScrollView);


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
                onBackPressed();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    public void billPaymentSuccess() {
        ivClose.setVisibility(View.VISIBLE);
        int peekHeightPx = getResources().getDimensionPixelSize(R.dimen.peek_height);
        mBottomSheetBehaviour.setPeekHeight(peekHeightPx);
    }
}
