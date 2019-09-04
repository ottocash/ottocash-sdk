package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.interfaces.ITransferToFriendView;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.model.dao.TransferToFriendDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

public class TransferToFriendPresenter extends BasePresenter implements TransferToFriendDao.ITransferToFriendDao {

    private ITransferToFriendView iTransferToFriendView;

    public TransferToFriendPresenter(IView view){
        this.iTransferToFriendView = (ITransferToFriendView) view;
    }

    @Override
    public void getTransferToFriend(TransferToFriendRequest transferToFriendRequest) {
        new TransferToFriendDao(this, new OnPresenterResponseCallback(){
            @Override
            public void call(BaseResponse br) {
                TransferToFriendResponse model = (TransferToFriendResponse) br;
                iTransferToFriendView.handleResponseTransferToFriend(model);
            }
        }).onTransferToFriend(transferToFriendRequest, getContext());
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return iTransferToFriendView.getBaseActivity();
    }
}
