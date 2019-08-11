package com.otto.sdk.ui.activities.tac;

import android.os.Bundle;
import android.webkit.WebView;

import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivity;


public class TACMitraActivity extends BaseActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra);

        initView();
        displayTAC();
    }

    private void initView() {
        webView = findViewById(R.id.web_view);

    }

    private void displayTAC() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://ottocash.id/syarat-dan-ketentuan-otorisasi-layanan-dengan-mitra");
    }

}
