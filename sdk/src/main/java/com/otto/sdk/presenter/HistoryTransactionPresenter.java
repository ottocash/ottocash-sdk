package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenter;
import com.otto.sdk.base.IView;
import com.otto.sdk.base.response.BaseResponse;
import com.otto.sdk.interfaces.IHistoryView;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.model.dao.TransactionDao;



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
        }).onGetHistories(requestModel, getContext());
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return iHistoryView.getBaseActivity();
    }
}
