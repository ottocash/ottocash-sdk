package com.otto.sdk.ui.activity.account.activation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class ActivationBlockedActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked);
    }
}
