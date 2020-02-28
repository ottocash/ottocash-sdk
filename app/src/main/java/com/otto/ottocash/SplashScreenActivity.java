package com.otto.ottocash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.otto.sdk.IConfig;

import app.beelabs.com.codebase.support.util.CacheUtil;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler;
    private Boolean sessionLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        handler.postDelayed(this::onBoarding, 1000);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) handler = null;
    }

    private void onBoarding() {
        sessionLogin = CacheUtil.getPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY,  SplashScreenActivity.this);

        if (sessionLogin) {
            Intent intent = new Intent(this, DashboardAppActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
