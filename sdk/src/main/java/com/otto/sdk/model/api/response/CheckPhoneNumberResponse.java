package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.beelabs.com.codebase.base.response.BaseResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckPhoneNumberResponse extends BaseResponse {

    /**
     * data : {"is_existing":true}
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
         * is_existing : true
         */

        private boolean is_existing;

        public boolean isIs_existing() {
            return is_existing;
        }

        public void setIs_existing(boolean is_existing) {
            this.is_existing = is_existing;
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
