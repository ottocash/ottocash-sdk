package com.otto.sdk.model.dao;

import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.base.BaseDao;
import com.otto.sdk.base.IDao;
import com.otto.sdk.model.api.Api;


import retrofit2.Callback;

public class SecurityDao extends BaseDao {
    public SecurityDao(IDao obj) {
        super(obj);
    }

    public void securityQuestionDAO(Callback callback) {
        Api.onSecurityQuestion(OttoCashSdk.getContext(), callback);
    }
}
