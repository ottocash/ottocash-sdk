package com.otto.sdk.base.di;

import android.content.Context;

import com.otto.sdk.base.LoadingDialogComponentSDK;


public interface IProgressSDK {
    void showProgressDialog(Context context, String message, boolean isCanceledOnTouch);

    void showLoadingDialog(LoadingDialogComponentSDK dialog);
}

