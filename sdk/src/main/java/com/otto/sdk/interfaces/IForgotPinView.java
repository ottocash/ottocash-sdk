package com.otto.sdk.interfaces;

import app.beelabs.com.codebase.base.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

public interface IForgotPinView extends IView {

    void handleForgotPin(BaseResponse model);
}
