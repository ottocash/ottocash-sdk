package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.otto.sdk.IConfig;

import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.edt_phone)
    EditText edt_phone;

    String phone_number;
    String client_id;
    String client_secret;
    String partner_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initCredentialsHostApp();
    }

    @Override
    protected void onResume() {
        initCredentialsHostApp();
        super.onResume();
    }

    @Override
    protected void onStart() {
        initCredentialsHostApp();
        super.onStart();
    }

    private void initCredentialsHostApp() {
        client_id = "knYbtc84tAJQfWj28UPgqPsGirr4607KFvzIfKU2_HY";
        client_secret = "ANwky-ms8rQQ_IyIIejiiTI91Mmx2g9IgRLa_mQaMQo";
        partner_id = "AAT";


        CacheUtil.putPreferenceString(IConfig.OC_SESSION_CLIENT_ID, client_id, this);
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_CLIENT_SECRET, client_secret, this);
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_PARTNER_ID, partner_id, this);

    }


    @OnClick(R.id.btnLogin)
    public void onNumberPhone() {
        phone_number = edt_phone.getText().toString();
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_PHONE, phone_number, LoginActivity.this);

        if (TextUtils.isEmpty(phone_number)) {
            edt_phone.setError("Input Phone Number");
        } else {
            Intent intent = new Intent(LoginActivity.this, DashboardAppActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
