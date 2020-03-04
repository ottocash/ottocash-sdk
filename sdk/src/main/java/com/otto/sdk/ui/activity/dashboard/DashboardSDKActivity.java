package com.otto.sdk.ui.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.otto.sdk.model.general.MainMenuModel;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.ui.activity.account.activation.PinLoginActivity;
import com.otto.sdk.ui.activity.history.HistoryActivity;
import com.otto.sdk.ui.activity.kycupgrade.IntroductionUpgradeActivity;
import com.otto.sdk.ui.activity.payment.QR.PayWithQr;
import com.otto.sdk.ui.activity.payment.TransferToFriend.TransferToFriendActivity;
import com.otto.sdk.ui.activity.tac.TACOttoCashActivity;
import com.otto.sdk.ui.activity.topup.TopUpActivity;
import com.otto.sdk.ui.adapter.MainMenuAdapter;
import com.otto.sdk.ui.component.dialog.CustomDialog;
import com.otto.sdk.ui.component.support.ItemClickSupport;
import com.otto.sdk.ui.component.support.UiUtil;

import java.util.ArrayList;
import java.util.List;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class DashboardSDKActivity extends BaseActivity implements IInquiryView {

    ImageView ivBack;
    TextView tvTitleOttoCash;
    TextView tvEmoneyBalance;
    RecyclerView rvMainMenu;
    TextView tvSetPinOttocash;
    TextView tvTacOttocash;
    TextView tvUpgrade;
    TextView tvPending;
    private String saldo_ottocash;
    private String user_name;
    private int verifyStatus;
    public static String nikmatinaja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sdk);

        CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_IS_ACTIVE, true, this);
        onCallApiInquiry();

        initComponent();
        initRecyclerView();
        displayMainMenu();
    }


    public void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvTitleOttoCash = findViewById(R.id.tvTitleOttoCash);
        tvEmoneyBalance = findViewById(R.id.tvEmoneyBalance);
        rvMainMenu = findViewById(R.id.rvMainMenu);
        tvPending = findViewById(R.id.tv_pending);

        tvSetPinOttocash = findViewById(R.id.tvSetPinOttocash);
        tvTacOttocash = findViewById(R.id.tvTacOttocash);
        tvUpgrade = findViewById(R.id.tv_upgrade);

        tvSetPinOttocash.setText(UiUtil.getHTMLContent(getString(R.string.set_pin_ottocash)));
        tvTacOttocash.setText(UiUtil.getHTMLContent(getString(R.string.tac_ottocash)));

        tvUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSDKActivity.this, IntroductionUpgradeActivity.class);
                intent.putExtra("account_number", nikmatinaja);

                startActivity(intent);
            }
        });

        tvTacOttocash.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardSDKActivity.this, TACOttoCashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        ivBack.setOnClickListener(v -> onBackPressed());

    }


    private void initRecyclerView() {
        rvMainMenu.setHasFixedSize(true);
        rvMainMenu.setNestedScrollingEnabled(false);
        final GridLayoutManager mainMenuLayoutManager = new GridLayoutManager(this, 4);
        rvMainMenu.setLayoutManager(mainMenuLayoutManager);
    }

    private void displayMainMenu() {
        final List<MainMenuModel> mainMenuList = getMainMenuList();

        MainMenuAdapter adapter = new MainMenuAdapter(this, mainMenuList, R.layout.item_main_menu_ottocash);
        rvMainMenu.setAdapter(adapter);

        ItemClickSupport.addTo(rvMainMenu).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainMenuSelected(mainMenuList.get(position));
                    }
                }, 300);
            }
        });
    }

    private List<MainMenuModel> getMainMenuList() {

        String[] mainMenus;
        String[] mainMenuCodes;
        Integer[] menuIcons;

        mainMenus = getResources().getStringArray(R.array.otto_cash_main_menu);
        mainMenuCodes = getResources().getStringArray(R.array.otto_cash_menu_code);

        menuIcons = new Integer[]{
                R.drawable.ic_bayar,
                //R.drawable.ic_merchant,
                R.drawable.ic_top_up,
                R.drawable.ic_history,
                //R.drawable.ic_gift,
                R.drawable.ic_tarik_duit,
                //R.drawable.ic_req_money,
                R.drawable.ic_transfer,
        };

        List<MainMenuModel> mainMenuList = new ArrayList<>();
        for (int i = 0; i < mainMenus.length; i++) {
            MainMenuModel mainMenu = new MainMenuModel();
            mainMenu.setCode(mainMenuCodes[i]);
            mainMenu.setName(mainMenus[i]);
            mainMenu.setIcon(menuIcons[i]);
            mainMenuList.add(mainMenu);
        }

        return mainMenuList;
    }

    private void mainMenuSelected(MainMenuModel mainMenu) {
        switch (mainMenu.getCode()) {
            case "mm_1":
                goPaymentWithQR();
                break;
            case "mm_2":
                Intent intent = new Intent(this, TopUpActivity.class);
                startActivity(intent);
                break;
            case "mm_3":
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
            case "mm_4":
                dialogComingSoon();
                break;
            case "mm_5":
                goTransferToFriend();
                break;
            /*case "mm_6":
                dialogComingSoon();
                break;
            case "mm_7":
                dialogComingSoon();
                break;
            case "mm_8":
                goTransferToFriend();
                break;*/
            default:
                break;
        }
    }


    protected void dialogComingSoon() {
        String title = getString(R.string.dialog_tittle_coming_soon);
        String message = getString(R.string.dialog_message_coming_soon);
        String btnLabel = getString(R.string.dialog_button_coming_soon);

        CustomDialog.alertDialog(this, title, message, btnLabel, false);
    }

    protected void dialogUpgradePlus() {
        String title = getString(R.string.dialog_title);
        String message = getString(R.string.dialog_msg);
        String btnLabel = getString(R.string.dialog_btn_close);

        CustomDialog.alertDialog(this, title, message, btnLabel, false);

    }

    public void goPaymentWithQR() {
        startActivity(new Intent(this, PayWithQr.class));
    }

    private void goTransferToFriend() {
        if (verifyStatus == 0 || verifyStatus == 1 || verifyStatus == 3) {
            dialogUpgradePlus();
        } else {
            Intent intent = new Intent(this, TransferToFriendActivity.class);
            startActivity(intent);
        }
    }


    /**
     * Call Api Inquiry
     */
    private void onCallApiInquiry() {
        String phone = String.valueOf(CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, DashboardSDKActivity.this));
        final InquiryRequest inquiryRequest = new InquiryRequest();

        inquiryRequest.setAccount_number(phone);

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new InquiryPresenter(this) {
            @Override
            public void call() {
                getInquiry(inquiryRequest);
            }

        }, "Loading");
    }


    /**
     * Handle Response Api Inquiry
     */
    @Override
    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {
            CacheUtil.putPreferenceBoolean(IConfig.OC_SESSION_LOGIN_KEY, true, DashboardSDKActivity.this);

            CacheUtil.putPreferenceString(IConfig.OC_SESSION_ACCOUNT_NUMBER, model.getData().getAccount_number(), DashboardSDKActivity.this);

            saldo_ottocash = model.getData().getEmoney_balance();
            CacheUtil.putPreferenceString(IConfig.OC_SESSION_EMONEY_BALANCE, saldo_ottocash, DashboardSDKActivity.this);

            user_name = model.getData().getName();
            nikmatinaja = model.getData().getAccount_number();

            CacheUtil.putPreferenceString(IConfig.OC_SESSION_NAME, user_name, DashboardSDKActivity.this);
            verifyStatus = model.getData().getVerify_status();

            tvEmoneyBalance.setText(UiUtil.formatMoneyIDR(Long.parseLong(saldo_ottocash)));

            if (model.getData().getVerify_status() == 2) {
                tvUpgrade.setVisibility(View.GONE);
                tvPending.setVisibility(View.GONE);
                tvTitleOttoCash.setText("Hai " + user_name + ". Saldo OttoCash Plus");
            } else if (model.getData().getVerify_status() == 1) {
                tvUpgrade.setVisibility(View.GONE);
                tvPending.setVisibility(View.VISIBLE);
                tvTitleOttoCash.setText("Hai " + user_name + ". Saldo OttoCash Reguler");
            } else if (model.getData().getVerify_status() == 0) {
                tvUpgrade.setVisibility(View.VISIBLE);
                tvPending.setVisibility(View.GONE);
                tvTitleOttoCash.setText("Hai " + user_name + ". Saldo OttoCash Reguler");
            } else if (model.getData().getVerify_status() == 3) {
                tvUpgrade.setVisibility(View.VISIBLE);
                tvPending.setVisibility(View.GONE);
                tvTitleOttoCash.setText("Hai " + user_name + ". Saldo OttoCash Reguler");
            }

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return DashboardSDKActivity.this;
    }

}