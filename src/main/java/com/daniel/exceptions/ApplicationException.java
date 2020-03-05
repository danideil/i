package com.daniel.exceptions;

import com.daniel.enums.ErrorTypes;

@SuppressWarnings("serial")
public class ApplicationException extends Exception {	
	
	private ErrorTypes errorType;
	
	public ApplicationException(ErrorTypes errorType, String message) {
		super(message);
		this.errorType = errorType;
	}
	
	public ApplicationException(Exception e, ErrorTypes errorType, String message) {
		super(message, e);
		this.errorType = errorType;
	}
	
	public ErrorTypes getErrorType() {
		return this.errorType;
	}
}