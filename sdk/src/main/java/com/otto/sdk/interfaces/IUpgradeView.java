package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.UpgradeAccountResponse;

import app.beelabs.com.codebase.base.contract.IView;


public interface IUpgradeView extends IView {

    void handleUpgrade(UpgradeAccountResponse model);

}
