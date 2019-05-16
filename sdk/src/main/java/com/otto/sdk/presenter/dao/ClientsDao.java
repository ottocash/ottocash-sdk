package com.otto.sdk.presenter.dao;

import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.ClientsRequest;

import retrofit2.Callback;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;

public class ClientsDao extends BaseDao {
    public ClientsDao(Object obj) {
        super(obj);
    }

    public void onClients(ClientsRequest model, BaseActivity ac, Callback callback) {
        Api.onClients(model, ac, callback);
    }
}