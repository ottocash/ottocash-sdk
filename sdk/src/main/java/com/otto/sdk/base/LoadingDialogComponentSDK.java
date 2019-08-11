package com.otto.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.R;

import butterknife.ButterKnife;

public class LoadingDialogComponentSDK extends BaseDialogSDK {

    private String message;
    private static LoadingDialogComponentSDK dialog;
    private TextView text;


    public LoadingDialogComponentSDK(@NonNull String message, Context context, int style) {
        super(context, style);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setWindowContentDialogLayout(R.layout.dialog_loading);
        ButterKnife.bind(this);

        dialog = this;

//        text = (TextView) findViewById(R.id.loading_text);
        text.setText(message);
    }

    synchronized public static LoadingDialogComponentSDK showCustomProgressDialog(Context context, String message) {
        if (dialog == null) {
            message = message != null ? message : IConfigSDK.DEFAULT_LOADING;

            dialog = new LoadingDialogComponentSDK(message, context, R.style.CoconutDialogFullScreen);
            dialog.show();
        }

        return dialog;

    }

    public static void closeDialog(BaseActivitySDK ac) {
        if (dialog == null) return;
        if (ac == null || !ac.isFinishing()) {
            dialog.dismiss();
            dialog = null;
        }

    }
}
