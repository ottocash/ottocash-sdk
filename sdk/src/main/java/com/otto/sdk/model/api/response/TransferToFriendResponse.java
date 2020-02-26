package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.beelabs.com.codebase.base.response.BaseResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferToFriendResponse extends BaseResponse {

    /**
     * data : {"reference_number":"OA1Q00000014","date":"26-Feb-2020 16:33","service_type":"Transfer OTTOCASH","destination_account_number":"OTTOCASH|085880507999","nominal":"Rp 1","description":"overbook","status":"Sukses"}
     * meta : {"status":true,"code":200,"message":"OK"}
     */

    private DataBean data;
    private MetaBean meta;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public static class DataBean {
        /**
         * reference_number : OA1Q00000014
         * date : 26-Feb-2020 16:33
         * service_type : Transfer OTTOCASH
         * destination_account_number : OTTOCASH|085880507999
         * nominal : Rp 1
         * description : overbook
         * status : Sukses
         */

        private String reference_number;
        private String date;
        private String service_type;
        private String destination_account_number;
        private String nominal;
        private String description;
        private String status;

        public String getReference_number() {
            return reference_number;
        }

        public void setReference_number(String reference_number) {
            this.reference_number = reference_number;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
        }

        public String getDestination_account_number() {
            return destination_account_number;
        }

        public void setDestination_account_number(String destination_account_number) {
            this.destination_account_number = destination_account_number;
        }

        public String getNominal() {
            return nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class MetaBean {
        /**
         * status : true
         * code : 200
         * message : OK
         */

        private boolean status;
        private int code;
        private String message;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

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
    }
}