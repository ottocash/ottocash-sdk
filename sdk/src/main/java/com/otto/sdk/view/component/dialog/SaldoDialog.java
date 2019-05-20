package com.otto.sdk.view.component.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseDialog;

public class SaldoDialog extends BaseDialog {

    TextView btnBack;
    Activity activity;
    Context context;

    public SaldoDialog(Context context, int appDialogFullScreen) {
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
