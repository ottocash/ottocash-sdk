package com.otto.sdk.base.di.manager;


import android.content.Context;

import com.otto.sdk.base.LoadingDialogComponentSDK;
import com.otto.sdk.base.ProgressDialogComponentSDK;
import com.otto.sdk.base.di.IProgressSDK;


public class ProgressManagerSDK implements IProgressSDK {
    @Override
    public void showProgressDialog(Context context, String message, boolean isCanceledOnTouch) {
        ProgressDialogComponentSDK.showProgressDialog(context, message, isCanceledOnTouch);
    }

    @Override
    public void showLoadingDialog(LoadingDialogComponentSDK dialog) {
        dialog.show();
    }


}

