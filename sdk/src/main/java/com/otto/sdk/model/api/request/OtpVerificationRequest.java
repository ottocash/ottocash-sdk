package com.otto.sdk.model.api.request;

public class OtpVerificationRequest {


    /**
     * user_id : 323
     * otp_code : 918139
     */

    private int user_id;
    private String otp_code;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }
}
