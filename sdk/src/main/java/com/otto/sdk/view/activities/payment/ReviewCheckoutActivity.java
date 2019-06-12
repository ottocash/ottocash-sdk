package com.otto.sdk.view.activities.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.presenter.dao.PaymentDao;
import com.otto.sdk.support.UiUtil;
import com.otto.sdk.view.component.dialog.SaldoDialog;

import java.util.Random;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

import static app.beelabs.com.codebase.support.util.CacheUtil.getPreferenceString;

public class ReviewCheckoutActivity extends BaseActivity {

    private int grandTotal;
    private String customerReferenceNumber;
    private String SESSION_GRAND_TOTAL = "total";

    TextView tvBill;
    TextView tvPembayaranMitra;
    TextView tvBiayaLayanan;
    TextView tvTotalBayar;
    Button btnBottom;

    private ReviewCheckOutRequest model;

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
        final PaymentDao dao = new PaymentDao(this);

        model = new ReviewCheckOutRequest(String.valueOf(getPreferenceString(
                IConfig.SESSION_ACCOUNT_NUMBER, ReviewCheckoutActivity.this)));

        model.setAmount(grandTotal);
        model.setFee(0);
        model.setProductName("Pembayaran");
        model.setBillerId("PURCHASE_ELEVENIA");
        model.setProductCode("PYMNT");
        model.setPartnerCode("P000001");

        model.setCustomerReferenceNumber("UPN"+generateRandom(9) + "");

        int total = (model.getAmount() + model.getFee());
        CacheUtil.putPreferenceInteger(IConfig.SESSION_TOTAL, total, ReviewCheckoutActivity.this);
        int emoney = CacheUtil.getPreferenceInteger(IConfig.SESSION_EMONEY_BALANCE, ReviewCheckoutActivity.this);
        if (emoney < total) {
            saldoDialog();
        } else {
            showApiProgressDialog(OttoCashSdk.getAppComponent(), new PaymentDao(this) {
                @Override
                public void call() {
                    dao.onReviewCheckOut(model, ReviewCheckoutActivity.this, BaseDao.getInstance(ReviewCheckoutActivity.this,
                            IConfig.KEY_API_REVIEW_CHECK_OUT).callback);
                }
            });
        }
    }

    @Override
    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        super.onApiResponseCallback(br, responseCode, response);
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_REVIEW_CHECK_OUT) {
                ReviewCheckOutResponse data = (ReviewCheckOutResponse) br;
                if (data.getMeta().getCode() == 200) {

                    int total = (model.getAmount() + model.getFee());
                    CacheUtil.putPreferenceInteger(IConfig.SESSION_TOTAL, total, ReviewCheckoutActivity.this);
//                    int emoney = CacheUtil.getPreferenceInteger(IConfig.SESSION_EMONEY_BALANCE, ReviewCheckoutActivity.this);

                    Intent intent = new Intent(ReviewCheckoutActivity.this, PinActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    public void saldoDialog() {
        SaldoDialog saldoDialog;
        saldoDialog = new SaldoDialog(this, R.style.AppDialogFullScreen);
        saldoDialog.show();
    }
}
