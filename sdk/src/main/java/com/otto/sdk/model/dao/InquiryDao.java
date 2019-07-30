package com.otto.sdk.model.dao;

import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.InquiryRequest;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import retrofit2.Callback;

public class InquiryDao extends BaseDao {
    public InquiryDao(IDao obj) {
        super(obj);
    }

    public void onInquiry(InquiryRequest model, BaseActivity ac, Callback callback) {
        Api.onInquiry(model, ac, callback);
    }
}