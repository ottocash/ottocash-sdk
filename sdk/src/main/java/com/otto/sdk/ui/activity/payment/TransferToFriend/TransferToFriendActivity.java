package com.otto.sdk.ui.activity.payment.TransferToFriend;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.otto.sdk.R;
import com.otto.sdk.ui.fragment.TransferToFriend.TransferToFriendHistoryFragment;
import com.otto.sdk.ui.fragment.TransferToFriend.TransferToFriendListFragment;

import app.beelabs.com.codebase.base.BaseActivity;

public class TransferToFriendActivity extends BaseActivity {

    ImageView ivBack;
    Button btnTab1;
    Button btnTab2;
    View lineTab1;
    View lineTab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_friend);

        initComponent();
        tabSelected(1);
    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        btnTab1 = findViewById(R.id.btn_tab_1);
        btnTab2 = findViewById(R.id.btn_tab_2);
        lineTab1 = findViewById(R.id.line_tab_1);
        lineTab2 = findViewById(R.id.line_tab_2);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabSelected(1);
            }
        });

        btnTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabSelected(2);
            }
        });
    }

    private void tabSelected(int position) {
        if (position == 1) {
            btnTab1.setTextColor(ContextCompat.getColor(this, R.color.Red_d04a55));
            btnTab2.setTextColor(ContextCompat.getColor(this, R.color.Grey_484848));
            lineTab1.setVisibility(View.VISIBLE);
            lineTab2.setVisibility(View.GONE);

            showFragment(new TransferToFriendListFragment(), R.id.fragment_container);
        } else if (position == 2) {
            btnTab1.setTextColor(ContextCompat.getColor(this, R.color.Grey_484848));
            btnTab2.setTextColor(ContextCompat.getColor(this, R.color.Red_d04a55));
            lineTab1.setVisibility(View.GONE);
            lineTab2.setVisibility(View.VISIBLE);

            showFragment(new TransferToFriendHistoryFragment(), R.id.fragment_container);
        }
    }
}
