package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.presenter.TransferToFriendPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import app.beelabs.com.codebase.base.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class TransferToFriendDao extends BaseDao {

    private TransferToFriendPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private ITransferToFriendDao iTransferToFriendDao;

    public interface ITransferToFriendDao extends IDaoPresenter {
        void getTransferToFriend(TransferToFriendRequest transferToFriendRequest);
    }

    public TransferToFriendDao(IDao obj) {
        super(obj);
    }

    public TransferToFriendDao(TransferToFriendDao.ITransferToFriendDao iTransferToFriendDao, TransferToFriendPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iTransferToFriendDao = iTransferToFriendDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onTransferToFriend(TransferToFriendRequest transferToFriendRequest, Context context) {
        Api.onTransferToFriend(transferToFriendRequest, context, BaseDao.getInstance(this, iTransferToFriendDao.getPresenter(), IConfig
                .KEY_API_TRANSFER_TO_FRIEND).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_TRANSFER_TO_FRIEND) {
                TransferToFriendResponse transferToFriendResponse = (TransferToFriendResponse) br;
                onPresenterResponseCallback.call(transferToFriendResponse);
            }
        }
    }
}