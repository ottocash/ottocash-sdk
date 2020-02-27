package com.otto.sdk.ui.activity.topup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.otto.sdk.IConfig;
import com.otto.sdk.R;
import com.otto.sdk.ui.activity.dashboard.DashboardSDKActivity;
import com.otto.sdk.ui.component.support.UiUtil;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;

public class TopUpActivity extends BaseActivity {

    Button btnBack;
    ImageView ivBack;
    TextView tvMbank3;
    TextView tvMbank5;
    TextView tvAtm4;
    TextView tvAtm5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        initComponent();
        initContent();
    }

    private void initComponent() {
        btnBack = findViewById(R.id.btnBack);
        ivBack = findViewById(R.id.ivBack);
        tvAtm4 = findViewById(R.id.tvAtm4);
        tvAtm5 = findViewById(R.id.tvAtm5);
        tvMbank3 = findViewById(R.id.tvMbank3);
        tvMbank5 = findViewById(R.id.tvMbank5);
        tvAtm4.setText(UiUtil.getHTMLContent(getString(R.string.atm_4)));
        tvMbank3.setText(UiUtil.getHTMLContent(getString(R.string.mbank_3)));

        String phone = CacheUtil.getPreferenceString(IConfig.OC_SESSION_PHONE, TopUpActivity.this);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String black = "5. Masukkan nomor virtual account Kamu : ";
        SpannableString blackSpannable = new SpannableString(black);
        blackSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.Black_000000)), 0, black.length(), 0);
        builder.append(blackSpannable);

        SpannableString blueSpannable = new SpannableString(phone);
        blueSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, phone.length(), 0);
        blueSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.Blue_056fb6)), 0, phone.length(), 0);
        builder.append(blueSpannable);
        tvAtm5.setText(builder, TextView.BufferType.SPANNABLE);
        tvMbank5.setText(builder, TextView.BufferType.SPANNABLE);
    }

    private void initContent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopUpActivity.this, DashboardSDKActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                TopUpActivity.this.startActivity(intent);
            }
        });
    }

}
