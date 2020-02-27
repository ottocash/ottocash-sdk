package com.otto.sdk.ui.component.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Pulse;
import com.otto.sdk.R;

import app.beelabs.com.codebase.base.BaseDialog;

public class OttocashSDKDialog extends BaseDialog {

    private static OttocashSDKDialog dialog;
    private Context context;
    private ProgressBar progressBar;

    public OttocashSDKDialog(@NonNull Context context, int style) {
        super(context, style);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ottocash_dialog);
        progressBar = findViewById(R.id.process);
        Sprite pulse = new Pulse();
        progressBar.setIndeterminateDrawable(pulse);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.Grey_C4C4C4),android.graphics.PorterDuff.Mode.SRC_IN);

        dialog = this;
    }

    synchronized public static OttocashSDKDialog openLoadingDialog(Activity activity) {
        if (dialog == null) {
            dialog = new OttocashSDKDialog(activity, R.style.SDKDialogFullscreen);
            dialog.show();
        }

        return dialog;
    }

    public static void closeLoadingDialog(Activity ac) {
        if (dialog == null) return;
        if (ac == null || !ac.isFinishing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
