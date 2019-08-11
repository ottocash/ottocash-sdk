package com.otto.sdk.ui.activities.payment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IReviewCheckoutViewSDK;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponseSDK;
import com.otto.sdk.model.api.response.ReviewCheckOutResponseSDK;
import com.otto.sdk.presenter.ReviewCheckoutPresenterSDK;
import com.poovam.pinedittextfield.LinePinField;


public class PinActivitySDK extends BaseActivitySDK implements IReviewCheckoutViewSDK {

    LinePinField lineField;
    private PaymentValidateRequest model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        initComponent();
        addTextWatcher(lineField);
    }

    private void initComponent() {
        lineField = findViewById(R.id.lineField);
    }

    private void onCallApiSetPin(final String pin) {

        model = new PaymentValidateRequest(String.valueOf(CacheUtil.getPreferenceString(
                IConfigSDK.SESSION_PHONE, PinActivitySDK.this)));
        model.setPin(lineField.getText().toString());

        showApiProgressDialog(OttoCashSDK.getAppComponent(), new ReviewCheckoutPresenterSDK(this) {
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
    public void handleReviewCheckout(ReviewCheckOutResponseSDK model) {

    }

    @Override
    public void handlePaymentValidate(PaymentValidateResponseSDK model) {
        if (model.getMeta().getCode() == 200) {

            int emoney = CacheUtil.getPreferenceInteger(IConfigSDK.SESSION_EMONEY_BALANCE, PinActivitySDK.this);
            int total = CacheUtil.getPreferenceInteger(IConfigSDK.SESSION_TOTAL, PinActivitySDK.this);

            int emoneyBalance = emoney - total;
            CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_EMONEY_BALANCE, emoneyBalance, PinActivitySDK.this);

            Intent intent = new Intent(PinActivitySDK.this, CheckOutSuccessActivitySDK.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}