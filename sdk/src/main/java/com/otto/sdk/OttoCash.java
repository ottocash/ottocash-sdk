package com.otto.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.otto.sdk.ui.activity.account.activation.ActivationActivity;
import com.otto.sdk.ui.activity.account.registration.RegistrationActivity;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.activity.payment.ReviewCheckoutActivity;

import app.beelabs.com.codebase.support.util.CacheUtil;


public class OttoCash {

    private static final String BILL_PAYMENT = "bill_payment";
    public static final String OTTOCASH_PAYMENT_DATA = "paymentData";
    public static final int REQ_OTTOCASH_PAYMENT = 101;

    public static void onCallPayment(Activity activity, int amount) {
        if (onCheckIsActive(activity)) {
            activity.startActivity(new Intent(activity, DashboardSDKActivity.class));
        } else {
            onActivateAccount(activity);
        }
        Intent intent = new Intent(activity, ReviewCheckoutActivity.class);
        intent.putExtra(BILL_PAYMENT, String.valueOf(amount));
        activity.startActivityForResult(intent, REQ_OTTOCASH_PAYMENT);
    }

    public static void onActivateAccount(Context context) {
        boolean hasPhoneNumber = Boolean.parseBoolean(String.valueOf(CacheUtil.getPreferenceBoolean(String.valueOf(
                IConfig.SESSION_CHECK_PHONE_NUMBER), context)));
        if (hasPhoneNumber) {
            context.startActivity(new Intent(context, ActivationActivity.class));
        } else {
            context.startActivity(new Intent(context, RegistrationActivity.class));
        }
    }

    public static void onCallOttoCashDashboard(Context context) {
        if (onCheckIsActive(context)) {
            context.startActivity(new Intent(context, DashboardSDKActivity.class));
        } else {
            onActivateAccount(context);
        }
    }

    public static Boolean onCheckIsActive(Context context) {
        return !CacheUtil.getPreferenceString(IConfig.SESSION_EMONEY_BALANCE, context).isEmpty();
    }
}
