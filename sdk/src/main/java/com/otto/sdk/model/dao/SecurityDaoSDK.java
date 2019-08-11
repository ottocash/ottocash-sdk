package com.otto.sdk.model.dao;

import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.base.BaseDaoSDKSDK;
import com.otto.sdk.base.IDaoSDK;
import com.otto.sdk.model.api.ApiSDK;


import retrofit2.Callback;

public class SecurityDaoSDK extends BaseDaoSDKSDK {
    public SecurityDaoSDK(IDaoSDK obj) {
        super(obj);
    }

    public void securityQuestionDAO(Callback callback) {
        ApiSDK.onSecurityQuestion(OttoCashSDK.getContext(), callback);
    }
}
