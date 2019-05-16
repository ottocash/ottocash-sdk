package com.otto.sdk.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpVerificationRequest {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("otp_code")
    private String otpCode;

    public OtpVerificationRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}
