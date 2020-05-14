package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.presenter.SdkResourcePresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import app.beelabs.com.codebase.base.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class CheckPhoneNumberDao extends BaseDao {

    private SdkResourcePresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private ICheckPhoneDao cdao;

    public interface ICheckPhoneDao extends IDaoPresenter {
        void getCheckPhone(CheckPhoneNumberRequest requestModel);

        void doCreateToken(CreateTokenRequest requestModel);
    }

    public CheckPhoneNumberDao(IDao obj) {
        super(obj);
    }

    public CheckPhoneNumberDao(ICheckPhoneDao cdao, SdkResourcePresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.cdao = cdao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }


    public void onCheckPhoneNumber(CheckPhoneNumberRequest model, Context context) {
        Api.onCheckPhoneNumber(model, context, BaseDao.getInstance(this, cdao.getPresenter(), IConfig.KEY_API_CHECK_PHONE_NUMBER).callback);
    }


//    public void onClients(ClientsRequest model, BaseActivity ac, Callback callback) {
//        Api.onClients(model, ac, callback);
//    }

    public void onCreateToken(CreateTokenRequest model) {
        Api.onCreateToken(model, BaseDao.getInstance(this, cdao.getPresenter(), IConfig.KEY_API_TOKEN).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_CHECK_PHONE_NUMBER) {
                CheckPhoneNumberResponse model = (CheckPhoneNumberResponse) br;
                onPresenterResponseCallback.call(model);
            } else if (responseCode == IConfig.KEY_API_TOKEN) {
                CreateTokenResponse model = (CreateTokenResponse) br;
                onPresenterResponseCallback.call(model);
            }
        }
    }
}
