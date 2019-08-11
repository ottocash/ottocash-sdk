package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenter;
import com.otto.sdk.base.IView;
import com.otto.sdk.base.response.BaseResponse;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.dao.CheckPhoneNumberDao;
import com.otto.sdk.interfaces.ISdkView;


public class SdkResourcePresenter extends BasePresenter implements CheckPhoneNumberDao.ICheckPhoneDao {

    private ISdkView sdkView;

    public SdkResourcePresenter(IView view) {
        this.sdkView = (ISdkView) view;
    }

    @Override
    public void getCheckPhone(CheckPhoneNumberRequest requestModel) {
        BasePresenter.getInstance(sdkView, this);
        new CheckPhoneNumberDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                CheckPhoneNumberResponse model = (CheckPhoneNumberResponse) br;
                sdkView.handleCheckPhoneNumber(model);
            }
        }).onCheckPhoneNumber(requestModel);
    }

    @Override
    public void doCreateToken(CreateTokenRequest requestModel) {
        new CheckPhoneNumberDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                CreateTokenResponse model = (CreateTokenResponse) br;
                sdkView.handleToken(model);
            }
        }).onCreateToken(requestModel);
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return sdkView.getBaseActivity();
    }
}
