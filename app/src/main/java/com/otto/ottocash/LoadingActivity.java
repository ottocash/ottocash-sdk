package com.otto.ottocash;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.otto.sdk.ui.component.dialog.OttocashSDKDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {


    @BindView(R.id.butn)
    Button butn;
    OttocashSDKDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        butn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog == null) {
                    dialog = OttocashSDKDialog.openLoadingDialog(LoadingActivity.this);
                } else {
                }
            }
        });
//        Sprite pulse = new Pulse();
////        process.getProgressDrawable()
//        process.setIndeterminateDrawable(pulse);
//        process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.Grey_C4C4C4),android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialog.closeLoadingDialog(LoadingActivity.this);
    }
}
