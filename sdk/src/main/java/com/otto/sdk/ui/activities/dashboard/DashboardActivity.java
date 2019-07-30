//package com.otto.sdk.ui.activities.dashboard;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.CardView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.otto.sdk.IConfig;
//import com.otto.sdk.OttoCashSdk;
//import com.otto.sdk.R;
//import com.otto.sdk.model.api.request.InquiryRequest;
//import com.otto.sdk.model.api.response.InquiryResponse;
//import com.otto.sdk.model.dao.InquiryDao;
//import com.otto.sdk.support.UiUtil;
//import com.otto.sdk.ui.activities.payment.HistoryActivity;
//import com.otto.sdk.ui.activities.tac.TACOttocashAndMitraActivity;
//import com.otto.sdk.ui.component.dialog.CustomDialog;
//import com.otto.sdk.ui.component.support.Util;
//import com.otto.sdk.ui.activities.payment.qr.PayWithQr;
//import com.otto.sdk.ui.activities.topup.TopUpActivity;
//
//import app.beelabs.com.codebase.base.BaseActivity;
//import app.beelabs.com.codebase.base.BaseDao;
//import app.beelabs.com.codebase.base.response.BaseResponse;
//import app.beelabs.com.codebase.support.util.CacheUtil;
//import retrofit2.Response;
//
//public class DashboardActivity extends BaseActivity {
//
//    ImageView ivBack;
//    TextView tvSaldoOttoCash;
//    TextView tvName;
//    ImageView imgPayments;
//    ImageView imgTopUp;
//    CardView cdPayments;
//    CardView cdTopUp;
//    CardView cdReqMoney;
//    TextView webView;
//    CardView cdTransfer;
//    CardView cdHistory;
//
//    private InquiryRequest model;
//    String emoneyBalance;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//
//        initComponent();
//        initContent();
//        onCallApiInquiry();
//    }
//
//    private void initComponent() {
//        ivBack = findViewById(R.id.ivBack);
//        tvSaldoOttoCash = findViewById(R.id.tvSaldoOttoCash);
//        tvName = findViewById(R.id.tvName);
//        imgPayments = findViewById(R.id.imgPayments);
//        imgTopUp = findViewById(R.id.imgTopUp);
//        cdPayments = findViewById(R.id.card_payment);
//        cdTopUp = findViewById(R.id.card_top_up);
//        webView = findViewById(R.id.webview);
//        cdTransfer = findViewById(R.id.card_transfer);
//        cdReqMoney = findViewById(R.id.card_req_money);
//        cdHistory = findViewById(R.id.card_history);
//
//        webView.setText(Util.getHTMLContent(getString(R.string.syarat_ketentuan)));
//    }
//
//    private void initContent() {
//        cdPayments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, PayWithQr.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                DashboardActivity.this.startActivity(intent);
//            }
//        });
//
//        cdTopUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, TopUpActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                DashboardActivity.this.startActivity(intent);
//            }
//        });
//
//        cdHistory.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        webView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, TACOttocashAndMitraActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                DashboardActivity.this.startActivity(intent);
//            }
//        });
//
//        cdTransfer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopUpDialogPlus();
//            }
//        });
//
//        cdReqMoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopUpDialogEmoney();
//            }
//        });
//
//    }
//
//
//    private void onCallApiInquiry() {
//        final InquiryDao dao = new InquiryDao(this);
//
//        model = new InquiryRequest(String.valueOf(CacheUtil.getPreferenceString(
//                IConfig.SESSION_PHONE, DashboardActivity.this)));
//
//        showApiProgressDialog(OttoCashSdk.getAppComponent(), new InquiryDao(this) {
//            @Override
//            public void call() {
//                dao.onInquiry(model, DashboardActivity.this, BaseDao.getInstance(DashboardActivity.this,
//                        IConfig.KEY_API_INQUIRY).callback);
//            }
//        });
//    }
//
//
//    @Override
//    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
//        super.onApiResponseCallback(br, responseCode, response);
//        if (response.isSuccessful()) {
//            if (responseCode == IConfig.KEY_API_INQUIRY) {
//                InquiryResponse data = (InquiryResponse) br;
//                if (data.getMeta().getCode() == 201 || data.getMeta().getCode() == 200) {
//
//                    emoneyBalance = data.getData().getEmoneyBalance();
//                    CacheUtil.putPreferenceInteger(IConfig.SESSION_EMONEY_BALANCE, Integer.parseInt(emoneyBalance), DashboardActivity.this);
//
//                    tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(data.getData().getEmoneyBalance())));
//                    tvName.setText("Hi " + data.getData().getName() + ". Saldo OttoCash Reguler Kamu");
//
//
//                } else {
//                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }
//
//    protected void popUpDialogPlus() {
//        String title = getString(R.string.dialog_title);
//        String message = getString(R.string.dialog_msg);
//        String btnLabel = getString(R.string.dialog_btn_close);
//
//        CustomDialog.alertDialog(this, title, message, btnLabel, false);
//    }
//
//    public void showPopUpDialogPlus() {
//        popUpDialogPlus();
//    }
//
//    public void showPopUpDialogEmoney() {
//        popUpDialogEmoney();
//
//    }
//
//    protected void popUpDialogEmoney() {
//        String title = getString(R.string.dialog_tittle_coming_soon);
//        String message = getString(R.string.dialog_message_coming_soon);
//        String btnLabel = getString(R.string.dialog_button_coming_soon);
//
//        CustomDialog.alertDialog(this, title, message, btnLabel, false);
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        try {
//
//            String PackageName = CacheUtil.getPreferenceString(IConfig.SESSION_PACKAGE_NAME,
//                    DashboardActivity.this);
//
//            Intent intent = new Intent(DashboardActivity.this, Class.forName(PackageName));
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.putExtra("EMONEY", emoneyBalance);
//            startActivity(intent);
//            finish();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}