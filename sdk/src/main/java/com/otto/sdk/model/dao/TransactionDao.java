package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.presenter.HistoryTransactionPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import app.beelabs.com.codebase.base.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class TransactionDao extends BaseDao {

    private HistoryTransactionPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IHistoryDao iHistoryDao;

    public interface IHistoryDao extends IDaoPresenter {
        void getHistories(TransactionHistoryRequest requestModel);
    }

    public TransactionDao(IDao obj) {
        super(obj);
    }

    public TransactionDao(TransactionDao.IHistoryDao iHistoryDao, HistoryTransactionPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iHistoryDao = iHistoryDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onGetHistories(TransactionHistoryRequest model, Context context) {
        Api.onGetHistories(context, model, BaseDao.getInstance(this, iHistoryDao.getPresenter(), IConfig.KEY_API_HISTORIES).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_HISTORIES) {
                TransactionHistoryResponse transactionHistoryResponse = (TransactionHistoryResponse) br;
                onPresenterResponseCallback.call(transactionHistoryResponse);
            }
        }
    }
}
