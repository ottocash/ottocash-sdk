package com.otto.ottocash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.otto.sdk.IConfig;
import com.otto.sdk.ui.activity.SdkActivity;

import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends SdkActivity {

    @BindView(R.id.btnNextWidget)
    Button btnNextWidget;
    @BindView(R.id.edt_phone)
    EditText edt_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initClientSendCredentialstoSDK();
    }

    private void initClientSendCredentialstoSDK() {
        String id = "b7f45e6bf8091a16107f9b524fe498fae1201c8d412fb61be380177eb383d4a7";
        CacheUtil.putPreferenceString(IConfig.SESSION_ID, id, LoginActivity.this);
        String secret = "c1b79f3316cd0f4f6240c75644d84a7de574be713ceaf8a8dd8a33f27c9f3594";
        CacheUtil.putPreferenceString(IConfig.SESSION_SECRET, secret, LoginActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.btnNextWidget)
    public void onNumberPhone() {

        String inputSubTotal = edt_phone.getText().toString();

        if (TextUtils.isEmpty(inputSubTotal)) {
            edt_phone.setError("Input Phone Number");
        } else {
            String phone = edt_phone.getText().toString();
            CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, LoginActivity.this);
            Intent intent = new Intent(LoginActivity.this, DashboardAppActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    }

}
