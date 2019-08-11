package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.base.BaseDaoSDKSDK;
import com.otto.sdk.base.IDaoSDK;
import com.otto.sdk.base.IDaoPresenter;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.model.api.ApiSDK;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponseSDK;
import com.otto.sdk.presenter.InquiryPresenterSDK;


import retrofit2.Response;

public class InquiryDaoSDK extends BaseDaoSDKSDK {


    private InquiryPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback;
    private IInquiryDao iInquiryDao;


    public interface IInquiryDao extends IDaoPresenter {
        void getInquiry(InquiryRequest requestModel);
    }

    public InquiryDaoSDK(IDaoSDK obj) {
        super(obj);
    }

    public InquiryDaoSDK(InquiryDaoSDK.IInquiryDao iInquiryDao, InquiryPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iInquiryDao = iInquiryDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }


    public void onInquiry(InquiryRequest model, Context context) {
        ApiSDK.onInquiry(model, context, BaseDaoSDKSDK.getInstance(this, iInquiryDao.getPresenter(), IConfigSDK
                .KEY_API_INQUIRY).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfigSDK.KEY_API_INQUIRY) {
                InquiryResponseSDK inquiryResponse = (InquiryResponseSDK) br;
                onPresenterResponseCallback.call(inquiryResponse);
            }
        }
    }
}
