package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.otto.sdk.IConfig;

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
    }

    @OnClick(R.id.btnNextWidget)
    public void onNumberPhone() {
        String phone = edt_phone.getText().toString();
        CacheUtil.putPreferenceString(String.valueOf(IConfig.SESSION_PHONE), phone, MainActivity.this);
        Intent intent = new Intent(MainActivity.this, DashboardAppActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.this.startActivity(intent);
    }
}
