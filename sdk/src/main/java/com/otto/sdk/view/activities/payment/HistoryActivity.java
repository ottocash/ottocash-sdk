package com.otto.sdk.view.activities.payment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.model.api.request.TransactionHistoryRequest;
import com.otto.sdk.model.api.response.TransactionHistoryResponse;
import com.otto.sdk.presenter.dao.TransactionDao;
import com.otto.sdk.view.activities.account.registration.RegistrationActivity;
import com.otto.sdk.view.adapter.HistoryAdapter;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

import static com.otto.sdk.IConfig.KEY_API_HISTORIES;

public class HistoryActivity extends BaseActivity {
    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private ImageView ivBack;

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
        final TransactionHistoryRequest model = new TransactionHistoryRequest();
        model.setAccount_number(CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, HistoryActivity.this));
        showApiProgressDialog(OttoCashSdk.getAppComponent(), new TransactionDao(HistoryActivity.this) {
            @Override
            public void call() {
                this.onGetHistories(HistoryActivity.this, model,
                        BaseDao.getInstance(HistoryActivity.this, KEY_API_HISTORIES).callback);
            }
        });
    }

    @Override
    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        super.onApiResponseCallback(br, responseCode, response);

        if (responseCode == KEY_API_HISTORIES) {
            if (response.code() == 200) {
                TransactionHistoryResponse data = (TransactionHistoryResponse) br;
                adapter.setData(data.getData().getTransaction().getHistories());
            }
        }
    }
}
