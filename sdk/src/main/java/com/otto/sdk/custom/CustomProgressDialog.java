package com.otto.sdk.custom;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;

import com.otto.sdk.R;

public class CustomProgressDialog extends Dialog {

    private static String TAG = CustomProgressDialog.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static CustomProgressDialog dialog;

    /*@BindView(R.id.tv_text)
    TextView tvtext;*/

    private String loadingText;

    private CustomProgressDialog(@NonNull Context context) {
        super(context);
    }

    private CustomProgressDialog(@NonNull Context context, String loadingText) {
        super(context);

        this.loadingText = loadingText;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // configure dialog
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_progress);
        //ButterKnife.bind(this);

        /*if(loadingText != null && !loadingText.isEmpty())
            tvtext.setText(loadingText);*/
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        // remove element from memory
        if(dialog != null) dialog = null;
    }

    public static void showDialog(Context context){
        if(context == null) return;

        try {
            if(dialog == null)
                dialog = new CustomProgressDialog(context);

            if(!dialog.isShowing())
                dialog.show();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    public static void showDialog(Context context, String loadingText){
        if(context == null) return;

        try {
            if(dialog == null)
                dialog = new CustomProgressDialog(context, loadingText);

            if(!dialog.isShowing())
                dialog.show();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    public static void closeDialog(){
        try {
            if(dialog != null && dialog.isShowing())
                dialog.dismiss();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }


}
