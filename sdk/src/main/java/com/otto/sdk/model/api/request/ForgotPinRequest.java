package com.otto.sdk.model.api.request;

public class ForgotPinRequest {

    /**
     * phone_number : 085880507666
     * pin : 000000
     * otp : 270379
     */

    private String phone_number;
    private String pin;
    private String otp;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
