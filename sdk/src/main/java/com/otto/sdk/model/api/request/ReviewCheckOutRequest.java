package com.otto.sdk.model.api.request;

public class ReviewCheckOutRequest {


    /**
     * account_number : 085880507999
     * amount : 55000
     * fee : 500
     * product_name : Pembayaran
     * biller_id : PURCHASE_ELEVENIA
     * customer_reference_number : UPN00d000458
     * product_code : PYMNT
     * partner_code : P000001
     * latitude : 10.232444
     * longitude : -6.4312323
     * device_id : 213123123123123
     */

    private String account_number;
    private int amount;
    private int fee;
    private String product_name;
    private String biller_id;
    private String customer_reference_number;
    private String product_code;
    private String partner_code;
    private String latitude;
    private String longitude;
    private String device_id;

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public void setBiller_id(String biller_id) {
        this.biller_id = biller_id;
    }

    public String getCustomer_reference_number() {
        return customer_reference_number;
    }

    public void setCustomer_reference_number(String customer_reference_number) {
        this.customer_reference_number = customer_reference_number;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getPartner_code() {
        return partner_code;
    }

    public void setPartner_code(String partner_code) {
        this.partner_code = partner_code;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
