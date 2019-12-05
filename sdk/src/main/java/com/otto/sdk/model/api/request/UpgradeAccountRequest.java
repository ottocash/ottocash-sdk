package com.otto.sdk.model.api.request;

public class UpgradeAccountRequest  {

	private String passport_photo;
	private String account_number;
	private String id_card;

	public UpgradeAccountRequest(String passport_photo, String account_number, String id_card) {
		this.passport_photo = passport_photo;
		this.account_number = account_number;
		this.id_card = id_card;
	}

	public String getPassport_photo() {
		return passport_photo;
	}

	public void setPassport_photo(String passport_photo) {
		this.passport_photo = passport_photo;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
}