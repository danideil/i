package com.daniel.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import com.daniel.enums.Categories;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="COMPANIES")
public class Company {

	@Id
	@GeneratedValue
	private Long id;
	
	@Length(max = 45)
	@Column(name="name", unique = true, nullable = false)
	private String name;
	
	@Length(max = 115)
	@Column(name="address", nullable = false)
	private String address;
	
	@Length(max = 15)
	@Column(name="phone", nullable = false)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(name="category", nullable = false)
	private Categories category;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="company")
	private List<User> users;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="company")
	private List<Coupon> coupons;
// --------------- Constructors: ------------ //
	
	public Company() {
	}

//	public Company(String name, String address, String phone, Categories category) {
//		this.name = name;
//		this.address = address;
//		this.phone = phone;
//		this.category = category;
//	}
	
// ------------- Getters/Setters: --------------- //


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


	public Categories getCategory() {
		return category;
	}


	public void setCategory(Categories category) {
		this.category = category;
	}

}
