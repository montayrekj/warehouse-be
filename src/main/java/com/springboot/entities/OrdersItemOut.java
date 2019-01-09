package com.springboot.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the orders_item_out database table.
 * 
 */
@Entity
@Table(name="orders_item_out")
@NamedQuery(name="OrdersItemOut.findAll", query="SELECT o FROM OrdersItemOut o")
public class OrdersItemOut implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="out_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int outId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="order_item_id")
	private int orderItemId;

	@Column(name="out_quantity")
	private double outQuantity;

	public OrdersItemOut() {
	}

	public int getOutId() {
		return this.outId;
	}

	public void setOutId(int outId) {
		this.outId = outId;
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

	public int getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public double getOutQuantity() {
		return this.outQuantity;
	}

	public void setOutQuantity(double outQuantity) {
		this.outQuantity = outQuantity;
	}

}