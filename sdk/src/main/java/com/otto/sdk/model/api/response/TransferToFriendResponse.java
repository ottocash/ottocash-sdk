package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import app.beelabs.com.codebase.base.response.BaseResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferToFriendResponse extends BaseResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("meta")
    private Meta meta;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Data {
        @SerializedName("date")
        private String date;

        @SerializedName("service_type")
        private String serviceType;

        @SerializedName("nominal")
        private String nominal;

        @SerializedName("destination_account_number")
        private String destinationAccountNumber;

        @SerializedName("description")
        private String description;

        @SerializedName("reference_number")
        private String referenceNumber;

        @SerializedName("status")
        private String status;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getNominal() {
            return nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        public String getDestinationAccountNumber() {
            return destinationAccountNumber;
        }

        public void setDestinationAccountNumber(String destinationAccountNumber) {
            this.destinationAccountNumber = destinationAccountNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getReferenceNumber() {
            return referenceNumber;
        }

        public void setReferenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Meta {
        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;

        @SerializedName("status")
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
}