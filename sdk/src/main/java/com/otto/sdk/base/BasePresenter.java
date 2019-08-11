package com.otto.sdk.base;

import com.otto.sdk.base.response.BaseResponse;

public class BasePresenter implements IPresenter {
    private static BasePresenter presenter;
    private static IView iview;


    public static BasePresenter getInstance(IView iv, BasePresenter bp) {
        iview = iv;
        presenter = bp;

        return presenter;
    }

    @Override
    public void call() {

    }

    @Override
    public void done() {
        ProgressDialogComponent.dismissProgressDialog(iview.getBaseActivity());
        LoadingDialogComponent.closeDialog(iview.getBaseActivity());

    }

    @Override
    public void fail(String message) {
        done();
        iview.handleFail(message);
    }


    public static class OnPresenterResponseCallback {

        public void call(BaseResponse br) {

        }

    }

}
