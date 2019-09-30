package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.ui.activity.payment.ReviewCheckoutActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOutActivity extends BaseActivity {

    private String BILL_PAYMENT = "bill_payment";
    private String SERVICES_FEE = "services_fee";
    private String billPayment;
    private String servicesFee;
    private String emoney;

    @BindView(R.id.PaymentOttoCash)
    LinearLayout PaymentOttoCash;

    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;

    @BindView(R.id.edtSubTotal)
    EditText edtSubTotal;

    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);

        addTextWatcher(edtSubTotal);
        initComponent();
        initContent();
    }

    private void initComponent() {
        PaymentOttoCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputSubTotal = edtSubTotal.getText().toString();

                if (TextUtils.isEmpty(inputSubTotal)) {
                    edtSubTotal.setError("Input Sample Sub Total");
                } else {
                    billPayment = edtSubTotal.getText().toString();
                    Intent intent = new Intent(CheckOutActivity.this, ReviewCheckoutActivity.class);
                    intent.putExtra(BILL_PAYMENT, billPayment);
                    intent.putExtra(SERVICES_FEE, servicesFee);
                    startActivity(intent);
                }
            }
        });
    }

    private void initContent() {
        emoney = CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, CheckOutActivity.this);
        tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(emoney)));
    }

    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputSubTotal = edtSubTotal.getText().toString();
                tvGrandTotal.setText(inputSubTotal);
            }

        });
    }


}