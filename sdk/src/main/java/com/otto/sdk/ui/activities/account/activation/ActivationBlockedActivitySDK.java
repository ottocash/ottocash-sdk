package com.otto.sdk.ui.activities.account.activation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;


public class ActivationBlockedActivitySDK extends BaseActivitySDK {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked);
    }
}
