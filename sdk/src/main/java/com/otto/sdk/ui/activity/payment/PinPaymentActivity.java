package com.otto.sdk.ui.activity.payment;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IPinVerificationPaymentView;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.presenter.PinVerificationPaymentPresenter;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.component.support.DeviceId;
import com.otto.sdk.ui.component.support.Logging;
import com.otto.sdk.ui.component.support.UiUtil;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class PinPaymentActivity extends AppActivity implements IPinVerificationPaymentView {

    private final String TAG = this.getClass().getSimpleName();

    TextView tvPaymentValue;
    LinePinField lineField;
    TextView errorMessage;
    Button btnBack;

    int paymentValue;
    int user_id;

    private int emoneyBalance;
    private int nominalTransfer;
    private int grandTotal;

    private int total;
    private String numberContact;
    private String nominalTransferToFriend;
    private String nameContact;
    private String phone;
    private int statusPayment;

    private String pinTransferToFriend = "P2P";
    private String pinReviewCheckout = "ReviewCheckout";
    private String keyPinTransferToFriend;
    private String keyPinReviewCheckout;


    /*Receipt Transfer To Friend*/
    private PinVerificationPaymentPresenter pinVerificationPaymentPresenter;
    private String receiptReferenceNumber;
    private String receiptDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_payment);

        getLastKnownLocation();
        keyPinVerificationPayment();
        initComponent();
        addTextWatcher(lineField);
    }

    private void initComponent() {
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
        lineField = findViewById(R.id.lineField);
        errorMessage = findViewById(R.id.errorMessage);
        btnBack = findViewById(R.id.btn_back);

        total = CacheUtil.getPreferenceInteger(IConfig.SESSION_TOTAL, PinPaymentActivity.this);
        emoneyBalance = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, PinPaymentActivity.this));

        if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
            tvPaymentValue.setText(nominalTransferToFriend);
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
            nameContact = extras.getString(IConfig.KEY_NAME_CONTACT);
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
        model.setLatitude(String.valueOf(getMyLastLocation().getLatitude()));
        model.setLongitude(String.valueOf(getMyLastLocation().getLongitude()));
        model.setDeviceId(DeviceId.getDeviceID(this));

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

            if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
                grandTotal = UiUtil.removeAllCharacterNumbers(nominalTransferToFriend);
                if (emoneyBalance < grandTotal) {
                    errorMessage.setText("Saldo Anda tidak mencukupi");
                    btnBack.setVisibility(View.VISIBLE);
                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                } else {
                    onCallApiTransferToFriend();
                }

            } else if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
                paymentValue = emoneyBalance - total;

                Intent intent = new Intent(PinPaymentActivity.this, DashboardSDKActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        model.setAmount(UiUtil.removeAllCharacterExpectNumbers(nominalTransferToFriend));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new PinVerificationPaymentPresenter(this) {
            @Override
            public void call() {
                getTransferToFriend(model);

            }
        }, "Loading");
    }


    @Override
    public void handlePaymentTransferToFriend(TransferToFriendResponse model) {
        if (model.getMeta().getCode() == 200) {

            receiptReferenceNumber = model.getData().getReferenceNumber();
            receiptDate = model.getData().getDate();

            Intent intent = new Intent(PinPaymentActivity.this, PaymentSuccessActivity.class);
            intent.putExtra(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND, nominalTransferToFriend);
            intent.putExtra(IConfig.KEY_PIN_TRANSFER_TO_FRIEND, keyPinTransferToFriend);
            intent.putExtra(IConfig.KEY_PIN_CHECKOUT, keyPinReviewCheckout);
            intent.putExtra(IConfig.KEY_NAME_CONTACT, nameContact);
            intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);
            intent.putExtra(IConfig.KEY_REFERENCE_NUMBER_P2P, receiptReferenceNumber);
            intent.putExtra(IConfig.KEY_DATE_P2P, receiptDate);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(PaymentValidateResponse paymentValidateResponse) {

        Logging.response(TAG, new Gson().toJson(paymentValidateResponse));
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
                    hideSoftKeyboard(lineField);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}