package com.otto.sdk.ui.activities.account.activation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.otto.sdk.R;
import com.otto.sdk.ui.activities.dashboard.DashboardSDKActivity;

import app.beelabs.com.codebase.base.BaseActivity;

public class ActivationSuccessActivity extends BaseActivity {

    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_success);

        initComponent();
    }

    private void initComponent() {
        btnFinish = findViewById(R.id.btnBottom);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivationSuccessActivity.this, DashboardSDKActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ActivationSuccessActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}
