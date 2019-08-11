package com.otto.sdk.ui.activities.payment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivity;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IHistoryView;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.presenter.HistoryTransactionPresenter;
import com.otto.sdk.ui.adapter.HistoryAdapter;


import static com.otto.sdk.IConfig.SESSION_PHONE;

public class HistoryActivity extends BaseActivity implements IHistoryView {
    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private ImageView ivBack;
    private TransactionHistoryRequest model;
    private HistoryTransactionPresenter historyTransactionPresenter;

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
                CacheUtil.getPreferenceString(SESSION_PHONE, HistoryActivity.this));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new HistoryTransactionPresenter(this) {
            @Override
            public void call() {
                getHistories(model);

            }
        }, "Loading...");


        //        showApiProgressDialog(OttoCashSdk.getAppComponent(), new TransactionDao(HistoryActivity.this) {
//            @Override
//            public void call() {
//                this.onGetHistories(HistoryActivity.this, model,
//                        BaseDao.getInstance(HistoryActivity.this, KEY_API_HISTORIES).callback);
//            }
//        });
    }

//    @Override
//    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
//        super.onApiResponseCallback(br, responseCode, response);
//
//        if (responseCode == KEY_API_HISTORIES) {
//            if (response.code() == 200) {
//                TransactionHistoryResponse data = (TransactionHistoryResponse) br;
//                adapter.setData(data.getData().getTransaction().getHistories());
//            }
//        }
//    }

    @Override
    public void handleTransacionHistory(TransactionHistoryResponse model) {
        if (model.getMeta().getCode() == 200) {
            adapter.setData(model.getData().getTransaction().getHistories());
        }
    }
}