package com.otto.sdk.base.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseSDK {

    @JsonProperty("meta")
    private BaseMetaResponseSDK baseMeta;
    @JsonProperty("data")
    private BaseDataResponseSDK baseData;

    private String status;


    public BaseMetaResponseSDK getBaseMeta() {
        return baseMeta;
    }

    public void setBaseMeta(BaseMetaResponseSDK baseMeta) {
        this.baseMeta = baseMeta;
    }

    public BaseDataResponseSDK getBaseData() {
        return baseData;
    }

    public void setBaseData(BaseDataResponseSDK baseData) {
        this.baseData = baseData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
