package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCash;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.otto.sdk.OttoCash.REQ_OTTOCASH_PAYMENT;


public class CheckOutActivity extends AppActivity {

    //private String BILL_PAYMENT = "bill_payment";
    //private String SERVICES_FEE = "services_fee";
    private String billPayment;
    private String servicesFee;
    private String emoney;
    private Long amount = 0L;

    private TextWatcher mTextWatcher;

    @BindView(R.id.PaymentOttoCash)
    LinearLayout PaymentOttoCash;

    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;

    @BindView(R.id.edtSubTotal)
    EditText edtSubTotal;

    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);

        addTextWatcher(edtSubTotal);
        initComponent();
        initContent();

        emoney = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, CheckOutActivity.this);
        tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong((emoney))));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initComponent() {
        PaymentOttoCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputSubTotal = edtSubTotal.getText().toString();

                if (TextUtils.isEmpty(inputSubTotal)) {
                    edtSubTotal.setError("Input Sample Sub Total");
                } else {
                    String account_number = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, CheckOutActivity.this);
                    billPayment = UiUtil.removeCurrencyFormat(edtSubTotal.getText().toString());
                    CacheUtil.putPreferenceString(IConfig.TOTAL_BILL_PAYMENT, billPayment, CheckOutActivity.this);

                    OttoCash.onCallPayment(CheckOutActivity.this, account_number, Integer.parseInt(billPayment));
                    /*Intent intent = new Intent(CheckOutActivity.this, ReviewCheckoutActivity.class);
                    intent.putExtra(BILL_PAYMENT, billPayment);
                    intent.putExtra(SERVICES_FEE, servicesFee);
                    startActivity(intent);
                    finish();*/
                }
            }
        });
    }


    @Override
    protected void onResume() {
        emoney = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, CheckOutActivity.this);
        tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong((emoney))));
        super.onResume();
    }


    @Override
    protected void onStart() {
        emoney = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, CheckOutActivity.this);
        tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong((emoney))));
        super.onStart();
    }

    private void initContent() {
        try {
            emoney = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, CheckOutActivity.this);
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong((emoney))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void addTextWatcher(EditText input) {
//        input.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String inputSubTotal = edtSubTotal.getText().toString();
//                tvGrandTotal.setText(inputSubTotal);
//            }
//
//        });
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_OTTOCASH_PAYMENT) {

            emoney = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, CheckOutActivity.this);
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong((emoney))));

            String transactionStatus = CacheUtil.getPreferenceString(IConfig.OTTOCASH_PAYMENT_DATA_STATUS, this);
            String referenceNumber = CacheUtil.getPreferenceString(IConfig.OTTOCASH_PAYMENT_DATA_REFERENCE_NUMBER, this);
            String transactionDate = CacheUtil.getPreferenceString(IConfig.OTTOCASH_PAYMENT_DATA_TRANSACTION_DATE, this);

            Toast.makeText(CheckOutActivity.this, transactionStatus, Toast.LENGTH_LONG).show();
        }
    }

    public void addTextWatcher(EditText input) {
        mTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    edtSubTotal.removeTextChangedListener(mTextWatcher);
                    String currentInput = UiUtil.removeAllCharacterExpectNumbers(edtSubTotal.getText().toString());
                    if (currentInput.equals("")) {
                        edtSubTotal.setText(currentInput);
                        amount = 0L;
                    } else {
                        edtSubTotal.setText(UiUtil.numberToIDR(Long.valueOf(currentInput), true));
                        edtSubTotal.setSelection(edtSubTotal.getText().toString().length());
                        amount = Long.valueOf(UiUtil.removeCurrencyFormat(edtSubTotal.getText().toString()));
                    }
                    edtSubTotal.addTextChangedListener(mTextWatcher);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputSubTotal = edtSubTotal.getText().toString();
                tvGrandTotal.setText(inputSubTotal);
            }

        };

        input.addTextChangedListener(mTextWatcher);
    }

}