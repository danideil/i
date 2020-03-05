package com.daniel.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.daniel.utils.JsonTimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@Entity
@Table(name="PURCHASES")
public class Purchase {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="quantity", nullable = false)
	private int quantity;
	
	@Column(name="purchasePrice", nullable = false)
	private float purchasePrice;
	
	@Column(name="purchaseDate", nullable = false, columnDefinition="TIMESTAMP")
	private Timestamp purchaseDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Coupon coupon;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;
	
// ---------------- Constructors: ---------------- //
	public Purchase() {

	}
// --------------- Getters/Setters: --------------- //


	public int getQuantity() {
		return quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@JsonSerialize(using = JsonTimestampSerializer.class)
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}


	@Override
	public String toString() {
		return "Purchase [id=" + id + ", quantity=" + quantity + ", purchasePrice=" + purchasePrice + ", purchaseDate="
				+ purchaseDate + ", coupon=" + coupon + ", customer=" + customer + "]";
	}



	
}
