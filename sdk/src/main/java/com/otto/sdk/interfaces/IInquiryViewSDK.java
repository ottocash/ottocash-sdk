package com.otto.sdk.interfaces;

import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.model.api.response.InquiryResponseSDK;


public interface IInquiryViewSDK extends IViewSDK {

    void handleInquiry(InquiryResponseSDK model);
}
