package com.otto.sdk.ui.activities.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IReviewCheckoutViewSDK;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponseSDK;
import com.otto.sdk.model.api.response.ReviewCheckOutResponseSDK;
import com.otto.sdk.presenter.ReviewCheckoutPresenterSDK;
import com.otto.sdk.support.UiUtil;
import com.otto.sdk.ui.component.dialog.SaldoDialogSDK;

import java.util.Random;

import static com.otto.sdk.base.support.util.CacheUtil.getPreferenceString;


public class ReviewCheckoutActivitySDK extends BaseActivitySDK implements IReviewCheckoutViewSDK {

    private int grandTotal;
    private String customerReferenceNumber;
    private String SESSION_GRAND_TOTAL = "total";

    TextView tvBill;
    TextView tvPembayaranMitra;
    TextView tvBiayaLayanan;
    TextView tvTotalBayar;
    Button btnBottom;

    private ReviewCheckOutRequest reviewCheckOutRequest;

    Activity activity;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_checkout);

        initComponent();
        initContent();
    }

    private void initComponent() {
        tvBill = findViewById(R.id.tvBill);
        tvPembayaranMitra = findViewById(R.id.tvPembayaranMitra);
        tvBiayaLayanan = findViewById(R.id.tvBiayaLayanan);
        tvTotalBayar = findViewById(R.id.tvTotalBayar);
        btnBottom = findViewById(R.id.btnBottom);

        Bundle extras = getIntent().getExtras();
        grandTotal = Integer.parseInt(extras.getString(SESSION_GRAND_TOTAL));
        tvBill.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(grandTotal))));
        tvPembayaranMitra.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(grandTotal))));
        tvTotalBayar.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(grandTotal))));
    }

    private void initContent() {
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallApiReviewCheckOut();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (customerReferenceNumber != null) {
            customerReferenceNumber = (generateRandom(12) + "");
        }
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }


    private void onCallApiReviewCheckOut() {
        reviewCheckOutRequest = new ReviewCheckOutRequest(String.valueOf(getPreferenceString(
                IConfigSDK.SESSION_ACCOUNT_NUMBER, ReviewCheckoutActivitySDK.this)));
        reviewCheckOutRequest.setAmount(grandTotal);
        reviewCheckOutRequest.setFee(0);
        reviewCheckOutRequest.setProductName("Pembayaran");
        reviewCheckOutRequest.setBillerId("PURCHASE_ELEVENIA");
        reviewCheckOutRequest.setProductCode("PYMNT");
        reviewCheckOutRequest.setPartnerCode("P000001");
        reviewCheckOutRequest.setCustomerReferenceNumber("UPN" + generateRandom(9) + "");

        int total = (reviewCheckOutRequest.getAmount() + reviewCheckOutRequest.getFee());
        CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_TOTAL, total, ReviewCheckoutActivitySDK.this);
        int emoney = CacheUtil.getPreferenceInteger(IConfigSDK.SESSION_EMONEY_BALANCE, ReviewCheckoutActivitySDK.this);
        if (emoney < total) {
            saldoDialog();
        } else {
            showApiProgressDialog(OttoCashSDK.getAppComponent(), new ReviewCheckoutPresenterSDK(this) {
                @Override
                public void call() {
                    getReviewCheckout(reviewCheckOutRequest);

                }
            }, "Loading");
        }
    }


    public void saldoDialog() {
        SaldoDialogSDK saldoDialog;
        saldoDialog = new SaldoDialogSDK(this, R.style.AppDialogFullScreen);
        saldoDialog.show();
    }

    @Override
    public void handleReviewCheckout(ReviewCheckOutResponseSDK model) {
        if (model.getMeta().getCode() == 200) {

            int total = (reviewCheckOutRequest.getAmount() + reviewCheckOutRequest.getFee());
            CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_TOTAL, total, ReviewCheckoutActivitySDK.this);

            Intent intent = new Intent(ReviewCheckoutActivitySDK.this, PinActivitySDK.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handlePaymentValidate(PaymentValidateResponseSDK model) {

    }
}
