package com.springboot.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.springboot.entities.OrdersItem;

public class OrderModel {
	
	private String orderId;

	private String createdBy;

	private Timestamp createdDate;
	
	private List<OrdersItem> orderItems;
	
	private String customer;
	
	private int customerLevel;
	
	private double paidAmount;

	private double totalAmount;
	
	private double cashAmount;
	
	private double termAmount;
	
	private int purchaseOrderStatus;
	
	private Date termDueDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public List<OrdersItem> getSalesLogsItem() {
		return orderItems;
	}

	public void setSalesLogsItem(List<OrdersItem> salesLogsItem) {
		this.orderItems = salesLogsItem;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
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

	public int getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(int customerLevel) {
		this.customerLevel = customerLevel;
	}
}
