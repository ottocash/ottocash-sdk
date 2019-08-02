package com.otto.sdk.ui.activities.account.registration;

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
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.api.response.SecurityQuestionResponse;
import com.otto.sdk.model.dao.AuthDao;
import com.otto.sdk.model.dao.SecurityDao;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.ui.activities.account.formValidation.FormValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

import static com.otto.sdk.IConfig.SESSION_EMAIL;
import static com.otto.sdk.IConfig.SESSION_NAME;
import static com.otto.sdk.IConfig.SESSION_PHONE;

public class SetPinActivity extends BaseActivity {

    EditText edtPin;
    EditText edtConfirmPin;
    EditText edtAnswer;
    Button btnSelesai;

    //Spinner
    Spinner spinner;

    private boolean isFormValidationSuccess = false;
    private RegisterRequest model;

    //Security
    private boolean isSelectedSecurity;

    private AuthPresenter authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        initComponent();
        initContent();

//        onCallApiSecurityQuestion();
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
//        final AuthDao dao = new AuthDao(this);

        model = new RegisterRequest(
                CacheUtil.getPreferenceString(SESSION_PHONE, SetPinActivity.this),
                CacheUtil.getPreferenceString(SESSION_NAME, SetPinActivity.this),
                CacheUtil.getPreferenceString(SESSION_EMAIL, SetPinActivity.this));

        model.setPin(edtPin.getText().toString());
        model.setSecurityQuestionId("1");
        model.setAnswer(edtAnswer.getText().toString());
        model.setPlatform("android");

        authPresenter.getRegister(model);

//        showApiProgressDialog(OttoCashSdk.getAppComponent(), new AuthDao(this) {
//            @Override
//            public void call() {
//                dao.onRegister(model, SetPinActivity.this, BaseDao.getInstance(SetPinActivity.this,
//                        IConfig.KEY_API_REGISTER).callback);
//            }
//        });
    }


//    private void onCallApiSecurityQuestion() {
//        final SecurityDao dao = new SecurityDao(this);
//        dao.securityQuestionDAO(BaseDao.getInstance(this, 200).callback);
//    }


//    @Override
//    protected void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
//        super.onApiResponseCallback(br, responseCode, response);
//        if (response.isSuccessful()) {
//
//
//            if (responseCode == IConfig.KEY_API_SECURITY) {
//                List<SecurityQuestionsItem> questionsItems;
//                SecurityQuestionResponse data = (SecurityQuestionResponse) br;
//                if (data.getMeta().getCode() == 200) {
//                    questionsItems = ((SecurityQuestionResponse) br).getData().getSecurityQuestions();
//
//                }
//            }
//
//
//            if (responseCode == IConfig.KEY_API_REGISTER) {
//                RegisterResponse data = (RegisterResponse) br;
//                if (data.getMeta().getCode() == 200) {
//
//                    int user_id = data.getData().getId();
//                    String phone = data.getData().getPhone();
//                    String account_number = data.getData().getAccountNumber();
//                    String name = data.getData().getName();
//                    String email = data.getData().getEmail();
//
//                    CacheUtil.putPreferenceInteger(IConfig.SESSION_USER_ID, user_id, SetPinActivity.this);
//                    CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, SetPinActivity.this);
//                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCOUNT_NUMBER, account_number, SetPinActivity.this);
//                    CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, SetPinActivity.this);
//                    CacheUtil.putPreferenceString(IConfig.SESSION_EMAIL, email, SetPinActivity.this);
//
//
//                    Intent intent = new Intent(SetPinActivity.this, OtpActivity.class);
//                    intent.putExtra(IConfig.SESSION_PHONE, phone);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(this, data.getMeta().getCode() + ":" + data.getMeta().getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//
//
//        }
//    }

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
