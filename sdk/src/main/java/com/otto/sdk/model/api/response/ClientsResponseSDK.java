package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otto.sdk.base.response.BaseResponseSDK;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientsResponseSDK extends BaseResponseSDK {
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
            private String id;
            private String secret;
            private String email;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
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
