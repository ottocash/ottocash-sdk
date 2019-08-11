package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otto.sdk.base.response.BaseResponse;
import com.otto.sdk.model.api.misc.SecurityQuestionsItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityQuestionResponse extends BaseResponse {
    private Data data;
    private Meta meta;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Data {
        private List<SecurityQuestionsItem> securityQuestions;

        public List<SecurityQuestionsItem> getSecurityQuestions() {
            return securityQuestions;
        }

        public void setSecurityQuestions(List<SecurityQuestionsItem> securityQuestions) {
            this.securityQuestions = securityQuestions;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Meta {
        private int code;
        private String message;
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
