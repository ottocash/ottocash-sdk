package com.otto.sdk.ui.activity.tnc;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class TncOttoCashActivity extends BaseActivity {

    ImageView ivBack;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tac_ottocash);
        initView();
        displayTAC();
    }

    private void initView() {
        webView = findViewById(R.id.web_view);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void displayTAC() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://ottocash.id/terms_and_condition");

    }


}
