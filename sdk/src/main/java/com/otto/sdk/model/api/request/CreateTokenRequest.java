package com.otto.sdk.model.api.request;

public class CreateTokenRequest {


    /**
     * grant_type : client_credentials
     * client_id : 95eeb55af545c9ae295e8a6b772b2344abf11080ae0f3134ad850e79b49244ef
     * client_secret : 6517eb6f89378e987d3f508db0f1cbbb317d571e557a9a0c1d07108ba6e0e588
     */

    private String grant_type;
    private String client_id;
    private String client_secret;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
