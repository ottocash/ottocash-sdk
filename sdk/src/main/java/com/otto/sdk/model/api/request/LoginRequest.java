package com.otto.sdk.model.api.request;

public class LoginRequest{

	/**
	 * phone : 085659791134
	 * pin : 333333
	 * latitude : 10.232444
	 * longitude : -6.4312323
	 * device_id : 1121
	 */

	private String phone;
	private String pin;
	private String latitude;
	private String longitude;
	private String device_id;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
}