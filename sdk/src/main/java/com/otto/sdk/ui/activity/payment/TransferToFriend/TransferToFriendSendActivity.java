package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.sdk.R;
import com.otto.sdk.model.general.PhoneContact;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import app.beelabs.com.codebase.base.BaseActivity;

public class TransferToFriendSendActivity extends BaseActivity {

    ImageView ivBack;
    TextView tvName;
    TextView tvHp;
    EditText etAmount;
    Button btnSubmit;
    ImageView imgAvatar;

    private PhoneContact mPhoneContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend_send);

        initComponent();
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
                Intent intent = new Intent(TransferToFriendSendActivity.this, TransferToFriendReviewActivity.class);
                startActivity(intent);
            }
        });
    }


    private void displayFriendInfo() {
        Picasso.get()
                .load(mPhoneContact.getPhotoURI())
                .placeholder(R.drawable.photo_circle_bg)
                .error(R.drawable.photo_circle_bg)
                .into(imgAvatar);

        tvName.setText(mPhoneContact.getName());
        tvHp.setText(mPhoneContact.getMobileNumber());
    }


    /**
     *
     * EVENT BUS
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        if (mPhoneContact != null) {
            EventBus.getDefault().removeStickyEvent(mPhoneContact);
        }

        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessage(PhoneContact phoneContact) {
        mPhoneContact = phoneContact;
        displayFriendInfo();
    }
}
