package com.otto.sdk.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.BasePresenterSDKSDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.ISdkViewSDK;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.ClientsRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponseSDK;
import com.otto.sdk.model.api.response.CreateTokenResponseSDK;
import com.otto.sdk.presenter.SdkResourcePresenterSDK;
import com.otto.sdk.ui.activities.account.activation.ActivationActivitySDK;
import com.otto.sdk.ui.activities.account.registration.RegistrationActivitySDK;
import com.otto.sdk.ui.activities.dashboard.DashboardActivitySDK;


public class SdkActivitySDK extends BaseActivitySDK implements ISdkViewSDK {

    private SdkResourcePresenterSDK presenterSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk);

    }


    public void initializeSDK() {

        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
        final ClientsRequest clients = new ClientsRequest();
        clients.setEmail("ardi@clappingape.com");

        String phone = CacheUtil.getPreferenceString(IConfigSDK.SESSION_PHONE, SdkActivitySDK.this);
        model.setPhone(phone);

        presenterSDK = ((SdkResourcePresenterSDK) BasePresenterSDKSDK.getInstance(SdkActivitySDK.this, new SdkResourcePresenterSDK(SdkActivitySDK.this)));
        presenterSDK.getCheckPhone(model);

//        showApiProgressDialogSDK(OttoCashSDK.getAppComponentSDK(), new SdkResourcePresenterSDK(SdkActivitySDK.this) {
//            @Override
//            public void call() {
//                getCheckPhone(model);
//
//            }
//        }, "Loading...");
    }

    public void onCreateToken() {
        final CreateTokenRequest token = new CreateTokenRequest();

        token.setGrantType("client_credentials");
        String id = CacheUtil.getPreferenceString(IConfigSDK.SESSION_ID, SdkActivitySDK.this);
        token.setClientId(id);
        String secret = CacheUtil.getPreferenceString(IConfigSDK.SESSION_SECRET, SdkActivitySDK.this);
        token.setClientSecret(secret);

        presenterSDK = ((SdkResourcePresenterSDK) BasePresenterSDKSDK.getInstance(SdkActivitySDK.this, new SdkResourcePresenterSDK(SdkActivitySDK.this)));
        presenterSDK.doCreateToken(token);
    }


    public void goDashboardSDK() {
        Intent intent = new Intent(SdkActivitySDK.this, DashboardActivitySDK.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goActivation() {
        Intent intent = new Intent(SdkActivitySDK.this, ActivationActivitySDK.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SdkActivitySDK.this.startActivity(intent);
    }

    public void goRegistration() {
        Intent intent = new Intent(SdkActivitySDK.this, RegistrationActivitySDK.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SdkActivitySDK.this.startActivity(intent);
    }


    @Override
    public void handleCheckPhoneNumber(CheckPhoneNumberResponseSDK model) {
        if (model.getMeta().getCode() == 200) {
            boolean is_existing = model.getData().isIs_existing();
            CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfigSDK.SESSION_CHECK_PHONE_NUMBER)),
                    is_existing, SdkActivitySDK.this);

            onCreateToken();
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleToken(CreateTokenResponseSDK model) {
        if (model.getMeta().getCode() == 200) {

            String accessToken = model.getData().getClient().getAccessToken();
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_ACCESS_TOKEN, accessToken, SdkActivitySDK.this);

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}