package com.otto.sdk.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.ClientsRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.ClientsResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.presenter.dao.CheckPhoneNumberDao;
import com.otto.sdk.view.activities.account.activation.ActivationActivity;
import com.otto.sdk.view.activities.account.registration.RegistrationActivity;
import com.otto.sdk.view.activities.dashboard.DashboardActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

public class SdkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk);

    }


    public void initializeSDK() {

        final CheckPhoneNumberDao dao = new CheckPhoneNumberDao(this);
        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();
//        final ClientsRequest clients = new ClientsRequest();

        String phone = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, SdkActivity.this);
        model.setPhone(phone);
//        clients.setEmail("ardi@clappingape.com");

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new CheckPhoneNumberDao(this) {
            @Override
            public void call() {
                dao.onCheckPhoneNumber(model, SdkActivity.this, BaseDao.getInstance(SdkActivity.this,
                        IConfig.KEY_API_CHECK_PHONE_NUMBER).callback);
//                dao.onClients(clients, SdkActivity.this, BaseDao.getInstance(SdkActivity.this,
//                        IConfig.KEY_API_CLIENTS).callback);
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

                    onCreateToken();

                } else {
                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
//            if (responseCode == IConfig.KEY_API_CLIENTS) {
//                ClientsResponse clientData = (ClientsResponse) br;
//                if (clientData.getMeta().getCode() == 200) {
//
//                    String id = clientData.getData().getClient().getId();
//                    CacheUtil.putPreferenceString(IConfig.SESSION_ID, id, SdkActivity.this);
//                    String secret = clientData.getData().getClient().getSecret();
//                    CacheUtil.putPreferenceString(IConfig.SESSION_SECRET, secret, SdkActivity.this);
//
//                    onCreateToken();
//
//                } else {
//                    Toast.makeText(this, clientData.getMeta().getCode() + ":" + clientData.getMeta().getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
            if (responseCode == IConfig.KEY_API_TOKEN) {
                CreateTokenResponse createToken = (CreateTokenResponse) br;
                if (createToken.getMeta().getCode() == 200) {

                    String accessToken = createToken.getData().getClient().getAccessToken();
                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCESS_TOKEN, accessToken, SdkActivity.this);
                } else {
                    Toast.makeText(this, createToken.getMeta().getCode() + ":" + createToken.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void onCreateToken() {
        final CheckPhoneNumberDao dao = new CheckPhoneNumberDao(this);
        final CreateTokenRequest token = new CreateTokenRequest();

        token.setGrantType("client_credentials");
        String id = CacheUtil.getPreferenceString(IConfig.SESSION_ID, SdkActivity.this);
        token.setClientId("31199fb491883361aab49e9e1210b6f0847d9bee83bce849062eeef234f12621");
        String secret = CacheUtil.getPreferenceString(IConfig.SESSION_SECRET, SdkActivity.this);
        token.setClientSecret("9ef53ece2353a5ae9497910a1de0c483608bdb75ede462407d78ad08ec4da49a");

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new CheckPhoneNumberDao(this) {
            @Override
            public void call() {
                dao.onCreateToken(token, SdkActivity.this, BaseDao.getInstance(SdkActivity.this,
                        IConfig.KEY_API_TOKEN).callback);
            }
        });
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