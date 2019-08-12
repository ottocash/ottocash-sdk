package com.otto.sdk.base;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.otto.sdk.R;
import com.otto.sdk.base.di.IProgressSDK;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseActivitySDK extends AppCompatActivity implements IViewSDK, ComponentCallbacks2 {
    private View rootView;

    public void setupCoconutContentView(int rootIdLayout){

        this.rootView = findViewById(rootIdLayout);
    }

    protected void onApiFailureCallback(String message, IPresenterSDK iView) {
        // --- default callback if not defined on child class --
        try {
            Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show();
            Log.e("Message:", message);


            if (rootView != null)
                showSnackbar(rootView, getResources().getString(R.string.coconut_internet_fail_message), Snackbar.LENGTH_INDEFINITE).show();
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

    }

    protected Snackbar showSnackbar(View view, String message, int duration) {
        final Snackbar snackbar = Snackbar.make(view, message, duration);

        snackbar.setAction(getResources().getString(R.string.coconut_reply_action_label), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();

                finish();
                startActivity(getIntent());
            }
        });

        return snackbar;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showFragment(Fragment fragment, int fragmentResourceID) {

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentResourceID, fragment);
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
        }
    }

    protected BaseFragmentSDK onBackFragment(BaseActivitySDK activity) {
        List fragments = activity.getSupportFragmentManager().getFragments();
        BaseFragmentSDK currentFragment = (BaseFragmentSDK) fragments.get(fragments.size() - 1);

        return currentFragment;
    }


    // handle progress dialog
    protected void showApiProgressDialogSDK(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter) {
        showApiProgressDialogSDK(appComponentSDK, presenter, null);
    }

    protected void showApiProgressDialogSDK(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter, String message) {
        IProgressSDK progress = appComponentSDK.getProgressDialogSDK();
        progress.showProgressDialogSDK(this, message, false);
        presenter.call();
    }

    protected void showApiProgressDialogSDK(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter, String message, boolean isCanceledOnTouch) {
        IProgressSDK progress = appComponentSDK.getProgressDialogSDK();
        progress.showProgressDialogSDK(this, message, isCanceledOnTouch);
        presenter.call();
    }


    protected void showApiCustomProgressDialog(AppComponentSDK appComponentSDK, BasePresenterSDKSDK presenter, String message) {
        IProgressSDK progress = appComponentSDK.getProgressDialogSDK();
        progress.showLoadingDialogSDK(new LoadingDialogComponentSDK(message, this, R.style.CoconutDialogFullScreen));
        presenter.call();

    }

    //--- end ----

    @Override
    public void handleFail(String message) {

    }

    @Override
    public BaseActivitySDK getBaseActivity() {
        return this;
    }
}
