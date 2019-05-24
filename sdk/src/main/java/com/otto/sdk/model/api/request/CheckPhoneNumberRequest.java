package com.otto.sdk.model.api.request;

public class CheckPhoneNumberRequest {
	private String phone;

	public String setPhone(String phone){
		this.phone = phone;
        return phone;
    }

	public String getPhone(){
		return phone;
	}
}
