package com.otto.sdk.ui.activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.support.UiUtil;
import com.otto.sdk.ui.activities.payment.HistoryActivity;
import com.otto.sdk.ui.activities.payment.qr.PayWithQr;
import com.otto.sdk.ui.activities.tac.TACOttocashAndMitraActivity;
import com.otto.sdk.ui.activities.topup.TopUpActivity;
import com.otto.sdk.ui.component.dialog.CustomDialog;
import com.otto.sdk.ui.component.support.Util;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class DashboardSDKActivity extends BaseActivity implements IInquiryView {

    ImageView ivBack;
    TextView tvSaldoOttoCash;
    TextView tvNameSDK;
    ImageView imgPayments;
    ImageView imgTopUp;
    CardView cdPayments;
    CardView cdTopUp;
    CardView cdReqMoney;
    TextView webView;
    CardView cdTransfer;
    CardView cdHistory;

    private InquiryPresenter inquiryPresenter;

    private InquiryRequest model;
    String emoneyBalance;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sdk);

        initComponent();
        initContent();
        onCallApiInquiry();
    }

    public void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvSaldoOttoCash = findViewById(R.id.tvSaldoOttoCash);
        tvNameSDK = findViewById(R.id.tvNameSDK);
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

    public void initContent() {
        cdPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSDKActivity.this, PayWithQr.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                DashboardSDKActivity.this.startActivity(intent);
            }
        });

        cdTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSDKActivity.this, TopUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                DashboardSDKActivity.this.startActivity(intent);
            }
        });

        cdHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSDKActivity.this, HistoryActivity.class);
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
                Intent intent = new Intent(DashboardSDKActivity.this, TACOttocashAndMitraActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                DashboardSDKActivity.this.startActivity(intent);
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
                IConfig.SESSION_PHONE, DashboardSDKActivity.this)));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new InquiryPresenter(DashboardSDKActivity.this) {
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


    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {

            emoneyBalance = model.getData().getEmoneyBalance();
            CacheUtil.putPreferenceInteger(IConfig.SESSION_EMONEY_BALANCE, Integer.parseInt(emoneyBalance), DashboardSDKActivity.this);

            name = model.getData().getName();
            CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, DashboardSDKActivity.this);

            tvNameSDK.setText("Hai " + name + ". Saldo OttoCash Reguler Kamu");
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(model.getData().getEmoneyBalance())));

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        try {

            String PackageName = CacheUtil.getPreferenceString(IConfig.SESSION_PACKAGE_NAME,
                    DashboardSDKActivity.this);

            Intent intent = new Intent(DashboardSDKActivity.this, Class.forName(PackageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("EMONEY", emoneyBalance);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}