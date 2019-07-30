//package com.otto.sdk.ui.activities.account.login;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.otto.sdk.IConfig;
//import com.otto.sdk.OttoCashSdk;
//import com.otto.sdk.R;
//import com.otto.sdk.model.api.request.LoginRequest;
//import com.otto.sdk.model.api.response.LoginResponse;
//import com.otto.sdk.model.dao.AuthDao;
//import com.otto.sdk.presenter.manager.SessionManager;
//import com.poovam.pinedittextfield.LinePinField;
//
//import app.beelabs.com.codebase.base.BaseActivity;
//import app.beelabs.com.codebase.base.BaseDao;
//import app.beelabs.com.codebase.base.response.BaseResponse;
//import app.beelabs.com.codebase.support.util.CacheUtil;
//import retrofit2.Response;
//
//public class PinLoginActivity extends BaseActivity {
//
//    LinePinField lineField;
//    private LoginRequest model;
//
//    private String phone;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pin_login);
//
//        initComponent();
//        addTextWatcher(lineField);
//    }
//
//    private void initComponent() {
//        lineField = findViewById(R.id.lineField);
//    }
//
//    private void onCallApiSetPin(final String pin) {
//        final AuthDao dao = new AuthDao(this);
//
//        phone = CacheUtil.getPreferenceString(String.valueOf(IConfig.SESSION_PHONE), PinLoginActivity.this);
//        model = new LoginRequest(String.valueOf(CacheUtil.getPreferenceString(
//                IConfig.SESSION_PHONE, PinLoginActivity.this)));
//        model.setPhone(phone);
//        model.setPin(lineField.getText().toString());
//
//        showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthDao(this) {
//            @Override
//            public void call() {
//                dao.onLogin(model, PinLoginActivity.this, BaseDao.getInstance(PinLoginActivity.this,
//                        IConfig.KEY_API_LOGIN).callback);
//            }
//        });
//    }
//
//    @Override
//    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
//        super.onApiResponseCallback(br, responseCode, response);
//        if (response.isSuccessful()) {
//            if (responseCode == IConfig.KEY_API_LOGIN) {
//                LoginResponse data = (LoginResponse) br;
//                if (data.getMeta().getCode() == 201 || data.getMeta().getCode() == 200) {
//
//                    SessionManager.putSessionLogin(true, PinLoginActivity.this);
//
//                    int user_id = data.getData().getId();
//                    String account_number = data.getData().getAccountNumber();
//                    String name = data.getData().getName();
//                    String phone = data.getData().getPhone();
//
//                    boolean isLogin = data.getMeta().isStatus();
//                    CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_IS_LOGIN)),
//                            isLogin, PinLoginActivity.this);
//
//                    CacheUtil.putPreferenceInteger(IConfig.SESSION_USER_ID, user_id, PinLoginActivity.this);
//                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCOUNT_NUMBER, account_number, PinLoginActivity.this);
//                    CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, PinLoginActivity.this);
//                    CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, PinLoginActivity.this);
//
//                    Intent intent = new Intent(PinLoginActivity.this, OtpLoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.putExtra(IConfig.SESSION_PHONE, phone);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }
//
//
//    public void addTextWatcher(EditText input) {
//        input.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().length() == 6) {
//                    onCallApiSetPin(charSequence.toString());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//
//        });
//    }
//}