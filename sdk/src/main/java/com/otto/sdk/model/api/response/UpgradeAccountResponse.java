package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.beelabs.com.codebase.base.response.BaseResponse;

public class UpgradeAccountResponse extends BaseResponse {

	private String data;
	private Meta meta;

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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