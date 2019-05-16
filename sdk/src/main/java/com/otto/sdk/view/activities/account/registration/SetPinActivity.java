package com.otto.sdk.view.activities.account.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.model.api.misc.SecurityQuestionsItem;
import com.otto.sdk.model.api.request.ClientsRequest;
import com.otto.sdk.model.api.request.CreateTokenRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.ClientsResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.api.response.SecurityQuestionResponse;
import com.otto.sdk.presenter.dao.AuthDao;
import com.otto.sdk.presenter.dao.ClientsDao;
import com.otto.sdk.presenter.dao.CreateTokenDao;
import com.otto.sdk.presenter.dao.SecurityDao;
import com.otto.sdk.view.activities.account.formValidation.FormValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.component.ProgressDialogComponent;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

import static com.otto.sdk.IConfig.SESSION_EMAIL;
import static com.otto.sdk.IConfig.SESSION_ID;
import static com.otto.sdk.IConfig.SESSION_NAME;
import static com.otto.sdk.IConfig.SESSION_PHONE;
import static com.otto.sdk.IConfig.SESSION_SECRET;

public class SetPinActivity extends BaseActivity {

    EditText edtPin;
    EditText edtConfirmPin;
    EditText edtAnswer;
    Button btnSelesai;

    //Spinner
    Spinner spinner;

    private boolean isFormValidationSuccess = false;
    private RegisterRequest model;

    //Clients
    private ClientsRequest model_client;

    //CreateToken
    private CreateTokenRequest model_token;

    //Security
    private boolean isSelectedSecurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        initComponent();
        initContent();

