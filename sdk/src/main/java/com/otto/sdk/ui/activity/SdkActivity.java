package com.otto.sdk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.interfaces.ISdkView;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.presenter.SdkResourcePresenter;
import com.otto.sdk.ui.activity.account.activation.ActivationActivity;
import com.otto.sdk.ui.activity.account.registration.RegistrationActivity;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class SdkActivity extends BaseActivity implements ISdkView, IInquiryView {

    private String account_number;
    private SdkResourcePresenter presenterSDK;
    private CheckPhoneNumberResponse checkPhoneNumberResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk);

        checkIsExistingPhoneNumber();
    }


    public void goDashboardSDK() {
        Intent intent = new Intent(SdkActivity.this, DashboardSDKActivity.class);
        startActivity(intent);
    }

    public void goActivation() {
        Intent intent = new Intent(SdkActivity.this, ActivationActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SdkActivity.this.startActivity(intent);
    }

    public void goRegistration() {
        Intent intent = new Intent(SdkActivity.this, RegistrationActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SdkActivity.this.startActivity(intent);
    }


    /**
     * CHECK PHONE NUMBER IS EXISTING IN OTTOCASH
     */
    public void checkIsExistingPhoneNumber() {

        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();

        account_number = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, SdkActivity.this);
        model.setPhone(account_number);

        new InquiryPresenter(this).getInquiry(new InquiryRequest());
        showApiProgressDialog(OttoCashSdk.getAppComponent(), new SdkResourcePresenter(SdkActivity.this) {
            @Override
            public void call() {
                getCheckPhone(model);

            }
        }, "Loading");
    }


    /**
     * CCREATE TOKEN
     */
    public void onCreateToken() {

        String client_id = CacheUtil.getPreferenceString(IConfig.SESSION_ID, SdkActivity.this);
        String client_secret = CacheUtil.getPreferenceString(IConfig.SESSION_SECRET, SdkActivity.this);

        final CreateTokenRequest token = new CreateTokenRequest();
        token.setGrant_type("client_credentials");
        token.setClient_id(client_id);
        token.setClient_secret(client_secret);

        presenterSDK = ((SdkResourcePresenter) BasePresenter.getInstance(SdkActivity.this, new SdkResourcePresenter(SdkActivity.this)));
        presenterSDK.doCreateToken(token);
    }



    /**
     * RESPONSE REQUEST FROM CALL API
     */
    @Override
    public void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model) {
        checkPhoneNumberResponse = model;
        if (model.getMeta().getCode() == 200) {
            boolean is_existing = model.getData().isIs_existing();
            CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER)),
                    is_existing, SdkActivity.this);

            Log.i("IS_EX", "isExisting" + is_existing);

            onCreateToken();
        } else {
            //Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
            //       Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleToken(CreateTokenResponse model) {
        if (model.getMeta().getCode() == 200) {

            String accessToken = model.getData().getClient().getAccessToken();
            CacheUtil.putPreferenceString(IConfig.SESSION_ACCESS_TOKEN, accessToken, SdkActivity.this);

        } else {
            //Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
            //        Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {

            CacheUtil.putPreferenceString(IConfig.SESSION_EMONEY_BALANCE, model.getData().getEmoney_balance(), this);

        } else {
            //Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
            //        Toast.LENGTH_LONG).show();
        }
    }


}