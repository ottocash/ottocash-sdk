package com.otto.sdk.view.activities;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.otto.sdk.R;


public class CustomDialog {

    public static void alertDialog(Context context, String title, String message, String buttonText, boolean isCancelable) {
        try {
            if (!((Activity) context).isFinishing()) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alert);
                dialog.setCancelable(isCancelable);

                // set the custom dialog components - text, image and button
                TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
                TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
                Button btnPrimary = (Button) dialog.findViewById(R.id.btn_primary);

                tvTitle.setText(title);
                tvMessage.setText(message);
                btnPrimary.setText(buttonText);

                // if button is clicked, close the custom dialog
                btnPrimary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
