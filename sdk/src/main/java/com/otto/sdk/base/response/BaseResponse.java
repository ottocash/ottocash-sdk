package com.otto.sdk.base.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {

    @JsonProperty("meta")
    private BaseMetaResponse baseMeta;
    @JsonProperty("data")
    private BaseDataResponse baseData;

    private String status;


    public BaseMetaResponse getBaseMeta() {
        return baseMeta;
    }

    public void setBaseMeta(BaseMetaResponse baseMeta) {
        this.baseMeta = baseMeta;
    }

    public BaseDataResponse getBaseData() {
        return baseData;
    }

    public void setBaseData(BaseDataResponse baseData) {
        this.baseData = baseData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
