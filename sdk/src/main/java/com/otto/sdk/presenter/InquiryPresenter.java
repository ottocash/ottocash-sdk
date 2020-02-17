package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.interfaces.IInquiryView;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.model.dao.InquiryDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

public class InquiryPresenter extends BasePresenter implements InquiryDao.IInquiryDao {

    private IInquiryView inquiryView;

    public InquiryPresenter(IView view) {
        this.inquiryView = (IInquiryView) view;
    }


        @Override
        public void getInquiry (InquiryRequest requestModel){
            new InquiryDao(this, new OnPresenterResponseCallback() {
                @Override
                public void call(BaseResponse br) {
                    InquiryResponse model = (InquiryResponse) br;
                    inquiryView.handleInquiry(model);
                }


            }).onInquiry(requestModel, getContext());
        }


    @Override
    public BasePresenter getPresenter() {
        return BasePresenter.getInstance(inquiryView,this);
    }


    @Override
    public Context getContext() {
        return inquiryView.getBaseActivity();
    }
}
