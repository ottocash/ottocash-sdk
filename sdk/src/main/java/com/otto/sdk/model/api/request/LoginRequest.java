package com.otto.sdk.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest{
	private String pin;
	@JsonProperty("device_id")
	private String deviceId;
	private String phone;
	private String latitude;
	private String longitude;

    public LoginRequest(String phone) {
    	this.phone = phone;
    }

    public LoginRequest() {

    }

	public void setPin(String pin){
		this.pin = pin;
	}

	public String getPin(){
		return pin;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}
}