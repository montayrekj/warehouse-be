package com.springboot.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the customers database table.
 * 
 */
@Entity
@Table(name="customers")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="customer_id")
	private int customerId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Lob
	@Column(name="customer_address")
	private String customerAddress;

	@Lob
	@Column(name="customer_name")
	private String customerName;

	@Lob
	@Column(name="customer_number")
	private String customerNumber;

	@Column(name="modified_by")
	private int modifiedBy;

	@Column(name="modified_date")
	private Timestamp modifiedDate;
	
	@Column(name="customer_contact_person")
	private String customerContactPerson;
	
	@Column(name="customer_level")
	private int customerLevel;

	public Customer() {
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNumber() {
		return this.customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
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

	public String getCustomerContactPerson() {
		return customerContactPerson;
	}

	public void setCustomerContactPerson(String customerContactPerson) {
		this.customerContactPerson = customerContactPerson;
	}

	public int getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(int customerLevel) {
		this.customerLevel = customerLevel;
	}

}