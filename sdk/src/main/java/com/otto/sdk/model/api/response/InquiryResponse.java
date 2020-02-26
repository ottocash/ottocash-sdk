package com.otto.sdk.model.api.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.base.response.DefaultMetaResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InquiryResponse extends BaseResponse {

    /**
     * data : {"id":499,"name":"ardi","phone":"085880507999","email":"ardi@clappingape.com","account_number":"085880507999","birth_date":-25200,"latitude":"37.4219983333333","longitude":"-122.084","emoney_balance":"600000","account_type":"Registered","currency":"IDR","verify_status":0}
     * meta : {"status":true,"code":200,"message":"Data didapatkan."}
     */

    private DataBean data;
    private DefaultMetaResponse meta;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public DefaultMetaResponse getMeta() {
        return meta;
    }

    public void setMeta(DefaultMetaResponse meta) {
        this.meta = meta;
    }

    public static class DataBean {
        /**
         * id : 499
         * name : ardi
         * phone : 085880507999
         * email : ardi@clappingape.com
         * account_number : 085880507999
         * birth_date : -25200
         * latitude : 37.4219983333333
         * longitude : -122.084
         * emoney_balance : 600000
         * account_type : Registered
         * currency : IDR
         * verify_status : 0
         */

        private int id;
        private String name;
        private String phone;
        private String email;
        private String account_number;
        private int birth_date;
        private String latitude;
        private String longitude;
        private String emoney_balance;
        private String account_type;
        private String currency;
        private int verify_status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public int getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(int birth_date) {
            this.birth_date = birth_date;
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

        public String getEmoney_balance() {
            return emoney_balance;
        }

        public void setEmoney_balance(String emoney_balance) {
            this.emoney_balance = emoney_balance;
        }

        public String getAccount_type() {
            return account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }
    }
}
