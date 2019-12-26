package com.otto.sdk.model.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentData implements Parcelable {
    private String responseDescription;
    private String referenceNumber;
    private String transactionDate;
    private String responseCode;

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.responseDescription);
        dest.writeString(this.referenceNumber);
        dest.writeString(this.transactionDate);
        dest.writeString(this.responseCode);
    }

    public PaymentData() {
    }

    protected PaymentData(Parcel in) {
        this.responseDescription = in.readString();
        this.referenceNumber = in.readString();
        this.transactionDate = in.readString();
        this.responseCode = in.readString();
    }

    public static final Parcelable.Creator<PaymentData> CREATOR = new Parcelable.Creator<PaymentData>() {
        @Override
        public PaymentData createFromParcel(Parcel source) {
            return new PaymentData(source);
        }

        @Override
        public PaymentData[] newArray(int size) {
            return new PaymentData[size];
        }
    };
}
