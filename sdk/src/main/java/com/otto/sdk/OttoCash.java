package com.otto.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.otto.sdk.ui.activity.payment.ReviewCheckoutActivity;


public class OttoCash {

    private static final String BILL_PAYMENT = "bill_payment";
    public static final String OTTOCASH_PAYMENT_DATA = "paymentData";
    public static final int REQ_OTTOCASH_PAYMENT = 101;

    public static void callPayment(Activity activity,int amount) {
        Intent intent = new Intent(activity, ReviewCheckoutActivity.class);
        intent.putExtra(BILL_PAYMENT, String.valueOf(amount));
        activity.startActivityForResult(intent, REQ_OTTOCASH_PAYMENT);
    }
}
