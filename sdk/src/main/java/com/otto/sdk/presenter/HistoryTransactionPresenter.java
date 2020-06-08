package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.interfaces.IHistoryView;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.model.dao.TransactionDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.contract.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

public class HistoryTransactionPresenter extends BasePresenter implements TransactionDao.IHistoryDao {

    private IHistoryView iHistoryView;

    public HistoryTransactionPresenter(IView view) {
        this.iHistoryView = (IHistoryView) view;
    }

    @Override
    public void getHistories(TransactionHistoryRequest requestModel) {
        new TransactionDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                TransactionHistoryResponse model = (TransactionHistoryResponse) br;
                iHistoryView.handleTransacionHistory(model);
            }
        }).onGetHistories(requestModel, iHistoryView.getCurrentActivity());
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

}
