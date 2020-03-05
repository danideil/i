package com.daniel.beans;

public class UserLoginDetails {
	private String email;
	private String password;
	
	public UserLoginDetails() {
	}
	
	public UserLoginDetails(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLoginDetails [userName=" + email + ", password=" + password + "]";
	}
}
