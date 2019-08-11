package com.otto.sdk.base.di;

import android.content.Context;

import com.otto.sdk.base.LoadingDialogComponent;


public interface IProgress {
    void showProgressDialog(Context context, String message, boolean isCanceledOnTouch);

    void showLoadingDialog(LoadingDialogComponent dialog);
}

