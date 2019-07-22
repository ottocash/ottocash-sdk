package com.otto.sdk.presenter.dao;

import android.content.Context;

import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;

import app.beelabs.com.codebase.base.BaseDao;
import retrofit2.Call;
import retrofit2.Callback;

public class TransactionDao extends BaseDao {
    public TransactionDao(Object obj) {
        super(obj);
    }

    public void onGetHistories(Context context, TransactionHistoryRequest model, Callback callback){
        Api.onGetHistories(context,model,callback);
    }

}
