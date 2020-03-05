package com.daniel.beans;

public class ErrorBean {

	private int errorNumber;
	private String errorMessage;
	private String errorName;
	private boolean isShowStackTrace;
	
	public ErrorBean(int errorNumber, String errorMessage, String errorName, boolean isShowStackTrace) {
		this.errorNumber = errorNumber;
		this.errorMessage = errorMessage;
		this.errorName = errorName;
		this.isShowStackTrace = isShowStackTrace;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorName() {
		return errorName;
	}

	public boolean isShowStackTrace() {
		return isShowStackTrace;
	}
	
}
