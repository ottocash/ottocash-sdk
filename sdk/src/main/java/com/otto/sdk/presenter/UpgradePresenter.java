package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.interfaces.IUpgradeView;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;
import com.otto.sdk.model.dao.UpgradeDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.contract.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

import static com.otto.sdk.OttoCashSdk.getContext;

public class UpgradePresenter  extends BasePresenter implements UpgradeDao.IUpgradeDao {

    private IUpgradeView iUpgrade;

    public UpgradePresenter(IView view) {
        this.iUpgrade = (IUpgradeView) view;
    }

    @Override
    public void getUpgrade(UpgradeAccountRequest upgradeAccountRequest) {
        new UpgradeDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                UpgradeAccountResponse model = (UpgradeAccountResponse) br;
                iUpgrade.handleUpgrade(model);
            }
        }).onUpgrade(upgradeAccountRequest, getContext());
    }

    @Override
    public BasePresenter getPresenter() {
        return BasePresenter.getInstance(iUpgrade, this);
    }
}
