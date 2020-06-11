package com.otto.sdk.model.api.request;

public class OtpVerificationRequest {


    /**
     * user_id : 323
     * otp_code : 918139
     */

    private String phone_number;
    private String otp_code;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }
}
