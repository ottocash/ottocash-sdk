package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.R;
import com.otto.sdk.ui.activity.payment.PaymentSuccessActivity;
import com.poovam.pinedittextfield.LinePinField;

import app.beelabs.com.codebase.base.BaseActivity;

public class TransferToFriendPinActivity extends BaseActivity {

    ImageView ivBack;
    LinePinField lineField;
    TextView errorMessage;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend_pin);

        initComponent();
        addTextWatcher(lineField);
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        lineField = findViewById(R.id.lineField);
        errorMessage = findViewById(R.id.errorMessage);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setVisibility(View.GONE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void addTextWatcher(EditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6) {
                    Intent intent = new Intent(TransferToFriendPinActivity.this, PaymentSuccessActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }
}
