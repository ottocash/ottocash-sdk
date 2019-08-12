package com.otto.sdk.base.di;

import android.content.Context;

import com.otto.sdk.base.LoadingDialogComponentSDK;


public interface IProgressSDK {
    void showProgressDialogSDK(Context context, String message, boolean isCanceledOnTouch);

    void showLoadingDialogSDK(LoadingDialogComponentSDK dialog);
}

