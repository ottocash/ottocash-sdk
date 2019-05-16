package com.otto.sdk.presenter.dao;

import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.CreateTokenRequest;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import retrofit2.Callback;

public class CreateTokenDao extends BaseDao {
    public CreateTokenDao(Object obj) {
        super(obj);
    }

    public void onCreateToken(CreateTokenRequest model, BaseActivity ac, Callback callback) {
        Api.onCreateToken(model, ac, callback);
    }
}
