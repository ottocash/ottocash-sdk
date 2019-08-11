package com.otto.sdk.base.di.manager;


import android.content.Context;

import com.otto.sdk.base.LoadingDialogComponent;
import com.otto.sdk.base.ProgressDialogComponent;
import com.otto.sdk.base.di.IProgress;


public class ProgressManager implements IProgress {
    @Override
    public void showProgressDialog(Context context, String message, boolean isCanceledOnTouch) {
        ProgressDialogComponent.showProgressDialog(context, message, isCanceledOnTouch);
    }

    @Override
    public void showLoadingDialog(LoadingDialogComponent dialog) {
        dialog.show();
    }


}

