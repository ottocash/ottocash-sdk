//package com.otto.sdk.ui.activities.account.login;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.otto.sdk.IConfigSDK;
//import com.otto.sdk.OttoCashSDK;
//import com.otto.sdk.R;
//import com.otto.sdk.base.BaseActivitySDK;
//import com.otto.sdk.base.BaseDaoSDKSDK;
//import com.otto.sdk.base.response.BaseResponseSDK;
//import com.otto.sdk.model.api.request.LoginRequest;
//import com.otto.sdk.model.api.response.LoginResponseSDK;
//import com.otto.sdk.model.dao.AuthDaoSDK;
//import com.otto.sdk.presenter.manager.SessionManager;
//
//
//import retrofit2.Response;
//
//public class LoginActivity extends BaseActivitySDK {
//
//    EditText edt_phone;
//    EditText edt_pin;
//    Button btn_login;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        initComponent();
//        initContent();
//    }
//
//    private void initComponent() {
//
//        edt_phone = findViewById(R.id.edt_phone);
//        edt_pin = findViewById(R.id.edt_pin);
//        btn_login = findViewById(R.id.btn_login);
//    }
//
//    private void initContent() {
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onCallApiLogin();
//            }
//        });
//    }
//
//    private void onCallApiLogin() {
//        final AuthDaoSDK dao = new AuthDaoSDK(this);
//        final LoginRequest model = new LoginRequest();
//        model.setPhone(edt_phone.getText().toString());
//        model.setPin(edt_pin.getText().toString());
//        showApiProgressDialog(OttoCashSDK.getAppComponent(), new AuthDaoSDK(this) {
//            @Override
//            public void call() {
//                dao.onLogin(model, LoginActivity.this, BaseDaoSDKSDK.getInstance(LoginActivity.this,
//                        IConfigSDK.KEY_API_LOGIN).callback);
//            }
//        });
//    }
//
//
//    @Override
//    protected void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
//        super.onApiResponseCallback(br, responseCode, response);
//        if (response.isSuccessful()) {
//            if (responseCode == IConfigSDK.KEY_API_LOGIN) {
//                LoginResponseSDK data = (LoginResponseSDK) br;
//                if (data.getMeta().getCode() == 201 || data.getMeta().getCode() == 200) {
//
//                    SessionManager.putSessionLogin(true, LoginActivity.this);
//
//                    int user_id = data.getData().getId();
//                    String account_number = data.getData().getAccountNumber();
//                    String name = data.getData().getName();
//                    String phone = data.getData().getPhone();
//
//                    CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_USER_ID, user_id, LoginActivity.this);
//                    CacheUtil.putPreferenceString(IConfigSDK.SESSION_ACCOUNT_NUMBER, account_number, LoginActivity.this);
//                    CacheUtil.putPreferenceString(IConfigSDK.SESSION_NAME, name, LoginActivity.this);
//                    CacheUtil.putPreferenceString(IConfigSDK.SESSION_PHONE, phone, LoginActivity.this);
//
//                    Intent intent = new Intent(LoginActivity.this, OtpLoginActivitySDK.class);
//                    intent.putExtra(IConfigSDK.SESSION_PHONE, phone);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }
//}
