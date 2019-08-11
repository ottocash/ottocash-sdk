package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.base.BaseDaoSDKSDK;
import com.otto.sdk.base.IDaoSDK;
import com.otto.sdk.base.IDaoPresenter;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.model.api.ApiSDK;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponseSDK;
import com.otto.sdk.presenter.HistoryTransactionPresenterSDK;

import retrofit2.Response;

public class TransactionDaoSDK extends BaseDaoSDKSDK {

    private HistoryTransactionPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback;
    private IHistoryDao iHistoryDao;

    public interface IHistoryDao extends IDaoPresenter {
        void getHistories(TransactionHistoryRequest requestModel);
    }

    public TransactionDaoSDK(IDaoSDK obj) {
        super(obj);
    }

    public TransactionDaoSDK(TransactionDaoSDK.IHistoryDao iHistoryDao, HistoryTransactionPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iHistoryDao = iHistoryDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onGetHistories(TransactionHistoryRequest model, Context context) {
        ApiSDK.onGetHistories(context, model, BaseDaoSDKSDK.getInstance(this, iHistoryDao.getPresenter(), IConfigSDK.KEY_API_HISTORIES).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfigSDK.KEY_API_HISTORIES) {
                TransactionHistoryResponseSDK transactionHistoryResponse = (TransactionHistoryResponseSDK) br;
                onPresenterResponseCallback.call(transactionHistoryResponse);
            }
        }
    }
}
