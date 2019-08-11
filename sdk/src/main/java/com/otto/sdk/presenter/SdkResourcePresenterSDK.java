package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenterSDKSDK;
import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponseSDK;
import com.otto.sdk.model.api.response.CreateTokenResponseSDK;
import com.otto.sdk.model.dao.CheckPhoneNumberDaoSDK;
import com.otto.sdk.interfaces.ISdkViewSDK;


public class SdkResourcePresenterSDK extends BasePresenterSDKSDK implements CheckPhoneNumberDaoSDK.ICheckPhoneDao {

    private ISdkViewSDK sdkView;

    public SdkResourcePresenterSDK(IViewSDK view) {
        this.sdkView = (ISdkViewSDK) view;
    }

    @Override
    public void getCheckPhone(CheckPhoneNumberRequest requestModel) {
        BasePresenterSDKSDK.getInstance(sdkView, this);
        new CheckPhoneNumberDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                CheckPhoneNumberResponseSDK model = (CheckPhoneNumberResponseSDK) br;
                sdkView.handleCheckPhoneNumber(model);
            }
        }).onCheckPhoneNumber(requestModel);
    }

    @Override
    public void doCreateToken(CreateTokenRequest requestModel) {
        new CheckPhoneNumberDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                CreateTokenResponseSDK model = (CreateTokenResponseSDK) br;
                sdkView.handleToken(model);
            }
        }).onCreateToken(requestModel);
    }

    @Override
    public BasePresenterSDKSDK getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return sdkView.getBaseActivity();
    }
}
