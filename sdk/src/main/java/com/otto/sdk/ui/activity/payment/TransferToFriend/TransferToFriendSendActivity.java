package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.presenter.InquiryPresenter;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;

import static com.otto.sdk.IConfig.KEY_PAYMENT_QR;

public class TransferToFriendSendActivity extends BaseActivity implements IInquiryView {

    ImageView ivBack;
    TextView tvName;
    TextView tvHp;
    EditText etAmount;
    Button btnSubmit;
    ImageView imgAvatar;
    TextView title;

    private Long amount = 0L;
    private TextWatcher mTextWatcher;
    private String numberContact;
    private String nameContact;
    private String nominalTransferToFriend;
    private String nameTujuanTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend_send);
        initComponent();
        addTextWatcher(etAmount);
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvName = findViewById(R.id.tv_name);
        tvHp = findViewById(R.id.tv_hp);
        etAmount = findViewById(R.id.et_amount);
        btnSubmit = findViewById(R.id.btn_submit);
        imgAvatar = findViewById(R.id.img_avatar);
        title = findViewById(R.id.tv_title_pembayaran);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
            nameTujuanTransfer = extras.getString(IConfig.KEY_ACCOUNT_NAME_TUJUAN);
            if(extras.getBoolean(KEY_PAYMENT_QR)){
                title.setText("Pembayaran");
            }
        }
        if(nameTujuanTransfer!=null){
            tvName.setText(nameTujuanTransfer);
        }else{
            tvName.setText(numberContact);
        }
        tvHp.setText(numberContact);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nominalTransferToFriend = etAmount.getText().toString();

                if (nominalTransferToFriend.isEmpty()) {
                    Toast.makeText(TransferToFriendSendActivity.this, "Masukkan nominal transfer", Toast.LENGTH_SHORT).show();
                } else {
                    onCallApiInquiry();
                }
            }
        });

    }

    private void onCallApiInquiry() {

        final InquiryRequest model = new InquiryRequest();
        model.setAccount_number(numberContact);

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new InquiryPresenter(TransferToFriendSendActivity.this) {
            @Override
            public void call() {
                getInquiry(model);

            }
        }, "Loading");
    }

    @Override
    public void handleInquiry(InquiryResponse model) {
        if (model.getMeta().getCode() == 200) {

            nameTujuanTransfer = model.getData().getName();

            Intent intent = new Intent(TransferToFriendSendActivity.this, TransferToFriendReviewActivity.class);
            intent.putExtra(IConfig.KEY_ACCOUNT_NAME_TUJUAN, nameTujuanTransfer);
            intent.putExtra(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND, nominalTransferToFriend);
            intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);
            intent.putExtra(KEY_PAYMENT_QR,getIntent().getExtras().getBoolean(KEY_PAYMENT_QR));
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void addTextWatcher(EditText input) {
        mTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    etAmount.removeTextChangedListener(mTextWatcher);
                    String currentInput = UiUtil.removeAllCharacterExpectNumbers(etAmount.getText().toString());
                    if (currentInput.equals("")) {
                        etAmount.setText(currentInput);
                        amount = 0L;
                    } else {
                        etAmount.setText(UiUtil.numberToIDR(Long.valueOf(currentInput), true));
                        etAmount.setSelection(etAmount.getText().toString().length());
                        amount = Long.valueOf(UiUtil.removeCurrencyFormat(etAmount.getText().toString()));
                    }
                    etAmount.addTextChangedListener(mTextWatcher);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        };

        input.addTextChangedListener(mTextWatcher);
    }

}
