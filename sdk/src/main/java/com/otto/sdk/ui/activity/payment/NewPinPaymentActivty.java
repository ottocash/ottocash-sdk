package com.otto.sdk.ui.activity.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.interfaces.IPinVerificationPaymentView;
import com.otto.sdk.interfaces.IReviewCheckoutView;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.presenter.PinVerificationPaymentPresenter;
import com.otto.sdk.presenter.ReviewCheckoutPresenter;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.payment.pede.PaymentSuccessPedeActivity;
import com.otto.sdk.ui.adapter.PinAdapter;
import com.otto.sdk.ui.component.support.DeviceId;
import com.otto.sdk.ui.component.support.Logging;
import com.otto.sdk.ui.component.support.UiUtil;
import com.otto.sdk.ui.view.PinCodeView;

import java.util.Random;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static com.otto.sdk.IConfig.OTTOCASH_PAYMENT_DATA_REFERENCE_NUMBER;
import static com.otto.sdk.IConfig.OTTOCASH_PAYMENT_DATA_STATUS;
import static com.otto.sdk.IConfig.OTTOCASH_PAYMENT_DATA_TRANSACTION_DATE;
import static com.otto.sdk.OttoCash.OTTOCASH_PAYMENT_DATA;


public class NewPinPaymentActivty extends AppActivity implements PinAdapter.Callback, IPinVerificationPaymentView, IReviewCheckoutView, IInquiryView {

    TextView pinCodeTitle;
    TextView desc;
    TextView textViewForgotPin;
    RecyclerView pinList;
    PinCodeView pinCodeView;

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


