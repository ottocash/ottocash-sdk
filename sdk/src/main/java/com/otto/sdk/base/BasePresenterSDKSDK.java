package com.otto.sdk.base;

import com.otto.sdk.base.response.BaseResponseSDK;

public class BasePresenterSDKSDK implements IPresenterSDK {
    private static BasePresenterSDKSDK presenter;
    private static IViewSDK iview;


    public static BasePresenterSDKSDK getInstance(IViewSDK iv, BasePresenterSDKSDK bp) {
        iview = iv;
        presenter = bp;

        return presenter;
    }

    @Override
    public void call() {

    }

    @Override
    public void done() {
        ProgressDialogComponentSDK.dismissProgressDialog(iview.getBaseActivity());
        LoadingDialogComponentSDK.closeDialog(iview.getBaseActivity());

    }

    @Override
    public void fail(String message) {
        done();
        iview.handleFail(message);
    }


    public static class OnPresenterResponseCallback {

        public void call(BaseResponseSDK br) {

        }

    }

}
