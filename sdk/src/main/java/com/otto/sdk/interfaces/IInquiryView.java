package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.InquiryResponse;

import app.beelabs.com.codebase.base.IView;

public interface IInquiryView extends IView {

    void handleInquiry(InquiryResponse model);
}
