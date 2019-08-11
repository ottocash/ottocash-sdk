package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenterSDKSDK;
import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.interfaces.IHistoryViewSDK;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponseSDK;
import com.otto.sdk.model.dao.TransactionDaoSDK;


public class HistoryTransactionPresenterSDK extends BasePresenterSDKSDK implements TransactionDaoSDK.IHistoryDao {

    private IHistoryViewSDK iHistoryView;

    public HistoryTransactionPresenterSDK(IViewSDK view) {
        this.iHistoryView = (IHistoryViewSDK) view;
    }

    @Override
    public void getHistories(TransactionHistoryRequest requestModel) {
        new TransactionDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                TransactionHistoryResponseSDK model = (TransactionHistoryResponseSDK) br;
                iHistoryView.handleTransacionHistory(model);
            }
        }).onGetHistories(requestModel, getContext());
    }

    @Override
    public BasePresenterSDKSDK getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return iHistoryView.getBaseActivity();
    }
}