    private String saldo_ottocash;
    private InquiryPresenter inquiryPresenter;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pin_payment_activty);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Blue_4EA9F8));
        }
        pinCodeTitle = findViewById(R.id.pinCodeTitle);
        desc = findViewById(R.id.desc);
        textViewForgotPin = findViewById(R.id.textViewForgotPin);
        pinList = findViewById(R.id.pinList);
        pinCodeView = findViewById(R.id.relativeLayout);

        keyPinVerificationPayment();


        emoneyBalance = Integer.parseInt(CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE,
                NewPinPaymentActivty.this));
        amount = CacheUtil.getPreferenceInteger(IConfig.OC_SESSION_TOTAL, NewPinPaymentActivty.this);

        pinList.setLayoutManager(new GridLayoutManager(this, 3));
        pinList.setAdapter(new PinAdapter(this));
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

    @Override
    public void onUpdatePin(String digit) {
        if (pinCodeView.input(digit) == 6) {
            onCallApiPaymentValidate(pinCodeView.getCode());
        }
    }

    @Override
    public void onDeletePin() {
        pinCodeView.delete();
    }

    private void onCallApiReviewCheckOut() {
        String account_number = CacheUtil.getPreferenceString(IConfig.OC_SESSION_ACCOUNT_NUMBER, NewPinPaymentActivty.this);

        final ReviewCheckOutRequest reviewCheckOutRequest = new ReviewCheckOutRequest();

        reviewCheckOutRequest.setAccount_number(account_number);
        reviewCheckOutRequest.setAmount(amount);
        reviewCheckOutRequest.setFee(0);
        reviewCheckOutRequest.setProduct_name("Pembayaran");
        reviewCheckOutRequest.setBiller_id("PURCHASE_ELEVENIA");
        reviewCheckOutRequest.setCustomer_reference_number("UPN" + generateRandom(9) + "");
        reviewCheckOutRequest.setProduct_code("PYMNT");
        reviewCheckOutRequest.setPartner_code("P000001");
        reviewCheckOutRequest.setLatitude(String.valueOf(getMyLastLocation().getLatitude()));
        reviewCheckOutRequest.setLongitude(String.valueOf(getMyLastLocation().getLongitude()));
        reviewCheckOutRequest.setDevice_id(DeviceId.getDeviceID(this));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new ReviewCheckoutPresenter(this, this) {
            @Override
            public void call() {
                getReviewCheckout(reviewCheckOutRequest);

            }
        }, "Loading");
    }

    private void onCallApiPaymentValidate(final String pin) {

        final PaymentValidateRequest model = new PaymentValidateRequest();

        user_id = CacheUtil.getPreferenceInteger(IConfig.OC_SESSION_USER_ID, NewPinPaymentActivty.this);
        phone = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, NewPinPaymentActivty.this);

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

    /**
     * START TRANSFER TO FRIEND
     */
    private void onCallApiTransferToFriend() {

        final TransferToFriendRequest model = new TransferToFriendRequest();
        phone = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, NewPinPaymentActivty.this);

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

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    @Override
    public void handlePaymentValidate(PaymentValidateResponse model) {
        if (model.getMeta().getCode() == 200) {
            if (pinTransferToFriend.equals(keyPinTransferToFriend)) {
                grandTotal = UiUtil.removeAllCharacterNumbers(nominalTransferToFriend);
                if (emoneyBalance < grandTotal) {
//                    errorMessage.setText(R.string.saldo_minus);
//                    btnBack.setVisibility(View.VISIBLE);
//                    btnBack.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            onBackPressed();
//                        }
//                    });
                    Toast.makeText(NewPinPaymentActivty.this, model.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handlePaymentTransferToFriend(TransferToFriendResponse model) {
        if (model.getMeta().getCode() == 200) {

            receiptReferenceNumber = model.getData().getReference_number();
            receiptDate = model.getData().getDate();

            dateTransaction = model.getData().getDate();
            serviceTypeTransaction = model.getData().getService_type();
            nominalTransaction = model.getData().getNominal();
            destinationAccountNumberTransaction = model.getData().getDestination_account_number();
            descriptionTransaction = model.getData().getDescription();
            referenceNumberTransaction = model.getData().getReference_number();
            statusTransaction = model.getData().getStatus();

            Intent intent = new Intent(NewPinPaymentActivty.this, PaymentSuccessPedeActivity.class);
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
            finish();
        } else {
            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(PaymentValidateResponse paymentValidateResponse) {
        Logging.response(TAG, new Gson().toJson(paymentValidateResponse));

    }

    @Override
    public void handleReviewCheckout(ReviewCheckOutResponse model) {
        if (model.getMeta().getCode() == 200) {

            onCallApiInquiry();


            Intent intent = new Intent();
            intent.putExtra(OTTOCASH_PAYMENT_DATA, model.getData());
            CacheUtil.putPreferenceString(OTTOCASH_PAYMENT_DATA_STATUS, model.getData().getResponseDescription(), this);
            CacheUtil.putPreferenceString(OTTOCASH_PAYMENT_DATA_REFERENCE_NUMBER, model.getData().getReferenceNumber(), this);
            CacheUtil.putPreferenceString(OTTOCASH_PAYMENT_DATA_TRANSACTION_DATE, model.getData().getTransactionDate(), this);
            setResult(Activity.RESULT_OK, intent);
            finish();

        } else {
            Toast.makeText(this, model.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Call Api Inquiry
     */
    private void onCallApiInquiry() {
        String phone = String.valueOf(CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, NewPinPaymentActivty.this));
        final InquiryRequest inquiryRequest = new InquiryRequest();

        inquiryRequest.setAccount_number(phone);

        inquiryPresenter = ((InquiryPresenter) BasePresenter.getInstance(NewPinPaymentActivty.this, new InquiryPresenter(NewPinPaymentActivty.this)));
        inquiryPresenter.getInquiry(inquiryRequest);
    }

    @Override
    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {
            CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, true, NewPinPaymentActivty.this);
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCOUNT_NUMBER, model.getData().getAccount_number(), NewPinPaymentActivty.this);

            saldo_ottocash = model.getData().getEmoney_balance();
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, saldo_ottocash, NewPinPaymentActivty.this);
        }

    }
}
