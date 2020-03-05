package com.daniel.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="CUSTOMERS")
public class Customer {

	@Id
	private long id;
	
	@Length(max = 115) 
	@Column(name="address", nullable = false)
	private String address;

	@Length(max = 15) 
	@Column(name="phone", nullable = false)
	private String phone;
	
	@Length(max = 45) 
	@Column(name="fullName", nullable = false)
	private String fullName;
	
	@Column(name="isMarried", nullable = false)
	private boolean isMarried;
	
	@Column(name="amountOfKids", nullable = false)
	private int amountOfKids;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@MapsId
	private User user;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "customer")
	private List<Purchase> purchases;
	
// ---------------- Constructors: ---------------- //
	public Customer() {
	
	}
// --------------- Getters/Setters: --------------- //

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}

	public int getAmountOfKids() {
		return amountOfKids;
	}

	public void setAmountOfKids(int amountOfKids) {
		this.amountOfKids = amountOfKids;
	}
	
//	public long getCustomerId(User user) {
//		long customerId = user.getId();
//		return customerId;
//	}
//
//	public long getCustomerId() {
//		return id;
//	}
//
//	public void setCustomerId(long customerId) {
//		this.id = customerId;
//	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

// ------------------------------------------------- //
}
