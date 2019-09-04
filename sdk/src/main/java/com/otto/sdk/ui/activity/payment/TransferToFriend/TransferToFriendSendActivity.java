package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.otto.sdk.IConfig;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.R;
import com.otto.sdk.interfaces.ITransferToFriendView;
import com.otto.sdk.model.api.request.TransferToFriendRequest;
import com.otto.sdk.model.api.response.TransferToFriendResponse;
import com.otto.sdk.model.general.PhoneContact;
import com.otto.sdk.presenter.TransferToFriendPresenter;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class TransferToFriendSendActivity extends BaseActivity implements ITransferToFriendView {

    private final String TAG = this.getClass().getSimpleName();

    ImageView ivBack;
    TextView tvName;
    TextView tvHp;
    EditText etAmount;
    Button btnSubmit;
    ImageView imgAvatar;

    private PhoneContact mPhoneContact;

    private String phone;
    private String nominal;
    private String destinationPhone;
    private String numberContact;
    private String nameContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend_send);

        initComponent();
        displayFriendInfo();
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvName = findViewById(R.id.tv_name);
        tvHp = findViewById(R.id.tv_hp);
        etAmount = findViewById(R.id.et_amount);
        btnSubmit = findViewById(R.id.btn_submit);
        imgAvatar = findViewById(R.id.img_avatar);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallApiTransferToFriend();
            }
        });
    }


    private void displayFriendInfo() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
            nameContact = extras.getString(IConfig.KEY_NAME_CONTACT);
        }

        /*Picasso.get()
                .load(mPhoneContact.getPhotoURI())
                .placeholder(R.drawable.photo_circle_bg)
                .error(R.drawable.photo_circle_bg)
                .into(imgAvatar);*/

        tvName.setText(nameContact);
        tvHp.setText(numberContact);
    }


    private void onCallApiTransferToFriend() {
        final TransferToFriendRequest model = new TransferToFriendRequest();
        phone = CacheUtil.getPreferenceString(IConfig.SESSION_PHONE, TransferToFriendSendActivity.this);

        model.setAccountNumber(phone);
        model.setAmount(etAmount.getText().toString());
        model.setCustomerReference(numberContact);

        Log.d(TAG, " Request: " + new Gson().toJson(model));

        showApiProgressDialog(OttoCashSdk.getAppComponent(), new TransferToFriendPresenter(this) {
            @Override
            public void call() {
                getTransferToFriend(model);
            }
        }, "Loading");
    }


    @Override
    public void handleResponseTransferToFriend(TransferToFriendResponse model) {
        if (model.getMeta().getCode() == 200) {


            nominal = model.getData().getNominal();
            destinationPhone = model.getData().getDestinationAccountNumber();

            Intent intent = new Intent(TransferToFriendSendActivity.this, TransferToFriendReviewActivity.class);
            intent.putExtra(IConfig.KEY_NOMINAL, nominal);
            intent.putExtra(IConfig.KEY_DESTINATION, destinationPhone);
            intent.putExtra(IConfig.KEY_NAME_CONTACT, nameContact);
            intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);
            startActivity(intent);

        } else {
            Toast.makeText(this, model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }


}
