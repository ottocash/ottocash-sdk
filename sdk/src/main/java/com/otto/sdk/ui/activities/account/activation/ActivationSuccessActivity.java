package com.otto.sdk.ui.activities.account.activation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivity;
import com.otto.sdk.ui.activities.dashboard.DashboardActivity;


public class ActivationSuccessActivity extends BaseActivity {

    Button btnBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_success);

        initComponent();
    }


    private void initComponent() {
        btnBottom = findViewById(R.id.btnBottom);
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivationSuccessActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ActivationSuccessActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}
