package com.springboot.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the sales_logs database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Orders.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	private String orderId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="modified_by")
	private int modifiedBy;

	@Column(name="modified_date")
	private Timestamp modifiedDate;

	@Column(name="paid_amount")
	private double paidAmount;

	@Column(name="total_amount")
	private double totalAmount;
	
	private int customer;
	
	@Column(name="cash_amount")
	private double cashAmount;
	
	@Column(name="term_amount")
	private double termAmount;
	
	@Column(name="term_due_date")
	private Date termDueDate;
	
	@Column(name="purchase_order_status")
	private int purchaseOrderStatus;
	
	private boolean collected;

	public Order() {
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public int getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public double getPaidAmount() {
		return this.paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getCustomer() {
		return customer;
	}

	public void setCustomer(int customer) {
		this.customer = customer;
	}

	public double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public double getTermAmount() {
		return termAmount;
	}

	public void setTermAmount(double termAmount) {
		this.termAmount = termAmount;
	}

	public Date getTermDueDate() {
		return termDueDate;
	}

	public void setTermDueDate(Date termDueDate) {
		this.termDueDate = termDueDate;
	}

	public int getPurchaseOrderStatus() {
		return purchaseOrderStatus;
	}

	public void setPurchaseOrderStatus(int purchaseOrderStatus) {
		this.purchaseOrderStatus = purchaseOrderStatus;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

}