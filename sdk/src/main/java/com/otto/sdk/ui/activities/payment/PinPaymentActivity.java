package com.otto.sdk.ui.activities.payment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IReviewCheckoutView;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.presenter.ReviewCheckoutPresenter;
import com.otto.sdk.support.UiUtil;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PinPaymentActivity extends BaseActivity implements IReviewCheckoutView {

    TextView tvPaymentValue;
    LinePinField lineField;
    private PaymentValidateRequest model;

    int emoneyBalance;
    int total;
    int paymentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_payment);

        initComponent();
        addTextWatcher(lineField);
    }

    private void initComponent() {
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
        lineField = findViewById(R.id.lineField);
    }

    private void onCallApiSetPin(final String pin) {

        model = new PaymentValidateRequest(String.valueOf(CacheUtil.getPreferenceString(
                IConfig.SESSION_PHONE, PinPaymentActivity.this)));
        model.setPin(lineField.getText().toString());

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new ReviewCheckoutPresenter(this) {
            @Override
            public void call() {
                getPaymentValidate(model);

            }
        }, "Loading");
    }


    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    onCallApiSetPin(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    @Override
    public void handleReviewCheckout(ReviewCheckOutResponse model) {

    }

    @Override
    public void handlePaymentValidate(PaymentValidateResponse model) {
        if (model.getMeta().getCode() == 200) {

            emoneyBalance = CacheUtil.getPreferenceInteger(IConfig.SESSION_EMONEY_BALANCE, PinPaymentActivity.this);
            total = CacheUtil.getPreferenceInteger(IConfig.SESSION_TOTAL, PinPaymentActivity.this);
            tvPaymentValue.setText(UiUtil.formatMoneyIDR(Long.parseLong(String.valueOf(total))));

            paymentValue = emoneyBalance - total;

            Intent intent = new Intent(PinPaymentActivity.this, CheckOutSuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}