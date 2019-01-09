package com.springboot.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sales database table.
 * 
 */
@Entity
@Table(name="sales")
@NamedQuery(name="Sale.findAll", query="SELECT s FROM Sale s")
public class Sale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="sales_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int salesId;
	
	private double amount;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="order_id")
	private String orderId;

	@Column(name="sales_type")
	private String salesType;

	public Sale() {
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getSalesId() {
		return this.salesId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public String getSalesType() {
		return this.salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

}