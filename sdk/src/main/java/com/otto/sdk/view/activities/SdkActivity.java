package com.otto.sdk.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.db.User;
import com.otto.sdk.presenter.dao.CheckPhoneNumberDao;
import com.otto.sdk.view.activities.account.activation.ActivationActivity;
import com.otto.sdk.view.activities.account.registration.RegistrationActivity;
import com.otto.sdk.view.activities.features.DashboardActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

public class SdkActivity extends BaseActivity {

    public static String PackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk);

    }


    public void onCallApiCheckPhoneNumber() {
        final CheckPhoneNumberDao dao = new CheckPhoneNumberDao(this);
        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();

        String phone = CacheUtil.getPreferenceString(String.valueOf(IConfig.SESSION_PHONE), SdkActivity.this);
        model.setPhone(phone);

        User user = new User(model.getPhone());
        CheckPhoneNumberDao.save(user, getBaseContext());

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new CheckPhoneNumberDao(this) {
            @Override
            public void call() {
                dao.onCheckPhoneNumber(model, SdkActivity.this, BaseDao.getInstance(SdkActivity.this,
                        IConfig.KEY_API_CHECK_PHONE_NUMBER).callback);
            }
        });
    }


    @Override
    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        super.onApiResponseCallback(br, responseCode, response);
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_CHECK_PHONE_NUMBER) {
                CheckPhoneNumberResponse data = (CheckPhoneNumberResponse) br;
                if (data.getMeta().getCode() == 200) {

                    boolean is_existing = data.getData().isIs_existing();
                    CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER)),
                            is_existing, SdkActivity.this);

                } else {
                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void goDashboardSDK() {
        Intent intent = new Intent(SdkActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goActivation() {
        Intent intent = new Intent(SdkActivity.this, ActivationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SdkActivity.this.startActivity(intent);
    }

    public void goRegistration() {
        Intent intent = new Intent(SdkActivity.this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SdkActivity.this.startActivity(intent);
    }

}