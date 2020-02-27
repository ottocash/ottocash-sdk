package com.otto.sdk.ui.activity.kycupgrade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IUpgradeView;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;
import com.otto.sdk.presenter.UpgradePresenter;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class UpgradeStatusActivity extends AppActivity implements IUpgradeView {

    Button btn_done;
    TextView tvStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_status);

        btn_done = findViewById(R.id.btn_done);
        tvStatus = findViewById(R.id.tvStatus);

        onCallApiUpgrade();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpgradeStatusActivity.this, DashboardSDKActivity.class);
                startActivity(intent);
            }
        });
    }


    private void onCallApiUpgrade(){

        String phone_number = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, this);
        String ktp = CacheUtil.getPreferenceString(IConfig.KEY_BASE64_KTP, this);
        String selfie_ktp = CacheUtil.getPreferenceString(IConfig.KEY_BASE64_SELFIE, this);

        final UpgradeAccountRequest upgradeAccountRequest = new UpgradeAccountRequest();
        upgradeAccountRequest.setAccount_number(phone_number);
        upgradeAccountRequest.setId_card(ktp);
        upgradeAccountRequest.setPassport_photo(selfie_ktp);

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new UpgradePresenter(UpgradeStatusActivity.this) {
            @Override
            public void call() {
                getUpgrade(upgradeAccountRequest, getContext());
            }
        }, "Loading");


    }

    @Override
    public void handleUpgrade(UpgradeAccountResponse model) {
        if (model.getMeta().getCode()==200){
            tvStatus.setText("Pengajuan OttoCash Plus telah Berhasil");
        }else {
            tvStatus.setText("Pengajuan OttoCash Plus telah Gagal");
        }

    }

}
