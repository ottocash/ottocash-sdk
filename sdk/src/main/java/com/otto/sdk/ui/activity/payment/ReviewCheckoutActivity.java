package com.otto.sdk.ui.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IReviewCheckoutView;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.presenter.ReviewCheckoutPresenter;
import com.otto.sdk.ui.component.dialog.SaldoDialog;
import com.otto.sdk.ui.component.support.UiUtil;

import java.util.Random;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static app.beelabs.com.codebase.support.util.CacheUtil.getPreferenceString;

public class ReviewCheckoutActivity extends BaseActivity implements IReviewCheckoutView {


    TextView tvBill;
    TextView tvPembayaranMitra;
    TextView tvBiayaLayanan;
    TextView tvTotalBayar;
    Button btnPay;
    ImageView ivBack;

    TextView tv_title_tujuan;
    TextView tv_title_no_tujuan;
    TextView tv_title_jumlah_uang;

    private int total;
    private int grandTotal;
    private String customerReferenceNumber;
    private String SESSION_GRAND_TOTAL = "total";
    private String reviewCheckout = "ReviewCheckout";


    private ReviewCheckOutRequest reviewCheckOutRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_checkout);

        initComponent();
        initContent();
    }

    private void initComponent() {
        tv_title_tujuan = findViewById(R.id.tv_title_tujuan);
        tv_title_no_tujuan = findViewById(R.id.tv_title_no_tujuan);
        tv_title_jumlah_uang = findViewById(R.id.tv_title_jumlah_uang);
        tv_title_tujuan.setVisibility(View.GONE);
        tv_title_no_tujuan.setVisibility(View.GONE);
        tv_title_jumlah_uang.setVisibility(View.GONE);

        tvBill = findViewById(R.id.tvBill);
        tvPembayaranMitra = findViewById(R.id.tvPembayaranMitra);
        tvBiayaLayanan = findViewById(R.id.tvBiayaLayanan);
        tvTotalBayar = findViewById(R.id.tvTotalBayar);
        btnPay = findViewById(R.id.btnPay);
        ivBack = findViewById(R.id.ivBack);

        Bundle extras = getIntent().getExtras();
        grandTotal = Integer.parseInt(extras.getString(SESSION_GRAND_TOTAL));
        tvBill.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(grandTotal))));
        tvPembayaranMitra.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(grandTotal))));
        tvTotalBayar.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(grandTotal))));
    }

    private void initContent() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallApiReviewCheckOut();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                IConfig.SESSION_ACCOUNT_NUMBER, ReviewCheckoutActivity.this)));
        reviewCheckOutRequest.setAmount(grandTotal);
        reviewCheckOutRequest.setFee(0);
        reviewCheckOutRequest.setProductName("Pembayaran");
        reviewCheckOutRequest.setBillerId("PURCHASE_ELEVENIA");
        reviewCheckOutRequest.setProductCode("PYMNT");
        reviewCheckOutRequest.setPartnerCode("P000001");
        reviewCheckOutRequest.setCustomerReferenceNumber("UPN" + generateRandom(9) + "");

        int total = (reviewCheckOutRequest.getAmount() + reviewCheckOutRequest.getFee());
        CacheUtil.putPreferenceInteger(IConfig.SESSION_TOTAL, total, ReviewCheckoutActivity.this);
        int emoney = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, ReviewCheckoutActivity.this));
        if (emoney < total) {
            saldoDialog();
        } else {
            showApiProgressDialog(OttoCashSdk.getAppComponent(), new ReviewCheckoutPresenter(this) {
                @Override
                public void call() {
                    getReviewCheckout(reviewCheckOutRequest);

                }
            }, "Loading");
        }
    }


    public void saldoDialog() {
        SaldoDialog saldoDialog;
        saldoDialog = new SaldoDialog(this, R.style.AppDialogFullScreen);
        saldoDialog.show();
    }

    @Override
    public void handleReviewCheckout(ReviewCheckOutResponse model) {
        if (model.getMeta().getCode() == 200) {

            total = (reviewCheckOutRequest.getAmount() + reviewCheckOutRequest.getFee());
            CacheUtil.putPreferenceInteger(IConfig.SESSION_TOTAL, total, ReviewCheckoutActivity.this);

            Intent intent = new Intent(ReviewCheckoutActivity.this, PinPaymentActivity.class);
            intent.putExtra(IConfig.KEY_PIN_CHECKOUT, reviewCheckout);
            startActivity(intent);
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

}
