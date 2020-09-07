package com.otto.sdk.presenter;

import com.otto.sdk.interfaces.IUpgradeView;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;
import com.otto.sdk.model.dao.UpgradeDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.contract.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

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
        }).onUpgrade(upgradeAccountRequest, iUpgrade.getCurrentActivity());
    }

    @Override
    public BasePresenter getPresenter() {
        return BasePresenter.getInstance(iUpgrade, this);
    }

}
