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

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.OttoCashSDK;
import com.otto.sdk.R;
import com.otto.sdk.base.BaseActivitySDK;
import com.otto.sdk.base.support.util.CacheUtil;
import com.otto.sdk.interfaces.IAuthViewSDK;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponseSDK;
import com.otto.sdk.model.api.response.RegisterResponseSDK;
import com.otto.sdk.presenter.AuthPresenterSDK;
import com.otto.sdk.ui.activities.account.formValidation.FormValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.otto.sdk.IConfigSDK.SESSION_EMAIL;
import static com.otto.sdk.IConfigSDK.SESSION_NAME;
import static com.otto.sdk.IConfigSDK.SESSION_PHONE;

public class SetPinActivitySDK extends BaseActivitySDK implements IAuthViewSDK {

    EditText edtPin;
    EditText edtConfirmPin;
    EditText edtAnswer;
    Button btnSelesai;
    Spinner spinner;

    private boolean isFormValidationSuccess = false;
    private RegisterRequest model;
    private boolean isSelectedSecurity;
    private AuthPresenterSDK authPresenter;

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
                    Toast.makeText(SetPinActivitySDK.this, "Pin Tidak Sama", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onCallApiSetPin() {
        model = new RegisterRequest(
                CacheUtil.getPreferenceString(SESSION_PHONE, SetPinActivitySDK.this),
                CacheUtil.getPreferenceString(SESSION_NAME, SetPinActivitySDK.this),
                CacheUtil.getPreferenceString(SESSION_EMAIL, SetPinActivitySDK.this));

        model.setPin(edtPin.getText().toString());
        model.setSecurityQuestionId("1");
        model.setAnswer(edtAnswer.getText().toString());
        model.setPlatform("android");


        showApiProgressDialogSDK(OttoCashSDK.getAppComponentSDK(), new AuthPresenterSDK(SetPinActivitySDK.this) {
            @Override
            public void call() {
                getRegister(model);

            }
        }, "Loading");
    }


//    private void onCallApiSecurityQuestion() {
//        final SecurityDaoSDK dao = new SecurityDaoSDK(this);
//        dao.securityQuestionDAO(BaseDaoSDKSDK.getInstance(this, 200).callback);
//    }


//    @Override
//    protected void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
//        super.onApiResponseCallback(br, responseCode, response);
//        if (response.isSuccessful()) {
//
//
//            if (responseCode == IConfigSDK.KEY_API_SECURITY) {
//                List<SecurityQuestionsItem> questionsItems;
//                SecurityQuestionResponseSDK data = (SecurityQuestionResponseSDK) br;
//                if (data.getMeta().getCode() == 200) {
//                    questionsItems = ((SecurityQuestionResponseSDK) br).getData().getSecurityQuestions();
//
//                }
//            }
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


    @Override
    public void handleLogin(LoginResponseSDK model) {

    }

    @Override
    public void handleRegister(RegisterResponseSDK model) {
        if (model.getMeta().getCode() == 200) {

            int user_id = model.getData().getId();
            String phone = model.getData().getPhone();
            String account_number = model.getData().getAccountNumber();
            String name = model.getData().getName();
            String email = model.getData().getEmail();

            CacheUtil.putPreferenceInteger(IConfigSDK.SESSION_USER_ID, user_id, SetPinActivitySDK.this);
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_PHONE, phone, SetPinActivitySDK.this);
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_ACCOUNT_NUMBER, account_number, SetPinActivitySDK.this);
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_NAME, name, SetPinActivitySDK.this);
            CacheUtil.putPreferenceString(IConfigSDK.SESSION_EMAIL, email, SetPinActivitySDK.this);


            Intent intent = new Intent(SetPinActivitySDK.this, OtpActivitySDK.class);
            intent.putExtra(IConfigSDK.SESSION_PHONE, phone);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
