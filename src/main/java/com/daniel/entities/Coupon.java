package com.daniel.entities;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import com.daniel.enums.Categories;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="COUPONS")
public class Coupon {

	@Id
	@GeneratedValue
	private long id;
	
	@Length(max = 45)
	@Column(name="title", nullable = false)
	private String title;
	
	@Column(name="price", nullable = false)
	private Float price;
	
	@Column(name="category", nullable = false)
	private Categories category;
	
	@Column(name="amount", nullable = false)
	private int amount;
	
	@Column(name="startDate", nullable = false)
	private Date startDate;
	
	@Column(name="endDate", nullable = false)
	private Date endDate;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="coupon")
	private List<Purchase> purchases;
	
	@JsonIgnore
	@ManyToOne
	private Company company;
	
	public Coupon() {
	}

	// Getters/Setters:
	public long getId() {
		return id;
	}

	public void setId(long couponId) {
		this.id = couponId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", price=" + price + ", category=" + category + ", startDate="
				+ startDate + ", endDate=" + endDate + ", purchases=" + purchases + ", company=" + company + "]";
	}	

}
