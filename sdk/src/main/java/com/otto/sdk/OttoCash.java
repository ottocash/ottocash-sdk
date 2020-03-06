package com.otto.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.otto.sdk.interfaces.ISdkView;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.presenter.SdkResourcePresenter;
import com.otto.sdk.ui.activity.account.activation.ActivationActivity;
import com.otto.sdk.ui.activity.account.registration.RegistrationActivity;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.payment.ReviewCheckoutActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.IView;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static com.otto.sdk.IConfig.OC_SESSION_PHONE;

public class OttoCash extends BaseActivity implements ISdkView {

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
     * Function CheckOut Payment
     */
    public static void onCallPayment(Activity activity, String phoneNumber, int amount) {
        if (onCheckIsActive(activity)) {
            Intent intent = new Intent(activity, ReviewCheckoutActivity.class);
            intent.putExtra(BILL_PAYMENT, String.valueOf(amount));
            activity.startActivityForResult(intent, REQ_OTTOCASH_PAYMENT);
        } else {
            onCheckPhoneNumber((IView) activity, phoneNumber);
        }
    }


    /**
     * Function Check Phone Number go to Activation or Registration
     */
    public static void onActivateAccount(Boolean hasPhoneNumber, Context context) {
        if (hasPhoneNumber) {
            context.startActivity(new Intent(context, ActivationActivity.class));
        } else {
            context.startActivity(new Intent(context, RegistrationActivity.class));
        }
    }


    /**
     * Function GoTo DashBoard OttoCash SDK IF ?
     */
    public static void onCallOttoCashDashboard(Activity activity, String phoneNumber) {
        CacheUtil.putPreferenceString(OC_SESSION_PHONE, phoneNumber, activity);
        if (onCheckIsActive(activity)) {
            activity.startActivity(new Intent(activity, DashboardSDKActivity.class));
        } else {
            onCheckPhoneNumber((IView) activity, phoneNumber);
        }
    }

    private static Boolean onCheckIsActive(Context context) {
        return CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_IS_ACTIVE, context);
    }


    /**
     * Function Log Out
     */
    public static void onLogoutOttoCash(Context context) {
        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, false, context);
        CacheUtil.clearPreference(context);
    }


    /**
     * Call Api Check Account Number and Call Api Create Token
     */
    private static void onCheckPhoneNumber(final IView iView, String phoneNumber) {
        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
        model.setPhone(phoneNumber);
        new SdkResourcePresenter(iView).getCheckPhone(model);
//        new SdkResourcePresenter(new ISdkView() {
//            @Override
//            public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
//                if (model.getMeta().getCode() == 200) {
//                    boolean is_existing = model.getData().isIs_existing();
//                    CacheUtil.putPreferenceBoolean(String.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER), is_existing, context);
//                    onActivateAccount(is_existing, context);
//
//                    Log.i("ISEX", "isExisting" + is_existing);
//
//                } else {
//                    Toast.makeText(context, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void handleToken(CreateTokenResponse model) {
//                if (model.getMeta().getCode() == 200) {
//
//                    String accessToken = model.getData().getClient().getAccessToken();
//                    CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, accessToken, context);
//
//                } else {
//                    Toast.makeText(context, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void handleFail(String message) {
//                handleFail(message);
//            }
//
//            @Override
//            public BaseActivity getBaseActivity() {
//                return null;
//            }
//        }).getCheckPhone(model);
    }


//    @Override
//    public void handleInquiry(InquiryResponse model) {
//        if (model.getMeta().getCode() == 200) {
//            CacheUtil.putPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, model.getData().getEmoney_balance(), this);
//        } else {
//            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
//                    Toast.LENGTH_LONG).show();
//        }
//    }


    @Override
    public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
        if (model.getMeta().getCode() == 200) {
            boolean is_existing = model.getData().isIs_existing();
            CacheUtil.putPreferenceBoolean(String.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER), is_existing, this);
            onActivateAccount(is_existing, this);

            Log.i("ISEX", "isExisting" + is_existing);

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void handleToken(CreateTokenResponse model) {
        if (model.getMeta().getCode() == 200) {
            String accessToken = model.getData().getClient().getAccessToken();
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCESS_TOKEN, accessToken, this);

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
