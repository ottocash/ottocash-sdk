package com.otto.sdk.view.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.presenter.dao.AuthDao;
import com.otto.sdk.presenter.manager.SessionManager;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    EditText edt_phone;
    EditText edt_pin;
    Button btn_login;
//    TextView tv_lupa_pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        if (SessionManager.isLogin(this)) {
//            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//            startActivity(intent);
//        }

        initComponent();
        initContent();
    }

    private void initComponent() {

        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_pin = (EditText) findViewById(R.id.edt_pin);
        btn_login = (Button) findViewById(R.id.btn_login);
//        tv_lupa_pin = (TextView) findViewById(R.id.tv_lupa_pin);

    }

    private void initContent() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallApiLogin();
            }
        });
    }

    private void onCallApiLogin() {
        final AuthDao dao = new AuthDao(this);
        final LoginRequest model = new LoginRequest();
        model.setPhone(edt_phone.getText().toString());
        model.setPin(edt_pin.getText().toString());
        showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthDao(this) {
            @Override
            public void call() {
                dao.onLogin(model, LoginActivity.this, BaseDao.getInstance(LoginActivity.this,
                        IConfig.KEY_API_LOGIN).callback);
            }
        });
    }


    @Override
    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        super.onApiResponseCallback(br, responseCode, response);
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_LOGIN) {
                LoginResponse data = (LoginResponse) br;
                if (data.getMeta().getCode() == 201 || data.getMeta().getCode() == 200) {

                    SessionManager.putSessionLogin(true, LoginActivity.this);

                    int user_id = data.getData().getId();
                    String account_number = data.getData().getAccountNumber();
                    String name = data.getData().getName();
                    String phone = data.getData().getPhone();

                    CacheUtil.putPreferenceInteger(IConfig.SESSION_USER_ID, user_id, LoginActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCOUNT_NUMBER, account_number, LoginActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, LoginActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, LoginActivity.this);

                    Intent intent = new Intent(LoginActivity.this, OtpLoginActivity.class);
                    intent.putExtra(IConfig.SESSION_PHONE, phone);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