        //Client
        onCallApiClients();
    }

    private void initComponent() {
        edtPin = findViewById(R.id.edtPin);
        edtConfirmPin = findViewById(R.id.edtConfirmPin);
        edtAnswer = findViewById(R.id.edtAnswer);
        btnSelesai = findViewById(R.id.btnSelesai);

        //Spinner
        spinner = findViewById(R.id.spinner);

        String[] question = new String[]{
                "Nama kota dimana ayah anda dilahirkan",
                "Nama ibu anda",
                "Nama sekolah anda saat Sekolah Menengah Pertama (SMP)",
                "Nama perusahaan dimana anda bekerja pertama kali",
                "Nama tim sepak bola favorit anda",
                "Nama sahabat anda"
        };

        final List<String> questionList = new ArrayList<>(Arrays.asList(question));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.item_question, questionList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_question);
        spinner.setAdapter(spinnerArrayAdapter);

    }

    private void initContent() {
        addTextWatcher(edtPin);
        addTextWatcher(edtConfirmPin);

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValidationSuccess) {
                    onCallApiSetPin();
                } else {
                    Toast.makeText(SetPinActivity.this, "Pin Tidak Sama", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onCallApiSetPin() {
        final AuthDao dao = new AuthDao(this);

        model = new RegisterRequest(
                CacheUtil.getPreferenceString(SESSION_PHONE, SetPinActivity.this),
                CacheUtil.getPreferenceString(SESSION_NAME, SetPinActivity.this),
                CacheUtil.getPreferenceString(SESSION_EMAIL, SetPinActivity.this));

        model.setPin(edtPin.getText().toString());
        model.setSecurityQuestionId("1");
        model.setAnswer(edtAnswer.getText().toString());
        model.setPlatform("android");

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthDao(this) {
            @Override
            public void call() {
                dao.onRegister(model, SetPinActivity.this, BaseDao.getInstance(SetPinActivity.this,
                        IConfig.KEY_API_REGISTER).callback);
            }
        });
    }

    //Client
    private void onCallApiClients() {
        final ClientsDao dao = new ClientsDao(this);
        model_client = new ClientsRequest(CacheUtil.getPreferenceString(IConfig.SESSION_EMAIL, SetPinActivity.this));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new ClientsDao(this) {
            @Override
            public void call() {
                dao.onClients(model_client, SetPinActivity.this, BaseDao.getInstance(SetPinActivity.this,
                        IConfig.KEY_API_CLIENTS).callback);
            }
        });

    }

    //CreateToken
    private void onCallApiCreateToken() {

        final CreateTokenDao dao = new CreateTokenDao(this);
        model_token = new CreateTokenRequest(
                CacheUtil.getPreferenceString(SESSION_ID, SetPinActivity.this),
                CacheUtil.getPreferenceString(SESSION_SECRET, SetPinActivity.this));

        model_token.setGrantType("client_credentials");

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new CreateTokenDao(this) {
            @Override
            public void call() {
                dao.onCreateToken(model_token, SetPinActivity.this, BaseDao.getInstance(SetPinActivity.this,
                        IConfig.KEY_API_TOKEN).callback);
            }
        });


    }

    //SecurityQuestion
    private void onCallApiSecurityQuestion() {
        final SecurityDao dao = new SecurityDao(this);
        dao.securityQuestionDAO(BaseDao.getInstance(this, 200).callback);
        ProgressDialogComponent.showProgressDialog(this, "Loading", false);
    }


    @Override
    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        super.onApiResponseCallback(br, responseCode, response);
        if (response.isSuccessful()) {

            //Clients
            if (responseCode == IConfig.KEY_API_CLIENTS) {
                ClientsResponse data = (ClientsResponse) br;
                if (data.getMeta().getCode() == 200) {
                    String email = data.getData().getClient().getEmail();
                    String id = data.getData().getClient().getId();
                    String secret = data.getData().getClient().getSecret();
                    CacheUtil.putPreferenceString(IConfig.SESSION_EMAIL, email, SetPinActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_ID, id, SetPinActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_SECRET, secret, SetPinActivity.this);

                    onCallApiCreateToken();

                }

            }

            //CreateToken
            if (responseCode == IConfig.KEY_API_TOKEN) {
                CreateTokenResponse data = (CreateTokenResponse) br;
                if (data.getMeta().getCode() == 200) {
                    String access_token = data.getData().getClient().getAccessToken();
                    String token_type = data.getData().getClient().getTokenType();
                    int expire_in = data.getData().getClient().getExpiresIn();
                    int created_at = data.getData().getClient().getCreatedAt();

                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCESS_TOKEN, access_token, SetPinActivity.this);
                    onCallApiSecurityQuestion();
                }
            }

            //SecurityQuestion
            if (responseCode == IConfig.KEY_API_SECURITY) {
                List<SecurityQuestionsItem> questionsItems;
                SecurityQuestionResponse data = (SecurityQuestionResponse) br;
                if (data.getMeta().getCode() == 200) {
                    questionsItems = ((SecurityQuestionResponse) br).getData().getSecurityQuestions();


                }
            }


            if (responseCode == IConfig.KEY_API_REGISTER) {
                RegisterResponse data = (RegisterResponse) br;
                if (data.getMeta().getCode() == 200) {

                    int user_id = data.getData().getId();
                    String phone = data.getData().getPhone();
                    String account_number = data.getData().getAccountNumber();
                    String name = data.getData().getName();
                    String email = data.getData().getEmail();

                    CacheUtil.putPreferenceInteger(IConfig.SESSION_USER_ID, user_id, SetPinActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, SetPinActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCOUNT_NUMBER, account_number, SetPinActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, SetPinActivity.this);
                    CacheUtil.putPreferenceString(IConfig.SESSION_EMAIL, email, SetPinActivity.this);


                    Intent intent = new Intent(SetPinActivity.this, OtpActivity.class);
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

    private void validateForm() {
        String pin = edtPin.getText().toString();
        String pin2 = edtConfirmPin.getText().toString();

        if (FormValidation.required(pin) && FormValidation.validPin(pin)
                && FormValidation.required(pin2) && FormValidation.validPin(pin2) && (pin.equals(pin2))) {
            isFormValidationSuccess = true;
            btnSelesai.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selector));
        } else {
            isFormValidationSuccess = false;
            btnSelesai.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selected_bg));
        }
    }

    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateForm();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }


}
