package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseActivity;

public class TransferToFriendSendActivity extends BaseActivity {

    ImageView ivBack;
    TextView tvName;
    TextView tvHp;
    EditText et_amount;
    Button btnSubmit;
    ImageView imgAvatar;


    private String numberContact;
    private String nameContact;
    private String nominalTransferToFriend;

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
        et_amount = findViewById(R.id.et_amount);
        btnSubmit = findViewById(R.id.btn_submit);
        imgAvatar = findViewById(R.id.img_avatar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            numberContact = extras.getString(IConfig.KEY_NUMBER_CONTACT);
            nameContact = extras.getString(IConfig.KEY_NAME_CONTACT);
        }

        tvName.setText(nameContact);
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
                nominalTransferToFriend = et_amount.getText().toString();

                if (nominalTransferToFriend.isEmpty()) {
                    Toast.makeText(TransferToFriendSendActivity.this, "Masukkan nominal transfer", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(TransferToFriendSendActivity.this, TransferToFriendReviewActivity.class);
                    intent.putExtra(IConfig.KEY_NOMINAL_TRANSFER_TO_FRIEND, nominalTransferToFriend);
                    intent.putExtra(IConfig.KEY_NAME_CONTACT, nameContact);
                    intent.putExtra(IConfig.KEY_NUMBER_CONTACT, numberContact);
                    startActivity(intent);
                }
            }
        });

    }

    //HIT INQUIRY

    //IF RESPONSE REGISTERED NEXT REVIEW

    //ELSE NO TIDAK TERDAFTAR

}
