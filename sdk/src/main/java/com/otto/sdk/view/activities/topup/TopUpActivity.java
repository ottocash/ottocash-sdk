package com.otto.sdk.view.activities.topup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.view.View;
import android.widget.TextView;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.view.activities.account.activation.ActivationActivity;
import com.otto.sdk.view.component.support.Util;
import com.otto.sdk.view.activities.dashboard.DashboardActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class TopUpActivity extends BaseActivity {

    BottomAppBar btnBottom;
    TextView tvMbank3;
    TextView tvMbank5;
    TextView tvAtm4;
    TextView tvAtm5;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        initComponent();
        initContent();
    }

    private void initComponent() {

        String phone = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, TopUpActivity.this);


        btnBottom = findViewById(R.id.btnBottom);
        tvAtm4 = findViewById(R.id.tvAtm4);
        tvAtm5 = findViewById(R.id.tvAtm5);

        tvMbank3 = findViewById(R.id.tvMbank3);
        tvMbank5 = findViewById(R.id.tvMbank5);

        tvAtm4.setText(Util.getHTMLContent(getString(R.string.atm_4)));
        tvAtm5.setText((getString(R.string.atm_5) + phone));
        tvMbank3.setText(Util.getHTMLContent(getString(R.string.mbank_3)));

        tvMbank5.setText((getString(R.string.mbank_5) + phone));
    }

    private void initContent() {
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopUpActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                TopUpActivity.this.startActivity(intent);
            }
        });
    }

}
