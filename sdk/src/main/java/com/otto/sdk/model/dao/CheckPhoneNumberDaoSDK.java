package com.otto.sdk.model.dao;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.base.BaseDaoSDKSDK;
import com.otto.sdk.base.IDaoSDK;
import com.otto.sdk.base.IDaoPresenter;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.model.api.ApiSDK;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponseSDK;
import com.otto.sdk.model.api.response.CreateTokenResponseSDK;
import com.otto.sdk.presenter.SdkResourcePresenterSDK;


import retrofit2.Response;

public class CheckPhoneNumberDaoSDK extends BaseDaoSDKSDK {

    private SdkResourcePresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback;
    private ICheckPhoneDao cdao;

    public interface ICheckPhoneDao extends IDaoPresenter {
        void getCheckPhone(CheckPhoneNumberRequest requestModel);

        void doCreateToken(CreateTokenRequest requestModel);
    }

    public CheckPhoneNumberDaoSDK(IDaoSDK obj) {
        super(obj);
    }

    public CheckPhoneNumberDaoSDK(ICheckPhoneDao cdao, SdkResourcePresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.cdao = cdao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }


    public void onCheckPhoneNumber(CheckPhoneNumberRequest model) {
        ApiSDK.onCheckPhoneNumber(model, BaseDaoSDKSDK.getInstance(this, cdao.getPresenter(), IConfigSDK.KEY_API_CHECK_PHONE_NUMBER).callback);
    }


//    public void onClients(ClientsRequest model, BaseActivitySDK ac, Callback callback) {
//        ApiSDK.onClients(model, ac, callback);
//    }

    public void onCreateToken(CreateTokenRequest model) {
        ApiSDK.onCreateToken(model, BaseDaoSDKSDK.getInstance(this, cdao.getPresenter(), IConfigSDK.KEY_API_TOKEN).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfigSDK.KEY_API_CHECK_PHONE_NUMBER) {
                CheckPhoneNumberResponseSDK model = (CheckPhoneNumberResponseSDK) br;
                onPresenterResponseCallback.call(model);
            } else if (responseCode == IConfigSDK.KEY_API_TOKEN) {
                CreateTokenResponseSDK model = (CreateTokenResponseSDK) br;
                onPresenterResponseCallback.call(model);
            }
        }
    }
}
