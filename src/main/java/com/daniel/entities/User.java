package com.daniel.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.daniel.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="USERS")
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Length(max = 115) 
	@Column(name="Email", unique = true, nullable = false)
	private String email;
	
	@Length(max = 55) 
	@Column(name="UserName", nullable = false)
	private String userName;
	
	@Column(name="Password", nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="UserType", nullable = false)
	private UserType userType;
	
	@Column(name="IsActive", nullable = false)
	private boolean isActive;
	
	@JsonIgnore
	@ManyToOne
	private Company company;

	// ---------------- Constructor: ---------------- //
	public User() {
	}

	// ---------------- Getters & Setters: ---------------- //
	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}
	
}
