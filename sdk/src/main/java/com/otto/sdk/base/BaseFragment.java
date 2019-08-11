package com.otto.sdk.base;

import android.support.v4.app.Fragment;

import com.otto.sdk.R;
import com.otto.sdk.base.di.IProgress;


public abstract class BaseFragment extends Fragment implements IView {


    protected void showApiProgressDialog(AppComponent appComponent, BasePresenter presenter) {
        showApiProgressDialog(appComponent, presenter, null);
    }

    protected void showApiProgressDialog(AppComponent appComponent, BasePresenter presenter, String message) {
        IProgress progress = appComponent.getProgressDialog();
        progress.showProgressDialog(getActivity(), message, true);
        presenter.call();
    }

    protected void showApiProgressDialog(AppComponent appComponent, BasePresenter presenter, String message, boolean isCanceledOnTouch) {
        IProgress progress = appComponent.getProgressDialog();
        progress.showProgressDialog(getActivity(), message, isCanceledOnTouch);
        presenter.call();
    }

    protected void showLoadingDialog(AppComponent appComponent, BasePresenter presenter, String message) {
        IProgress progress = appComponent.getProgressDialog();
        progress.showLoadingDialog(new LoadingDialogComponent(message, getActivity(), R.style.CoconutDialogFullScreen));
        presenter.call();
    }

    @Override
    public void handleFail(String message) {

    }
}
