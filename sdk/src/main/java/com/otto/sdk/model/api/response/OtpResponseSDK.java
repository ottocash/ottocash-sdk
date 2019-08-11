package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otto.sdk.base.response.BaseResponseSDK;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtpResponseSDK extends BaseResponseSDK {
    private int code;
    private String message;
    private boolean status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
