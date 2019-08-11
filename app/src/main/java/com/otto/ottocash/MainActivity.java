package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.otto.sdk.IConfigSDK;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity {

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
        String id = "31199fb491883361aab49e9e1210b6f0847d9bee83bce849062eeef234f12621";
        CacheUtil.putPreferenceString(IConfigSDK.SESSION_ID, id, MainActivity.this);
        String secret = "9ef53ece2353a5ae9497910a1de0c483608bdb75ede462407d78ad08ec4da49a";
        CacheUtil.putPreferenceString(IConfigSDK.SESSION_SECRET, secret, MainActivity.this);
    }

    @OnClick(R.id.btnNextWidget)
    public void onNumberPhone() {

        String inputSubTotal = edt_phone.getText().toString();

        if (TextUtils.isEmpty(inputSubTotal)) {
            edt_phone.setError("Input Phone Number");
        } else {
            String phone = edt_phone.getText().toString();
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_PHONE, phone, MainActivity.this);
            Intent intent = new Intent(MainActivity.this, DashboardAppActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            MainActivity.this.startActivity(intent);
        }
    }


}
