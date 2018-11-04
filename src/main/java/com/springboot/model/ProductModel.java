package com.springboot.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductModel implements Serializable{
	
	private int productId;

	/*private String createdBy;

	private Timestamp dateCreated;

	private Timestamp dateModified;

	private String modifiedBy;;*/
	
	private double price;

	private String productCode;

	private String productName;

	private int quantity;

	private int quantityLimit;

	private String unit;
	
	private String supplier;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantityLimit() {
		return quantityLimit;
	}

	public void setQuantityLimit(int quantityLimit) {
		this.quantityLimit = quantityLimit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	/*public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Timestamp getDateModified() {
		return dateModified;
	}
	
	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}*/
}
