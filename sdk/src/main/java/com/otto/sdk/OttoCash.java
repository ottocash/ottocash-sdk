package com.otto.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.interfaces.ISdkView;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.presenter.SdkResourcePresenter;
import com.otto.sdk.presenter.manager.SessionManager;
import com.otto.sdk.ui.activity.SdkActivity;
import com.otto.sdk.ui.activity.account.activation.ActivationActivity;
import com.otto.sdk.ui.activity.account.registration.RegistrationActivity;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.payment.ReviewCheckoutActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;


public class OttoCash extends BaseActivity implements IInquiryView, ISdkView {

    private static final String BILL_PAYMENT = "bill_payment";
    public static final String OTTOCASH_PAYMENT_DATA = "paymentData";
    public static final int REQ_OTTOCASH_PAYMENT = 101;

    /**
     * Function Get Saldo OttoCash
     */
    public static String getBalance(Context context) {

        String saldo_ottocash = CacheUtil.getPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, context);

        return saldo_ottocash;
    }


    /**
     * account_number : 085880507999
     * amount : 55000
     * fee : 500
     * product_name : Pembayaran
     * biller_id : PURCHASE_ELEVENIA
     * customer_reference_number : UPN00d000458
     * product_code : PYMNT
     * partner_code : P000001
     * latitude : 10.232444
     * longitude : -6.4312323
     * device_id : 213123123123123
     */

    public static void onCallPayment(Activity activity, String phoneNumber, int amount, int fee, String productName, String billerId,
                                     String customerReferenceNumber, String productCode, String partnerCode) {
        if (SessionManager.getSessionLogin(activity)) {

            Intent intent = new Intent(activity, ReviewCheckoutActivity.class);
            intent.putExtra(BILL_PAYMENT, String.valueOf(amount));
            intent.putExtra(IConfig.PAYMENT_FEE, fee);
            intent.putExtra(IConfig.PAYMENT_PRODUCT_NAME, productName);
            intent.putExtra(IConfig.PAYMENT_BILLER_ID, billerId);
            intent.putExtra(IConfig.PAYMENT_CUSTOMER_RN, customerReferenceNumber);
            intent.putExtra(IConfig.PAYMENT_PRODUCT_CODE, productCode);
            intent.putExtra(IConfig.PAYMENT_PARTNER_CODE, partnerCode);
            activity.startActivityForResult(intent, REQ_OTTOCASH_PAYMENT);
        } else {
            onCheckPhoneNumber(activity, phoneNumber);
        }
    }

    private static void onActivateAccount(Boolean hasPhoneNumber, Context context) {
        if (hasPhoneNumber) {
            context.startActivity(new Intent(context, ActivationActivity.class));
        } else {
            context.startActivity(new Intent(context, RegistrationActivity.class));
        }
    }

    private static void onCheckPhoneNumber(final Context context, String phoneNumber) {
        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
        model.setPhone(phoneNumber);
        new SdkResourcePresenter(new ISdkView() {
            @Override
            public void handleError(String message) {

            }

            @Override
            public BaseActivity getCurrentActivity() {
                return null;
            }

            @Override
            public View getContentView() {
                return null;
            }

            @Override
            public void handleRetryConnection() {

            }

            @Override
            public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
                if (model.getMeta().getCode() == 200) {
                    boolean is_existing = model.getData().isIs_existing();
                    CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.OC_SESSION_CHECK_PHONE_NUMBER)),
                            is_existing, context);
                    onActivateAccount(is_existing, context);

                    Log.i("ISEX", "isExisting" + is_existing);

                } else {
                    Toast.makeText(context, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void handleToken(CreateTokenResponse model) {
                if (model.getMeta().getCode() == 200) {

                    String accessToken = model.getData().getClient().getAccessToken();
                    CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, accessToken, context);

                } else {
                    Toast.makeText(context, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }


        }).getCheckPhone(model);
    }

    private void onGetAccountData(String account_number) {
        new InquiryPresenter(this).getInquiry(new InquiryRequest());
    }

    /*public static void onCallOttoCashDashboard(Context context, String phoneNumber) {
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_PHONE, phoneNumber, context);
        if (onCheckIsActive(context)) {
            context.startActivity(new Intent(context, DashboardSDKActivity.class));
        } else {
//            onActivateAccount(context);
            onCheckPhoneNumber(context, phoneNumber);
        }
    }*/


    public static void onCallOttoCashDashboard(Context context) {
        Boolean checkIsExistingPhoneNumber = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_CHECK_PHONE_NUMBER, context);
        Boolean sessionLogin = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, context);
        Boolean session_active = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_IS_ACTIVE, context);
        Boolean session_need_otp = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_NEED_OTP, context);

        if (checkIsExistingPhoneNumber) {
            if (sessionLogin && session_active) {
                context.startActivity(new Intent(context, DashboardSDKActivity.class));
            } else {
                context.startActivity(new Intent(context, ActivationActivity.class));
            }
        } else {
            context.startActivity(new Intent(context, RegistrationActivity.class));
        }
    }

    public void goDashboardSDK() {
        Intent intent = new Intent(this, DashboardSDKActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private static Boolean onCheckIsActive(Context context) {
        return CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_IS_ACTIVE, context);
    }

    public static void onLogoutOttoCash(Context context) {
        CacheUtil.clearPreference(context);
        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_IS_ACTIVE, false, context);
        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, false, context);
        SessionManager.putSessionLogin(false, context);
    }

    @Override
    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, model.getData().getEmoney_balance(), this);
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {

    }

    @Override
    public void handleToken(CreateTokenResponse model) {

    }


}
