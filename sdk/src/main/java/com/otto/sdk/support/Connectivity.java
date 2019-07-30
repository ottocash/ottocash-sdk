package com.otto.sdk.support;

import android.content.Context;
import android.net.ConnectivityManager;

import com.otto.sdk.R;
import com.otto.sdk.ui.component.dialog.CustomDialog;

public class Connectivity {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        boolean isConnected = connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        if (!isConnected) {
            String title = context.getResources().getString(R.string.information);
            String message = context.getResources().getString(R.string.no_internet_connection);
            String btn = context.getResources().getString(R.string.close);
            CustomDialog.alertDialog(context, title, message, btn, false);
        }
        return isConnected;
    }
}