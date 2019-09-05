package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.presenter.PinVerificationPaymentPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import app.beelabs.com.codebase.base.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class PinVerificationPaymentDao extends BaseDao {

    private PinVerificationPaymentPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IPinVerificationPaymentDao iPinVerificationPaymentDao;

    public interface IPinVerificationPaymentDao extends IDaoPresenter {
        void getTransferToFriend(TransferToFriendRequest transferToFriendRequest);

        void getPaymentValidate(PaymentValidateRequest paymentValidateRequest);
    }

    public PinVerificationPaymentDao(IDao obj) {
        super(obj);
    }

    public PinVerificationPaymentDao(PinVerificationPaymentDao.IPinVerificationPaymentDao iPinVerificationPaymentDao, PinVerificationPaymentPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iPinVerificationPaymentDao = iPinVerificationPaymentDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onPaymentValidate(PaymentValidateRequest model, Context context) {
        Api.onPaymentValidate(model, context, BaseDao.getInstance(this, iPinVerificationPaymentDao.getPresenter(),
                IConfig.KEY_API_PAYMENT_VALIDATE).callback);
    }

    public void onTransferToFriend(TransferToFriendRequest transferToFriendRequest, Context context) {
        Api.onTransferToFriend(transferToFriendRequest, context, BaseDao.getInstance(this, iPinVerificationPaymentDao.getPresenter(), IConfig
                .KEY_API_TRANSFER_TO_FRIEND).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_TRANSFER_TO_FRIEND) {
                TransferToFriendResponse transferToFriendResponse = (TransferToFriendResponse) br;
                onPresenterResponseCallback.call(transferToFriendResponse);
            } else if (responseCode == IConfig.KEY_API_PAYMENT_VALIDATE) {
                PaymentValidateResponse paymentValidateResponse = (PaymentValidateResponse) br;
                onPresenterResponseCallback.call(paymentValidateResponse);
            }
        }
    }
}