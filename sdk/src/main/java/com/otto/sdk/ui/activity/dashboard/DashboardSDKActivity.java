package com.otto.sdk.ui.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.Flag;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.model.api.response.UpgradeAccountResponse;
import com.otto.sdk.ui.activity.kycupgrade.UpgradeActivity;
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.model.general.MainMenuModel;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.ui.activity.history.HistoryActivity;
import com.otto.sdk.ui.activity.payment.QR.PayWithQr;
import com.otto.sdk.ui.activity.payment.TransferToFriend.TransferToFriendActivity;
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

    private InquiryRequest inquiryRequest;
    private InquiryResponse inquiryResponse;
    private String emoneyBalance;
    private String name;
    private int verifyStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sdk);

        initComponent();
        initRecyclerView();
        displayMainMenu();
        onCallApiInquiry();

        inquiryResponse = new InquiryResponse();

        tvUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardSDKActivity.this, UpgradeActivity.class);
//                intent.putExtra(Flag.ACCOUNT_NUMBER, inquiryResponse.getData().getAccountNumber());
                startActivity(intent);
            }
        });
    }

    public void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvTitleOttoCash = findViewById(R.id.tvTitleOttoCash);
        tvEmoneyBalance = findViewById(R.id.tvEmoneyBalance);
        rvMainMenu = findViewById(R.id.rvMainMenu);

        tvSetPinOttocash = findViewById(R.id.tvSetPinOttocash);
        tvTacOttocash = findViewById(R.id.tvTacOttocash);
        tvUpgrade = findViewById(R.id.tv_upgrade);

        tvSetPinOttocash.setText(UiUtil.getHTMLContent(getString(R.string.set_pin_ottocash)));
        tvTacOttocash.setText(UiUtil.getHTMLContent(getString(R.string.tac_ottocash)));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                R.drawable.ic_merchant,
                R.drawable.ic_top_up,
                R.drawable.ic_history,
                R.drawable.ic_gift,
                R.drawable.ic_tarik_duit,
                R.drawable.ic_req_money,
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
                Intent intent = new Intent(this, PayWithQr.class);
                startActivity(intent);
                break;
            case "mm_2":
                dialogComingSoon();
                break;
            case "mm_3":
                intent = new Intent(this, TopUpActivity.class);
                startActivity(intent);
                break;
            case "mm_4":
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
            case "mm_5":
                dialogComingSoon();
                break;
            case "mm_6":
                dialogComingSoon();
                break;
            case "mm_7":
                dialogComingSoon();
                break;
            case "mm_8":
                goTransferToFriend();
                break;
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

    private void goTransferToFriend() {
        if (verifyStatus == 0 || verifyStatus == 1 || verifyStatus == 3) {
            dialogUpgradePlus();
        } else {
            Intent intent = new Intent(this, TransferToFriendActivity.class);
            startActivity(intent);
        }
    }


    private void onCallApiInquiry() {

        inquiryRequest = new InquiryRequest(String.valueOf(CacheUtil.getPreferenceString(
                IConfig.SESSION_PHONE, DashboardSDKActivity.this)));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new InquiryPresenter(DashboardSDKActivity.this) {
            @Override
            public void call() {
                getInquiry(inquiryRequest);

            }
        }, "Loading");
    }


    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {

            emoneyBalance = model.getData().getEmoneyBalance();
            CacheUtil.putPreferenceString(IConfig.SESSION_EMONEY_BALANCE, emoneyBalance, DashboardSDKActivity.this);

            name = model.getData().getName();
            CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, DashboardSDKActivity.this);

            verifyStatus = model.getData().getVerifyStatus();

            tvTitleOttoCash.setText("Hai " + name + ". Saldo OttoCash Reguler Kamu");
            tvEmoneyBalance.setText(UiUtil.formatMoneyIDR(Long.parseLong(emoneyBalance)));

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