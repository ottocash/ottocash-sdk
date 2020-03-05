package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;
import com.otto.sdk.presenter.UpgradePresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import app.beelabs.com.codebase.base.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class UpgradeDao extends BaseDao {

    private UpgradePresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IUpgradeDao idao;

    public interface IUpgradeDao extends IDaoPresenter {
        void getUpgrade(UpgradeAccountRequest upgradeAccountRequest);

    }

    public UpgradeDao(IDao obj){
        super(obj);
    }

    public UpgradeDao(IUpgradeDao idao, UpgradePresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.idao = idao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;

    }

    public void onUpgrade(UpgradeAccountRequest model, Context context) {
        Api.onUpgrade(model, context, BaseDao.getInstance(this, idao.getPresenter(), IConfig.KEY_API_UPGRADE).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_UPGRADE) {
                UpgradeAccountResponse upgradeAccountResponse = (UpgradeAccountResponse) br;
                onPresenterResponseCallback.call(upgradeAccountResponse);
            }
        }
    }

}

