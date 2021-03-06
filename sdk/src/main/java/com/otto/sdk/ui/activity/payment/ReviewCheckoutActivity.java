package com.otto.sdk.ui.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.component.dialog.SaldoDialog;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class ReviewCheckoutActivity extends AppActivity {

    TextView tvBill;
    TextView tvPembayaranMitra;
    TextView tvBiayaLayanan;
    TextView tvTotalBayar;
    Button btnPay;
    ImageView ivBack;

    TextView tv_title_tujuan;
    TextView tv_title_no_tujuan;
    TextView tv_title_jumlah_uang;


    private int saldoEmoneyOttocash;
    private String reviewCheckout = "ReviewCheckout";


    private String BILL_PAYMENT = "bill_payment";
    private int billPayment;

    private String SERVICES_FEE = "services_fee";
    private String servicesFee;


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
        billPayment = Integer.parseInt(extras.getString(BILL_PAYMENT));
        tvBill.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(billPayment))));
        tvPembayaranMitra.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(billPayment))));
        tvTotalBayar.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(billPayment))));

        int total = billPayment;
        CacheUtil.putPreferenceInteger(IConfig.SESSION_TOTAL, total, ReviewCheckoutActivity.this);
    }

    private void initContent() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckSaldo();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onCheckSaldo() {
        saldoEmoneyOttocash = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, ReviewCheckoutActivity.this));
        if (saldoEmoneyOttocash < billPayment) {
            saldoDialog();
        } else {
            Intent intent = new Intent(ReviewCheckoutActivity.this, PinPaymentActivity.class);
            intent.putExtra(IConfig.TOTAL_BILL_PAYMENT, billPayment);
            intent.putExtra(IConfig.KEY_PIN_CHECKOUT, reviewCheckout);
            startActivity(intent);
        }
    }

    public void saldoDialog() {
        SaldoDialog saldoDialog;
        saldoDialog = new SaldoDialog(this, R.style.AppDialogFullScreen);
        saldoDialog.show();
    }

}
