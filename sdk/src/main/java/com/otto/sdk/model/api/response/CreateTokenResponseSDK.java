package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.otto.sdk.base.response.BaseResponseSDK;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTokenResponseSDK extends BaseResponseSDK {
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
        private Client client;

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Client {
            @JsonProperty("access_token")
            private String accessToken;
            private int createdAt;
            private String tokenType;
            private int expiresIn;

            public String getAccessToken() {
                return accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }

            public int getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(int createdAt) {
                this.createdAt = createdAt;
            }

            public String getTokenType() {
                return tokenType;
            }

            public void setTokenType(String tokenType) {
                this.tokenType = tokenType;
            }

            public int getExpiresIn() {
                return expiresIn;
            }

            public void setExpiresIn(int expiresIn) {
                this.expiresIn = expiresIn;
            }
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
