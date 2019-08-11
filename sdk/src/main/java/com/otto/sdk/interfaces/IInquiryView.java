package com.otto.sdk.interfaces;

import com.otto.sdk.base.IView;
import com.otto.sdk.model.api.response.InquiryResponse;


public interface IInquiryView extends IView {

    void handleInquiry(InquiryResponse model);
}
