package com.otto.sdk.ui.activity.payment;

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
import com.otto.sdk.interfaces.IPinVerificationPaymentView;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.presenter.PinVerificationPaymentPresenter;
import com.otto.sdk.presenter.ReviewCheckoutPresenter;
import com.otto.sdk.presenter.SdkResourcePresenter;
import com.otto.sdk.ui.activity.SdkActivity;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.component.support.UiUtil;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class PinPaymentActivity extends BaseActivity implements IPinVerificationPaymentView {

    TextView tvPaymentValue;
    LinePinField lineField;
    TextView errorMessage;

    int paymentValue;
    int user_id;

    private int total;
    private String numberContact;
    private String nominalTransferToFriend;
    private String phone;

    private String pinTransferToFriend = "P2P";
    private String pinReviewCheckout = "ReviewCheckout";
    private String keyPinTransferToFriend;
    private String keyPinReviewCheckout;


    /*Receipt Transfer To Friend*/
    private PinVerificationPaymentPresenter pinVerificationPaymentPresenter;
    private String receiptDestinationAccountNumber;
    private String receiptNominal;
    private String receiptDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_payment);

        keyPinVerificationPayment();
        initComponent();
        addTextWatcher(lineField);
    }

    private void initComponent() {
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
        lineField = findViewById(R.id.lineField);
        errorMessage = findViewById(R.id.errorMessage);

        total = CacheUtil.getPreferenceInteger(IConfig.SESSION_TOTAL, PinPaymentActivity.this);

        if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
            tvPaymentValue.setText(UiUtil.formatMoneyIDR(Long.parseLong(nominalTransferToFriend)));
        } else if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
            tvPaymentValue.setText(UiUtil.formatMoneyIDR(total));
        }
    }

    private void keyPinVerificationPayment() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            keyPinTransferToFriend = extras.getString(IConfig.KEY_PIN_TRANSFER_TO_FRIEND);
            keyPinReviewCheckout = extras.getString(IConfig.KEY_PIN_CHECKOUT);
            nominalTransferToFriend = extras.getString(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND);
            numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
        }
    }

    /**
     * START PAYMENT VALIDATE
     */
    private void onCallApiPaymentValidate(final String pin) {

        final PaymentValidateRequest model = new PaymentValidateRequest();

        user_id = CacheUtil.getPreferenceInteger(IConfig.SESSION_USER_ID, PinPaymentActivity.this);
        phone = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, PinPaymentActivity.this);

        model.setUserId(user_id);
        model.setPhone(phone);
        model.setPin(pin);

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new PinVerificationPaymentPresenter(this) {
            @Override
            public void call() {
                getPaymentValidate(model);

            }
        }, "Loading");
    }

    @Override
    public void handlePaymentValidate(PaymentValidateResponse model) {
        if (model.getMeta().getCode() == 200) {
            int emoneyBalance = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, PinPaymentActivity.this));

            if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
                onCallApiTransferToFriend();
            } else if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
                paymentValue = emoneyBalance - total;
                Intent intent = new Intent(PinPaymentActivity.this, PaymentSuccessActivity.class);
                intent.putExtra(IConfig.KEY_PIN_CHECKOUT, keyPinReviewCheckout);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     * START TRANSFER TO FRIEND
     */
    private void onCallApiTransferToFriend() {

        final TransferToFriendRequest model = new TransferToFriendRequest();
        phone = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, PinPaymentActivity.this);

        model.setAccountNumber(phone);
        model.setCustomerReference(numberContact);
        model.setAmount(nominalTransferToFriend);

        pinVerificationPaymentPresenter.getTransferToFriend(model);
        /*showApiProgressDialog(OttoCashSdk.getAppComponent(), new PinVerificationPaymentPresenter(this) {
            @Override
            public void call() {
                getTransferToFriend(model);

            }
        }, "Loading");*/
    }


    @Override
    public void handlePaymentTransferToFriend(TransferToFriendResponse model) {
        if (model.getMeta().getCode() == 200) {

            receiptDestinationAccountNumber = model.getData().getDestinationAccountNumber();
            receiptNominal = model.getData().getNominal();
            receiptDate = model.getData().getDate();

            Intent intent = new Intent(PinPaymentActivity.this, PaymentSuccessActivity.class);
            intent.putExtra(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND, nominalTransferToFriend);
            intent.putExtra(IConfig.KEY_PIN_TRANSFER_TO_FRIEND, keyPinTransferToFriend);
            intent.putExtra(IConfig.KEY_PIN_CHECKOUT, keyPinReviewCheckout);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    onCallApiPaymentValidate(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }
}