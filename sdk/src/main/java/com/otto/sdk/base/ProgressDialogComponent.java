package com.otto.sdk.base;

import android.app.ProgressDialog;
import android.content.Context;

import com.otto.sdk.IConfig;

public class ProgressDialogComponent {
    private static ProgressDialog dialog;

    synchronized public static ProgressDialog showProgressDialog(Context context, String message, boolean isCanceledOnTouch) {
        if (dialog == null) {
            message = message != null ? message : IConfig.DEFAULT_LOADING;

            dialog = new ProgressDialog(context);
            dialog.setMessage(message + "...");
            dialog.setCanceledOnTouchOutside(isCanceledOnTouch);
            dialog.show();
        }

        return dialog;
    }

    public static void dismissProgressDialog(BaseActivity ac) {
        if (dialog == null) return;
        if (ac == null || !ac.isFinishing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}

