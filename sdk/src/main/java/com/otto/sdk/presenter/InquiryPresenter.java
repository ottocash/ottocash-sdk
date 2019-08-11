package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenter;
import com.otto.sdk.base.IView;
import com.otto.sdk.base.response.BaseResponse;
import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.model.dao.InquiryDao;


public class InquiryPresenter extends BasePresenter implements InquiryDao.IInquiryDao {

    private IInquiryView inquiryView;

    public InquiryPresenter(IView view) {
        this.inquiryView = (IInquiryView) view;
    }

    @Override
    public void getInquiry(InquiryRequest requestModel) {
        new InquiryDao(this, new BasePresenter.OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                InquiryResponse model = (InquiryResponse) br;
                inquiryView.handleInquiry(model);
            }
        }).onInquiry(requestModel, getContext());
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return inquiryView.getBaseActivity();
    }
}
