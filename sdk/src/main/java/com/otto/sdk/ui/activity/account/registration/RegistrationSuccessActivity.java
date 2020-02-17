package com.otto.sdk.ui.activity.account.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;

import app.beelabs.com.codebase.base.BaseActivity;

public class RegistrationSuccessActivity extends BaseActivity {

    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);

        initComponent();
        initContent();
    }

    private void initComponent() {
        btnFinish = findViewById(R.id.btnFinish);
    }

    private void initContent() {
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationSuccessActivity.this, DashboardSDKActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public BaseActivity getBaseActivity() {
        return RegistrationSuccessActivity.this;
    }
}
