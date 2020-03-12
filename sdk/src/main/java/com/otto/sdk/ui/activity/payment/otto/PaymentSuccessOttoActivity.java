package com.otto.sdk.ui.activity.payment.otto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PaymentSuccessOttoActivity extends BaseActivity {

    ImageView ivBack;
    LinearLayout lyDetailTransaction;
    TextView tvPaymentValue;

    private int total;

    private String pinTransferToFriend = "P2P";
    private String pinReviewCheckout = "ReviewCheckout";
    private String keyPinTransferToFriend;
    private String keyPinReviewCheckout;

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
        setContentView(R.layout.activity_payment_success_otto);

        initComponent();
        contentUI();
        actionComponent();
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        lyDetailTransaction = findViewById(R.id.lyDetailTransaction);
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
    }

    private void contentUI() {
        total = CacheUtil.getPreferenceInteger(IConfig.OC_SESSION_TOTAL, PaymentSuccessOttoActivity.this);

        Bundle extras = getIntent().getExtras();
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

    private void actionComponent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lyDetailTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentSuccessOttoActivity.this, PaymentReceiptOttoActivity.class);

                intent.putExtra(IConfig.KEY_PIN_TRANSFER_TO_FRIEND, keyPinTransferToFriend);
                intent.putExtra(IConfig.KEY_PIN_CHECKOUT, keyPinReviewCheckout);
                intent.putExtra(IConfig.KEY_ACCOUNT_NAME_TUJUAN, nameTujuanTransfer);
                intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);

                // Data Transfer to Friend
                intent.putExtra(IConfig.DATE_TRANSACTION, dateTransaction);
                intent.putExtra(IConfig.SERVICE_TYPE_TRANSACTION, serviceTypeTransaction);
                intent.putExtra(IConfig.NOMINAL_TRANSACTION, nominalTransaction);
                intent.putExtra(IConfig.DESTINATION_ACCOUNT_NUMBER_TRANSACTION, destinationAccountNumberTransaction);
                intent.putExtra(IConfig.DESCRIPTION_TRANSACTION, descriptionTransaction);
                intent.putExtra(IConfig.REFERENCE_NUMBER_TRANSACTION, referenceNumberTransaction);
                intent.putExtra(IConfig.STATUS_TRANSACTION, statusTransaction);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

}
