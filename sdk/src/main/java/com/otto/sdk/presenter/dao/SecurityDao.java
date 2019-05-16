package com.otto.sdk.presenter.dao;

import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.model.api.Api;

import app.beelabs.com.codebase.base.BaseDao;
import retrofit2.Callback;

public class SecurityDao extends BaseDao {
    public SecurityDao(Object obj) {
        super(obj);
    }

    public void securityQuestionDAO(Callback callback) {
        Api.onSecurityQuestion(OttoCashSdk.getContext(), callback);
    }
}
