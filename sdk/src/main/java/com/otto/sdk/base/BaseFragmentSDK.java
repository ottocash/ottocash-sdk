package com.otto.sdk.base;

import android.support.v4.app.Fragment;

import com.otto.sdk.R;
import com.otto.sdk.base.di.IProgressSDK;


public abstract class BaseFragmentSDK extends Fragment implements IViewSDK {


    protected void showApiProgressDialog(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter) {
        showApiProgressDialog(appComponentSDK, presenter, null);
    }

    protected void showApiProgressDialog(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter, String message) {
        IProgressSDK progress = appComponentSDK.getProgressDialog();
        progress.showProgressDialog(getActivity(), message, true);
        presenter.call();
    }

    protected void showApiProgressDialog(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter, String message, boolean isCanceledOnTouch) {
        IProgressSDK progress = appComponentSDK.getProgressDialog();
        progress.showProgressDialog(getActivity(), message, isCanceledOnTouch);
        presenter.call();
    }

    protected void showLoadingDialog(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter, String message) {
        IProgressSDK progress = appComponentSDK.getProgressDialog();
        progress.showLoadingDialog(new LoadingDialogComponentSDK(message, getActivity(), R.style.CoconutDialogFullScreen));
        presenter.call();
    }

    @Override
    public void handleFail(String message) {

    }
}
