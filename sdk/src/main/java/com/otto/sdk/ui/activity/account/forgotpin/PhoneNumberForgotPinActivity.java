package com.otto.sdk.ui.activity.account.forgotpin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.otto.sdk.AppActivity;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IForgotPinView;
import com.otto.sdk.model.api.request.ForgotPinInquiryRequest;
import com.otto.sdk.presenter.ForgotPinPresenter;
import com.otto.sdk.ui.activity.account.activation.OtpLoginActivity;
import com.otto.sdk.ui.activity.account.activation.PinLoginActivity;
import com.otto.sdk.ui.component.support.FormValidation;

import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;

import static com.otto.sdk.IConfig.OC_SESSION_PHONE;

public class PhoneNumberForgotPinActivity extends AppActivity implements IForgotPinView {

    EditText edtPhoneNumber;
    Button btnSave;
    ImageView ivBack;

    private boolean isFormValidationSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_forgot_pin);

        initComponent();
        actionInquiry();
    }

    private void initComponent() {
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnSave = findViewById(R.id.btnSave);
        ivBack = findViewById(R.id.ivBack);

        addTextWatcher(edtPhoneNumber);
    }


    private void actionInquiry() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFormValidationSuccess) {
                    onCallApiForgotPinInquiry();
                }
            }
        });
    }


    private void onCallApiForgotPinInquiry() {
        final ForgotPinInquiryRequest model = new ForgotPinInquiryRequest();

        model.setPhone_number(edtPhoneNumber.getText().toString());


        showApiProgressDialog(OttoCashSdk.getAppComponent(), new ForgotPinPresenter(this) {
            @Override
            public void call() {
                getForgotPinInquiry(model);

            }
        }, "Loading");
    }


    @Override
    public void handleForgotPin(BaseResponse model) {

    }

    @Override
    public void handleForgotPinInquiry(BaseResponse model) {
        Intent intent = new Intent(this, OtpLoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(IConfig.OC_SESSION_PHONE, edtPhoneNumber.getText().toString());
        intent.putExtra(IConfig.OC_FORGOT_PIN, true);
        startActivity(intent);
        //finish();
    }

    private void validateForm() {
        String phone = edtPhoneNumber.getText().toString();

        if (FormValidation.required(phone) && FormValidation.validPhone(phone)) {
            isFormValidationSuccess = true;
            btnSave.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_d04a55));
        } else {
            isFormValidationSuccess = false;
            btnSave.setBackground(ContextCompat.getDrawable(this, R.drawable.button_primary_selected_bg));
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