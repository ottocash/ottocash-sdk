package com.otto.sdk.ui.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCash;
import com.otto.sdk.R;
import com.otto.sdk.model.api.response.PaymentData;
import com.otto.sdk.ui.component.dialog.SaldoDialog;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.support.util.CacheUtil;

import static com.otto.sdk.IConfig.OTTOCASH_PAYMENT_DATA_REFERENCE_NUMBER;
import static com.otto.sdk.IConfig.PAYMENT_BILLER_ID;
import static com.otto.sdk.IConfig.PAYMENT_CUSTOMER_RN;
import static com.otto.sdk.IConfig.PAYMENT_FEE;
import static com.otto.sdk.IConfig.PAYMENT_PARTNER_CODE;
import static com.otto.sdk.IConfig.PAYMENT_PRODUCT_CODE;
import static com.otto.sdk.IConfig.PAYMENT_PRODUCT_NAME;
import static com.otto.sdk.OttoCash.OTTOCASH_PAYMENT_DATA;
import static com.otto.sdk.OttoCash.REQ_OTTOCASH_PAYMENT;

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
        tvBill.setText(UiUtil.formatMoneyIDR((billPayment)));
        tvPembayaranMitra.setText(UiUtil.formatMoneyIDR((billPayment)));
        tvTotalBayar.setText(UiUtil.formatMoneyIDR((billPayment)));

        int total = billPayment;
        CacheUtil.putPreferenceInteger(IConfig.OC_SESSION_TOTAL, total, ReviewCheckoutActivity.this);

        /**
         * account_number : 085880507999
         * amount : 55000
         * fee : 500
         * product_name : Pembayaran
         * biller_id : PURCHASE_ELEVENIA
         * customer_reference_number : UPN00d000458
         * product_code : PYMNT
         * partner_code : P000001
         * latitude : 10.232444
         * longitude : -6.4312323
         * device_id : 213123123123123
         */
        int fee = extras.getInt(PAYMENT_FEE);
        String product_name = extras.getString(PAYMENT_PRODUCT_NAME);
        String biller_id = extras.getString(PAYMENT_BILLER_ID);
        String customer_reference_number = extras.getString(PAYMENT_CUSTOMER_RN);
        String product_code = extras.getString(PAYMENT_PRODUCT_CODE);
        String partner_code = extras.getString(PAYMENT_PARTNER_CODE);

        CacheUtil.putPreferenceInteger(PAYMENT_FEE, fee, this);
        CacheUtil.putPreferenceString(PAYMENT_PRODUCT_NAME, product_name, this);
        CacheUtil.putPreferenceString(PAYMENT_BILLER_ID, biller_id, this);
        CacheUtil.putPreferenceString(PAYMENT_CUSTOMER_RN, customer_reference_number, this);
        CacheUtil.putPreferenceString(PAYMENT_PRODUCT_CODE, product_code, this);
        CacheUtil.putPreferenceString(PAYMENT_PARTNER_CODE, partner_code, this);
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
        saldoEmoneyOttocash = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, ReviewCheckoutActivity.this));
        if (saldoEmoneyOttocash < billPayment) {
            saldoDialog();
        } else {

            Intent intent = new Intent(ReviewCheckoutActivity.this, NewPinPaymentActivty.class);
            intent.putExtra(IConfig.TOTAL_BILL_PAYMENT, billPayment);
            intent.putExtra(IConfig.KEY_PIN_CHECKOUT, reviewCheckout);
            startActivityForResult(intent, REQ_OTTOCASH_PAYMENT);
            //startActivity(intent);
            //finish();

        }
    }

    public void saldoDialog() {
        SaldoDialog saldoDialog;
        saldoDialog = new SaldoDialog(this, R.style.AppDialogFullScreen);
        saldoDialog.show();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OttoCash.REQ_OTTOCASH_PAYMENT) {
            Intent intent = new Intent();
            if (data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA) != null) {
                PaymentData paymentData = data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA);
                Toast.makeText(this, paymentData.getReferenceNumber(), Toast.LENGTH_LONG).show();

                String referenceNumber = paymentData.getReferenceNumber();
                CacheUtil.putPreferenceString(OTTOCASH_PAYMENT_DATA_REFERENCE_NUMBER, referenceNumber, this);
            }
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OttoCash.REQ_OTTOCASH_PAYMENT) {
            Intent intent = new Intent();
            if (data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA) != null) {
                PaymentData paymentData = data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA);
                intent.putExtra(OTTOCASH_PAYMENT_DATA, paymentData);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}