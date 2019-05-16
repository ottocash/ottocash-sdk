package com.otto.sdk.model.api.request;

public class OtpRequest {
    private String phone;

    public OtpRequest(String phone) {
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
