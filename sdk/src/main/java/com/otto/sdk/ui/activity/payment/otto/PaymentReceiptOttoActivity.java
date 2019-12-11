package com.otto.sdk.ui.activity.payment.otto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;

import app.beelabs.com.codebase.base.BaseActivity;

public class PaymentReceiptOttoActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt_otto);

        initComponent();
        initContentUI();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentReceiptOttoActivity.this, DashboardSDKActivity.class));
            }
        });
    }

    private void initComponent(){
        ivBack = findViewById(R.id.ivBack);
        tvTransactionTujuan = findViewById(R.id.tvTransactionTujuan);
        tvTransactionAmount = findViewById(R.id.tvTransactionAmount);
        tvTransactionIdTujuan = findViewById(R.id.tvTransactionIdTujuan);
        tvTransactionPelanggan = findViewById(R.id.tvTransactionPelanggan);
        tvTransactionDate = findViewById(R.id.tvTransactionDate);
        tvTransactionID = findViewById(R.id.tvTransactionID);
        tvTransactionAmount2 = findViewById(R.id.tvTransactionAmount2);
        tvTransactionAdminFee = findViewById(R.id.tvTransactionAdminFee);
        tvTransactionTotal = findViewById(R.id.tvTransactionTotal);
    }


    private void initContentUI(){
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


        tvTransactionIdTujuan.setText("ID Tujuan : "+nameTujuanTransfer+" ("+numberContact+")");
        tvTransactionPelanggan.setText("Nama Pelanggan : ");

        tvTransactionTujuan.setText("Transfer ke "+nameTujuanTransfer);
        tvTransactionAmount.setText(nominalTransaction);

        tvTransactionDate.setText(dateTransaction);
        tvTransactionID.setText("ID Transaksi : "+referenceNumberTransaction);

        tvTransactionAmount2.setText(nominalTransaction);
        tvTransactionTotal.setText(nominalTransaction);
    }
}
