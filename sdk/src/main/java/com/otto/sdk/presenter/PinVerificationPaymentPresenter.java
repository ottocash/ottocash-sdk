package com.otto.sdk.presenter;

import com.otto.sdk.interfaces.IPinVerificationPaymentView;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.model.dao.PinVerificationPaymentDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.contract.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;


public class PinVerificationPaymentPresenter extends BasePresenter implements PinVerificationPaymentDao.IPinVerificationPaymentDao {

    private IPinVerificationPaymentView iPinVerificationPaymentView;

    public PinVerificationPaymentPresenter(IView view) {
        this.iPinVerificationPaymentView = (IPinVerificationPaymentView) view;
    }

    public void setCallback(IPinVerificationPaymentView callback) {
        this.iPinVerificationPaymentView = callback;
    }

    @Override
    public void getTransferToFriend(TransferToFriendRequest transferToFriendRequest) {
        new PinVerificationPaymentDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                TransferToFriendResponse model = (TransferToFriendResponse) br;
                iPinVerificationPaymentView.handlePaymentTransferToFriend(model);
            }
        }).onTransferToFriend(transferToFriendRequest, iPinVerificationPaymentView.getCurrentActivity());
    }

    @Override
    public void getPaymentValidate(PaymentValidateRequest paymentValidateRequest) {
        new PinVerificationPaymentDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                PaymentValidateResponse model = (PaymentValidateResponse) br;
                iPinVerificationPaymentView.handlePaymentValidate(model);
                iPinVerificationPaymentView.onSuccess(model);
            }
        }).onPaymentValidate(paymentValidateRequest, iPinVerificationPaymentView.getCurrentActivity());
    }

    @Override
    public BasePresenter getPresenter() {
        return BasePresenter.getInstance(iPinVerificationPaymentView,this);
    }

}
