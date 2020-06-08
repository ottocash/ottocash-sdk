package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.otto.ottocash.databinding.ActivityMainBinding;
import com.otto.sdk.IConfig;
import com.otto.sdk.ui.activity.SdkActivity;

import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends SdkActivity {

//    @BindView(R.id.btnLogin)
//    Button btnLogin;
//    @BindView(R.id.edt_phone)
//    EditText edt_phone;

    String phone_number;
    String client_id;
    String client_secret;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

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

    private void initCredentialsHostApp(){
        client_id = "b7f45e6bf8091a16107f9b524fe498fae1201c8d412fb61be380177eb383d4a7";
        client_secret = "c1b79f3316cd0f4f6240c75644d84a7de574be713ceaf8a8dd8a33f27c9f3594";

        CacheUtil.putPreferenceString(IConfig.OC_SESSION_CLIENT_ID, client_id, this);
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_CLIENT_SECRET, client_secret, this);

    }


    @OnClick(R.id.btnLogin)
    public void onNumberPhone() {
        phone_number = binding.edtPhone.getText().toString();
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_PHONE, phone_number, LoginActivity.this);

        if (TextUtils.isEmpty(phone_number)) {
            binding.edtPhone.setError("Input Phone Number");
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
