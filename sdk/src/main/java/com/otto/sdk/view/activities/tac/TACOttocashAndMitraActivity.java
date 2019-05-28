package com.otto.sdk.view.activities.tac;

import android.os.Bundle;
import android.webkit.WebView;

import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class TACOttocashAndMitraActivity extends BaseActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacottocash_and_mitra);
        initView();
        displayTAC();

    }

    private void initView() {
        webView = findViewById(R.id.web_view);
    }

    private void displayTAC() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://ottocash.id/syarat-dan-ketentuan-gabungan");
    }

}
