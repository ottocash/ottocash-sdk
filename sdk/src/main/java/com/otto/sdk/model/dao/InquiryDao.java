package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.InquiryRequest;
import com.otto.sdk.model.api.response.InquiryResponse;
import com.otto.sdk.presenter.InquiryPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import app.beelabs.com.codebase.base.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class InquiryDao extends BaseDao {

    private InquiryPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IInquiryDao iInquiryDao;


    public interface IInquiryDao extends IDaoPresenter {
        void getInquiry(InquiryRequest requestModel);
    }

    public InquiryDao(IDao obj) {
        super(obj);
    }

    public InquiryDao(IInquiryDao iInquiryDao, InquiryPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iInquiryDao = iInquiryDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }


    public void onInquiry(InquiryRequest model, Context context) {
        Api.onInquiry(model, context, BaseDao.getInstance(this, iInquiryDao.getPresenter(), IConfig
                .KEY_API_INQUIRY).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (responseCode == IConfig.KEY_API_INQUIRY) {
            if (response.isSuccessful()) {
                InquiryResponse inquiryResponse = (InquiryResponse) br;
                onPresenterResponseCallback.call(inquiryResponse);
            }

        }
    }
}
