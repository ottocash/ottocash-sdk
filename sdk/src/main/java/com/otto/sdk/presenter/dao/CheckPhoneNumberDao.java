package com.otto.sdk.presenter.dao;

import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.ClientsRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import retrofit2.Callback;

public class CheckPhoneNumberDao extends BaseDao {
    public CheckPhoneNumberDao(Object obj) {
        super(obj);
    }

    public void onCheckPhoneNumber(CheckPhoneNumberRequest model, BaseActivity ac, Callback callback) {
        Api.onCheckPhoneNumber(model, ac, callback);
    }


    public void onClients(ClientsRequest model, BaseActivity ac, Callback callback) {
        Api.onClients(model, ac, callback);
    }

    public void onCreateToken(CreateTokenRequest model, BaseActivity ac, Callback callback) {
        Api.onCreateToken(model, ac, callback);
    }


}
