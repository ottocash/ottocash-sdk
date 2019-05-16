package com.otto.sdk.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewCheckOutRequest {
    @JsonProperty("account_number")
    private String accountNumber;
    private int amount;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("customer_reference_number")
    private String customerReferenceNumber;
    private int fee;
    private String latitude;
    @JsonProperty("biller_id")
    private String billerId;
    @JsonProperty("partner_code")
    private String partnerCode;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("product_name")
    private String productName;
    private String longitude;

    public ReviewCheckOutRequest(String accountNumber, int amount, String deviceId, String customerReferenceNumber,
                                 int fee, String latitude, String partnerCode, String productCode, String productName,
                                 String longitude) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.deviceId = deviceId;
        this.customerReferenceNumber = customerReferenceNumber;
        this.fee = fee;
        this.latitude = latitude;
        this.partnerCode = partnerCode;
        this.productCode = productCode;
        this.productName = productName;
        this.longitude = longitude;
    }

    public ReviewCheckOutRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setCustomerReferenceNumber(String customerReferenceNumber) {
        this.customerReferenceNumber = customerReferenceNumber;
    }

    public String getCustomerReferenceNumber() {
        return customerReferenceNumber;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getBillerId() {
        return billerId;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
