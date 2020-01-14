package com.otto.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    public static OttoCash getInstance() {
        return new OttoCash();
    }

    public static void onCallPayment(Activity activity,String phoneNumber ,int amount) {
        if (SessionManager.getSessionLogin(activity)) {
//            activity.startActivity(new Intent(activity, DashboardSDKActivity.class));
            Intent intent = new Intent(activity, ReviewCheckoutActivity.class);
            intent.putExtra(BILL_PAYMENT, String.valueOf(amount));
            activity.startActivityForResult(intent, REQ_OTTOCASH_PAYMENT);
        } else {
//            onActivateAccount(activity);
            onCheckPhoneNumber(activity,phoneNumber);
        }
    }

    public static void onActivateAccount(Boolean hasPhoneNumber,Context context) {
//        boolean hasPhoneNumber = Boolean.parseBoolean(String.valueOf(CacheUtil.getPreferenceBoolean(String.valueOf(
//                IConfig.SESSION_CHECK_PHONE_NUMBER), context)));
        if (hasPhoneNumber) {
            context.startActivity(new Intent(context, ActivationActivity.class));
        } else {
            context.startActivity(new Intent(context, RegistrationActivity.class));
        }
    }

    public static void onCheckPhoneNumber(final Context context, String phoneNumber) {
        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
        model.setPhone(phoneNumber);
        new SdkResourcePresenter(new ISdkView() {
            @Override
            public void handleCheckPhoneNumber(CheckPhoneNumberResponse model) {
                if (model.getMeta().getCode() == 200) {
                    boolean is_existing = model.getData().isIs_existing();
                    CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER)),
                            is_existing, context);
            onActivateAccount(is_existing,context);

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
                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCESS_TOKEN, accessToken, context);

                } else {
                    Toast.makeText(context, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void handleFail(String message) {
handleFail(message);
            }

            @Override
            public BaseActivity getBaseActivity() {
                return null;
            }
        }).getCheckPhone(model);
//        showApiProgressDialog(OttoCashSdk.getAppComponent(), new SdkResourcePresenter(this) {
//            @Override
//            public void call() {
//                getCheckPhone(model);
////                loading.dismiss();
//            }
//        }, "Loading");
    }

    public void onGetAccountData(String account_number) {
        new InquiryPresenter(this).getInquiry(new InquiryRequest(account_number));
    }

    public static void onCallOttoCashDashboard(Context context,String phoneNumber) {
        if (onCheckIsActive(context)) {
            context.startActivity(new Intent(context, DashboardSDKActivity.class));
        } else {
//            onActivateAccount(context);
            onCheckPhoneNumber(context,phoneNumber);
        }
    }

    public static Boolean onCheckIsActive(Context context) {
        return !CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, context).isEmpty();
    }

    public static void onLogoutOttoCash(Activity activity) {
        SharedPreferences.Editor editor = activity.getSharedPreferences("dataSesi", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        SessionManager.putSessionLogin(false, activity);
    }

    @Override
    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {
            CacheUtil.putPreferenceString(IConfig.SESSION_EMONEY_BALANCE, model.getData().getEmoneyBalance(), this);
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleCheckPhoneNumber(CheckPhoneNumberResponse model) {

    }

    @Override
    public void handleToken(CreateTokenResponse model) {

    }


}
