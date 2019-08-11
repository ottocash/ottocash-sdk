package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenterSDKSDK;
import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.interfaces.IInquiryViewSDK;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponseSDK;
import com.otto.sdk.model.dao.InquiryDaoSDK;


public class InquiryPresenterSDK extends BasePresenterSDKSDK implements InquiryDaoSDK.IInquiryDao {

    private IInquiryViewSDK inquiryView;

    public InquiryPresenterSDK(IViewSDK view) {
        this.inquiryView = (IInquiryViewSDK) view;
    }

    @Override
    public void getInquiry(InquiryRequest requestModel) {
        new InquiryDaoSDK(this, new BasePresenterSDKSDK.OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                InquiryResponseSDK model = (InquiryResponseSDK) br;
                inquiryView.handleInquiry(model);
            }
        }).onInquiry(requestModel, getContext());
    }

    @Override
    public BasePresenterSDKSDK getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return inquiryView.getBaseActivity();
    }
}
