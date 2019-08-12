package com.otto.sdk.ui.activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IInquiryViewSDK;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponseSDK;
import com.otto.sdk.presenter.InquiryPresenterSDK;
import com.otto.sdk.support.UiUtil;
import com.otto.sdk.ui.activities.payment.HistoryActivitySDK;
import com.otto.sdk.ui.activities.payment.qr.PayWithQr;
import com.otto.sdk.ui.activities.tac.TACOttocashAndMitraActivitySDK;
import com.otto.sdk.ui.activities.topup.TopUpActivitySDK;
import com.otto.sdk.ui.component.dialog.CustomDialog;
import com.otto.sdk.ui.component.support.Util;


public class DashboardActivitySDK extends BaseActivitySDK implements IInquiryViewSDK {

    ImageView ivBack;
    TextView tvSaldoOttoCash;
    TextView tvName;
    ImageView imgPayments;
    ImageView imgTopUp;
    CardView cdPayments;
    CardView cdTopUp;
    CardView cdReqMoney;
    TextView webView;
    CardView cdTransfer;
    CardView cdHistory;

    private InquiryPresenterSDK inquiryPresenter;

    private InquiryRequest model;
    String emoneyBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initComponent();
        initContent();
        onCallApiInquiry();
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvSaldoOttoCash = findViewById(R.id.tvSaldoOttoCash);
        tvName = findViewById(R.id.tvName);
        imgPayments = findViewById(R.id.imgPayments);
        imgTopUp = findViewById(R.id.imgTopUp);
        cdPayments = findViewById(R.id.card_payment);
        cdTopUp = findViewById(R.id.card_top_up);
        webView = findViewById(R.id.webview);
        cdTransfer = findViewById(R.id.card_transfer);
        cdReqMoney = findViewById(R.id.card_req_money);
        cdHistory = findViewById(R.id.card_history);

        webView.setText(Util.getHTMLContent(getString(R.string.syarat_ketentuan)));
    }

    private void initContent() {
        cdPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySDK.this, PayWithQr.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                DashboardActivitySDK.this.startActivity(intent);
            }
        });

        cdTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySDK.this, TopUpActivitySDK.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                DashboardActivitySDK.this.startActivity(intent);
            }
        });

        cdHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySDK.this, HistoryActivitySDK.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySDK.this, TACOttocashAndMitraActivitySDK.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                DashboardActivitySDK.this.startActivity(intent);
            }
        });

        cdTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialogPlus();
            }
        });

        cdReqMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialogEmoney();
            }
        });

    }


    private void onCallApiInquiry() {

        model = new InquiryRequest(String.valueOf(CacheUtil.getPreferenceString(
                IConfigSDK.SESSION_PHONE, DashboardActivitySDK.this)));

        showApiProgressDialogSDK(OttoCashSDK.getAppComponentSDK(), new InquiryPresenterSDK(DashboardActivitySDK.this) {
            @Override
            public void call() {
                getInquiry(model);

            }
        }, "Loading");
    }


    protected void popUpDialogPlus() {
        String title = getString(R.string.dialog_title);
        String message = getString(R.string.dialog_msg);
        String btnLabel = getString(R.string.dialog_btn_close);

        CustomDialog.alertDialog(this, title, message, btnLabel, false);
    }

    public void showPopUpDialogPlus() {
        popUpDialogPlus();
    }

    public void showPopUpDialogEmoney() {
        popUpDialogEmoney();

    }

    protected void popUpDialogEmoney() {
        String title = getString(R.string.dialog_tittle_coming_soon);
        String message = getString(R.string.dialog_message_coming_soon);
        String btnLabel = getString(R.string.dialog_button_coming_soon);

        CustomDialog.alertDialog(this, title, message, btnLabel, false);

    }

    @Override
    public void onBackPressed() {
        try {

            String PackageName = CacheUtil.getPreferenceString(IConfigSDK.SESSION_PACKAGE_NAME,
                    DashboardActivitySDK.this);

            Intent intent = new Intent(DashboardActivitySDK.this, Class.forName(PackageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("EMONEY", emoneyBalance);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleInquiry(InquiryResponseSDK model) {
        if (model.getMeta().getCode() == 200) {

            emoneyBalance = model.getData().getEmoneyBalance();
            CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_EMONEY_BALANCE, Integer.parseInt(emoneyBalance), DashboardActivitySDK.this);

            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(model.getData().getEmoneyBalance())));
            tvName.setText("Hi " + model.getData().getName() + ". Saldo OttoCash Reguler Kamu");


        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}