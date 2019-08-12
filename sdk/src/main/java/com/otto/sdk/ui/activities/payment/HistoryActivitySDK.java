package com.otto.sdk.ui.activities.payment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IHistoryViewSDK;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponseSDK;
import com.otto.sdk.presenter.HistoryTransactionPresenterSDK;
import com.otto.sdk.ui.adapter.HistoryAdapter;


import static com.otto.sdk.IConfigSDK.SESSION_PHONE;

public class HistoryActivitySDK extends BaseActivitySDK implements IHistoryViewSDK {
    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private ImageView ivBack;
    private TransactionHistoryRequest model;
    private HistoryTransactionPresenterSDK historyTransactionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initVoid();
    }

    private void initVoid() {
        rvHistory = findViewById(R.id.rvHistory);
        ivBack = findViewById(R.id.ivBack);
        adapter = new HistoryAdapter(this);
        rvHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHistory.setAdapter(adapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        adapter.setData();
        callApiGetHistories();
    }

    private void callApiGetHistories() {
//        final TransactionHistoryRequest model = new TransactionHistoryRequest();

        model = new TransactionHistoryRequest(
                CacheUtil.getPreferenceString(SESSION_PHONE, HistoryActivitySDK.this));

        showApiProgressDialogSDK(OttoCashSDK.getAppComponentSDK(), new HistoryTransactionPresenterSDK(this) {
            @Override
            public void call() {
                getHistories(model);

            }
        }, "Loading...");


        //        showApiProgressDialogSDK(OttoCashSDK.getAppComponentSDK(), new TransactionDaoSDK(HistoryActivitySDK.this) {
//            @Override
//            public void call() {
//                this.onGetHistories(HistoryActivitySDK.this, model,
//                        BaseDaoSDKSDK.getInstance(HistoryActivitySDK.this, KEY_API_HISTORIES).callback);
//            }
//        });
    }

//    @Override
//    protected void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
//        super.onApiResponseCallback(br, responseCode, response);
//
//        if (responseCode == KEY_API_HISTORIES) {
//            if (response.code() == 200) {
//                TransactionHistoryResponseSDK data = (TransactionHistoryResponseSDK) br;
//                adapter.setData(data.getData().getTransaction().getHistories());
//            }
//        }
//    }

    @Override
    public void handleTransacionHistory(TransactionHistoryResponseSDK model) {
        if (model.getMeta().getCode() == 200) {
            adapter.setData(model.getData().getTransaction().getHistories());
        }
    }
}