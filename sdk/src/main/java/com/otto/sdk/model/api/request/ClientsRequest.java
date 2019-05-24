package com.otto.sdk.model.api.request;

public class ClientsRequest {
	private String email;

	public ClientsRequest(String email) {
		this.email = email;
	}

    public ClientsRequest() {

    }

    public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}
