package com.daniel.beans;

import com.daniel.enums.UserType;

public class SuccessfulLoginData {

	private long id;
	private int token;
	private UserType userType;

	public SuccessfulLoginData() {
	}

	public SuccessfulLoginData(long id, int token, UserType userType) {
		this.id = id;
		this.token = token;
		this.userType = userType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}
