package com.otto.sdk.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    private String pin;
    private String answer;

    @JsonProperty("device_id")
    private String deviceId;
    private String phone;

    @JsonProperty("birth_date")
    private int birthDate;
    private String latitude;
    private String name;

    @JsonProperty("security_question_id")
    private String securityQuestionId;
    private String email;
    private String platform;
    private String longitude;

    public RegisterRequest(String phone, String name, String email) {
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    public RegisterRequest(String preferenceString) {
    }

    public RegisterRequest() {

    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSecurityQuestionId(String securityQuestionId) {
        this.securityQuestionId = securityQuestionId;
    }

    public String getSecurityQuestionId() {
        return securityQuestionId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
