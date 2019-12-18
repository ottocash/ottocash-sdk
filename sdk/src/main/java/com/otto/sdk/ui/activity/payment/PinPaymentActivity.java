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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IPinVerificationPaymentView;
import com.otto.sdk.interfaces.IReviewCheckoutView;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.presenter.PinVerificationPaymentPresenter;
import com.otto.sdk.presenter.ReviewCheckoutPresenter;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.payment.otto.PaymentSuccessOttoActivity;
import com.otto.sdk.ui.component.support.DeviceId;
import com.otto.sdk.ui.component.support.Logging;
import com.otto.sdk.ui.component.support.UiUtil;
import com.poovam.pinedittextfield.LinePinField;

import java.util.Random;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static app.beelabs.com.codebase.support.util.CacheUtil.getPreferenceString;
import static com.otto.sdk.OttoCash.OTTOCASH_PAYMENT_DATA;

public class PinPaymentActivity extends AppActivity implements IPinVerificationPaymentView, IReviewCheckoutView {

    private final String TAG = this.getClass().getSimpleName();

    ImageView ivBack;
    TextView tvPaymentValue;
    LinePinField lineField;
    TextView errorMessage;
    Button btnBack;

    /*payment validate;*/
    private int user_id;
    private int emoneyBalance;
    private int grandTotal;

    /*transfer to friend*/
    private String numberContact;
    private String nominalTransferToFriend;
    private String nameTujuanTransfer;
    private String phone;

    /*key payment for receipt */
    private String pinTransferToFriend = "P2P";
    private String pinReviewCheckout = "ReviewCheckout";
    private String keyPinTransferToFriend;
    private String keyPinReviewCheckout;

    /*review checkout*/
    private int amount;
    private String customerReferenceNumber;
    private ReviewCheckOutRequest reviewCheckOutRequest;

    /*Receipt Transfer To Friend*/
    private PinVerificationPaymentPresenter pinVerificationPaymentPresenter;
    private String receiptReferenceNumber;
    private String receiptDate;

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
        setContentView(R.layout.activity_pin_payment);

        getLastKnownLocation();
        keyPinVerificationPayment();
        initComponent();
        addTextWatcher(lineField);

    }

    private void keyPinVerificationPayment() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            keyPinTransferToFriend = extras.getString(IConfig.KEY_PIN_TRANSFER_TO_FRIEND);
            keyPinReviewCheckout = extras.getString(IConfig.KEY_PIN_CHECKOUT);
            nominalTransferToFriend = extras.getString(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND);
            numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
            nameTujuanTransfer = extras.getString(IConfig.KEY_ACCOUNT_NAME_TUJUAN);
        }
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvPaymentValue = findViewById(R.id.tvPaymentValue);
        lineField = findViewById(R.id.lineField);
        errorMessage = findViewById(R.id.errorMessage);
        btnBack = findViewById(R.id.btn_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //total = CacheUtil.getPreferenceInteger(IConfig.SESSION_TOTAL, PinPaymentActivity.this);
        emoneyBalance = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE,
                PinPaymentActivity.this));
        amount = CacheUtil.getPreferenceInteger(IConfig.SESSION_TOTAL, PinPaymentActivity.this);

        if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
            tvPaymentValue.setText(nominalTransferToFriend);
        } else if (pinReviewCheckout.equals(keyPinReviewCheckout)) {
            tvPaymentValue.setText(UiUtil.formatMoneyIDR(amount));
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
                    errorMessage.setText(R.string.saldo_minus);
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
                onCallApiReviewCheckOut();
//
//                Intent intent = new Intent(PinPaymentActivity.this, DashboardSDKActivity.class);
//                intent.putExtra(IConfig.KEY_PIN_CHECKOUT, keyPinReviewCheckout);
//                startActivity(intent);
            }

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     * START REVIEW CHECKOUT
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (customerReferenceNumber != null) {
            customerReferenceNumber = (generateRandom(12) + "");
        }
    }

    @Override
    public BaseActivity getBaseActivity() {
        return PinPaymentActivity.this;
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    private void onCallApiReviewCheckOut() {
        reviewCheckOutRequest = new ReviewCheckOutRequest(String.valueOf(getPreferenceString(
                IConfig.SESSION_ACCOUNT_NUMBER, PinPaymentActivity.this)));
        reviewCheckOutRequest.setAmount(amount);
        reviewCheckOutRequest.setFee(0);
        reviewCheckOutRequest.setProductName("Pembayaran");
        reviewCheckOutRequest.setBillerId("PURCHASE_ELEVENIA");
        reviewCheckOutRequest.setCustomerReferenceNumber("UPN" + generateRandom(9) + "");
        reviewCheckOutRequest.setProductCode("PYMNT");
        reviewCheckOutRequest.setPartnerCode("P000001");
        reviewCheckOutRequest.setLatitude(String.valueOf(getMyLastLocation().getLatitude()));
        reviewCheckOutRequest.setLongitude(String.valueOf(getMyLastLocation().getLongitude()));
        reviewCheckOutRequest.setDeviceId(DeviceId.getDeviceID(this));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new ReviewCheckoutPresenter(this,this) {
            @Override
            public void call() {
                getReviewCheckout(reviewCheckOutRequest);

            }
        }, "Loading");
    }

    @Override
    public void handleReviewCheckout(ReviewCheckOutResponse model) {
        if (model.getMeta().getCode() == 200) {
            Intent intent = new Intent();
            intent.putExtra(OTTOCASH_PAYMENT_DATA,model.getData());
            setResult(RESULT_OK,intent);
            finish();

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

            dateTransaction = model.getData().getDate();
            serviceTypeTransaction = model.getData().getServiceType();
            nominalTransaction = model.getData().getNominal();
            destinationAccountNumberTransaction = model.getData().getDestinationAccountNumber();
            descriptionTransaction = model.getData().getDescription();
            referenceNumberTransaction = model.getData().getReferenceNumber();
            statusTransaction = model.getData().getStatus();

            Intent intent = new Intent(PinPaymentActivity.this, PaymentSuccessOttoActivity.class);
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