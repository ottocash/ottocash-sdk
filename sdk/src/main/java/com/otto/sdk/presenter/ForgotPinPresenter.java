package com.otto.sdk.presenter;

import com.otto.sdk.interfaces.IForgotPinView;
import com.otto.sdk.model.api.request.ForgotPinInquiryRequest;
import com.otto.sdk.model.api.request.ForgotPinRequest;
import com.otto.sdk.model.dao.ForgotPinDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.contract.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

public class ForgotPinPresenter extends BasePresenter implements ForgotPinDao.IForgotPinDao {

    private IForgotPinView forgotPinView;

    public ForgotPinPresenter(IView view) {
        this.forgotPinView = (IForgotPinView) view;
    }

    @Override
    public void getForgotPinInquiry(ForgotPinInquiryRequest requestModel) {
        new ForgotPinDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                BaseResponse model = (BaseResponse) br;
                forgotPinView.handleForgotPinInquiry(model);
            }
        }).onForgotPinInquiry(requestModel, forgotPinView.getCurrentActivity());
    }

    @Override
    public void getForgotPin(ForgotPinRequest requestModel) {
        new ForgotPinDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                BaseResponse model = (BaseResponse) br;
                forgotPinView.handleForgotPin(model);
            }
        }).onForgotPin(requestModel, forgotPinView.getCurrentActivity());
    }

    @Override
    public BasePresenter getPresenter() {
        return getInstance(forgotPinView, this);
    }
}
