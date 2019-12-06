package com.otto.ottocash;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.interfaces.ISdkView;
import com.otto.sdk.model.api.request.CheckPhoneNumberRequest;
import com.otto.sdk.model.api.request.ClientsRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.presenter.SdkResourcePresenter;
import com.otto.sdk.ui.activity.account.activation.ActivationActivity;
import com.otto.sdk.ui.activity.account.registration.RegistrationActivity;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardAppActivity extends BaseActivity implements ISdkView, IInquiryView {

    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;
    @BindView(R.id.lyWidgetSdk)
    LinearLayout lyWidgetSdk;
    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;
    @BindView(R.id.btnClearCache)
    Button btnClearCache;
    public static String PackageName;
    private String account_number;
    private CheckPhoneNumberResponse checkPhoneNumberResponse;
    private final String KEY_EMONEY_BALANCE = "EMONEY";
    private SdkResourcePresenter presenterSDK;
    SharedPreferences sharedPreferences;
    String getDatasessi;
    String saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_app);
        ButterKnife.bind(this);
        PackageName = (this.getPackageName() + ".DashboardAppActivity");
        CacheUtil.putPreferenceString(IConfig.SESSION_PACKAGE_NAME, PackageName, DashboardAppActivity.this);
        onSetupAccountClick();

        sharedPreferences = getSharedPreferences("dataSesi", Context.MODE_PRIVATE);
        saldo  = sharedPreferences.getString("saldo", null);

        getDatasessi = sharedPreferences.getString("session", null);
        Log.i("responData", " " + getDatasessi);

        if ( saldo != null) {
            Log.i("respon", " " + saldo);
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(saldo)));

        }


        onEmoneyBalanceWidget();


//        initializeSDK();
        checkFirstRun();


    }


    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = getSharedPreferences("dataSesi", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(DashboardAppActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goDashboardSDK() {
        Intent intent = new Intent(DashboardAppActivity.this, DashboardSDKActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goActivation() {
        Intent intent = new Intent(DashboardAppActivity.this, ActivationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        DashboardAppActivity.this.startActivity(intent);
    }

    public void goRegistration() {
        Intent intent = new Intent(DashboardAppActivity.this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        DashboardAppActivity.this.startActivity(intent);
    }

    public void onSetupAccountClick() {
        boolean hasPhoneNumber = Boolean.parseBoolean(String.valueOf(CacheUtil.getPreferenceBoolean(String.valueOf(
                IConfig.SESSION_CHECK_PHONE_NUMBER), DashboardAppActivity.this)));

//        if (checkPhoneNumberResponse.getData().isIs_existing()== true) {
        Log.i("JHONSON", "phone number" + hasPhoneNumber);
        lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeSDK();
            }
        });
//        } else {
//            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    goRegistration();
//                }
//            });
//        }
    }


    public void onEmoneyBalanceWidget() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            String emoney = extras.getString(KEY_EMONEY_BALANCE);
            tvSaldoOttoCash.setText(UiUtil.formatMoneyIDR(Long.parseLong(emoney)));
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goDashboardSDK();
                }
            });
        }
    }


    public void initializeSDK() {

        final CheckPhoneNumberRequest model = new CheckPhoneNumberRequest();

        final ClientsRequest clients = new ClientsRequest();
        clients.setEmail("ardi@clappingape.com");
        account_number = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, DashboardAppActivity.this);
        model.setPhone(account_number);

        final Dialog loading = UiUtil.getProgressDialog(DashboardAppActivity.this);
        loading.show();

        new InquiryPresenter(this).getInquiry(new InquiryRequest(account_number));
        showApiProgressDialog(OttoCashSdk.getAppComponent(), new SdkResourcePresenter(DashboardAppActivity.this) {
            @Override
            public void call() {
                getCheckPhone(model);
                loading.dismiss();

            }
        }, "Loading");
    }


    @OnClick(R.id.btnCheckOut)
    public void onCheckOut() {
        Intent intent = new Intent(DashboardAppActivity.this, CheckOutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.btnClearCache)
    public void onClearCache() {
//        SessionManager.getSessionLogin(false, DashboardAppActivity.this);
        SharedPreferences.Editor editor = getSharedPreferences("dataSesi", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(DashboardAppActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void onCreateToken() {
        final CreateTokenRequest token = new CreateTokenRequest();

        token.setGrantType("client_credentials");
        String id = CacheUtil.getPreferenceString(IConfig.SESSION_ID, DashboardAppActivity.this);
        token.setClientId(id);
        String secret = CacheUtil.getPreferenceString(IConfig.SESSION_SECRET, DashboardAppActivity.this);
        token.setClientSecret(secret);

        presenterSDK = ((SdkResourcePresenter) BasePresenter.getInstance(DashboardAppActivity.this, new SdkResourcePresenter(DashboardAppActivity.this)));
        presenterSDK.doCreateToken(token);
    }

    @Override
    public void handleCheckPhoneNumber(CheckPhoneNumberResponse model) {
        checkPhoneNumberResponse = model;
        if (model.getMeta().getCode() == 200) {
            boolean is_existing = model.getData().isIs_existing();
            CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_CHECK_PHONE_NUMBER)),
                    is_existing, DashboardAppActivity.this);

            Log.i("ISEX", "isExisting" + is_existing);

            onCreateToken();

            if (checkPhoneNumberResponse.getData().isIs_existing() == true) {
                if (getDatasessi != null) {
                    startActivity(new Intent(getApplicationContext(), DashboardSDKActivity.class));


                } else {
                    goActivation();
                }

            } else {
                goRegistration();
            }
        } else {
//            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
//                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void handleToken(CreateTokenResponse model) {
        if (model.getMeta().getCode() == 200) {

            String accessToken = model.getData().getClient().getAccessToken();
            CacheUtil.putPreferenceString(IConfig.SESSION_ACCESS_TOKEN, accessToken, DashboardAppActivity.this);


        } else {
//            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
//                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {

            CacheUtil.putPreferenceString(IConfig.SESSION_EMONEY_BALANCE, model.getData().getEmoneyBalance(), this);

        } else {
//            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
//                    Toast.LENGTH_LONG).show();
        }
    }
}

