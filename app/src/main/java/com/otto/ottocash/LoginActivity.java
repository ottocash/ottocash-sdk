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
    public static String PackageName;
    SharedPreferences sharedPreferences;
    String getDatasessi;
    String saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initClientSendCredentialstoSDK();
        sharedPreferences = getSharedPreferences("dataSesi", Context.MODE_PRIVATE);
        saldo = sharedPreferences.getString("saldo", null);

        getDatasessi = sharedPreferences.getString("session", null);

        String token = CacheUtil.getPreferenceString(IConfig.SESSION_ACCESS_TOKEN, LoginActivity.this);
        if (getDatasessi != null) {
            Intent intent = new Intent(LoginActivity.this, DashboardAppActivity.class);
            startActivity(intent);
            finish();

        }
    }

    private void initClientSendCredentialstoSDK() {
        String id = "31199fb491883361aab49e9e1210b6f0847d9bee83bce849062eeef234f12621";
        CacheUtil.putPreferenceString(IConfig.SESSION_ID, id, LoginActivity.this);
        String secret = "9ef53ece2353a5ae9497910a1de0c483608bdb75ede462407d78ad08ec4da49a";
        CacheUtil.putPreferenceString(IConfig.SESSION_SECRET, secret, LoginActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
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
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            LoginActivity.this.startActivity(intent);
        }
    }

    /*@OnClick(R.id.btnTransfer)
    public void onTransfer() {
        Intent intent = new Intent(LoginActivity.this, TransferToFriendActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }*/


}
