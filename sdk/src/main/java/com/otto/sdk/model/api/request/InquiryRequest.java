package com.otto.sdk.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InquiryRequest {

    @JsonProperty("account_number")
    private String accountNumber;

    public InquiryRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
