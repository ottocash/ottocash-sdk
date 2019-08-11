package com.otto.sdk.ui.component.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.otto.sdk.R;
import com.otto.sdk.base.BaseDialogSDK;


public class SaldoDialogSDK extends BaseDialogSDK {

    TextView btnBack;
    Activity activity;
    Context context;

    public SaldoDialogSDK(Context context, int appDialogFullScreen) {
        super(context, appDialogFullScreen);
        this.context = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowContentDialogLayout(R.layout.dialog_saldo);

        initContent();
    }

    private void initContent() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
