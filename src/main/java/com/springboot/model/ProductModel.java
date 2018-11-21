package com.springboot.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductModel implements Serializable{
	
	private int productId;

	/*private String createdBy;

	private Timestamp dateCreated;

	private Timestamp dateModified;

	;*/
	
	private double sellPrice;
	
	private double buyPrice;

	private String productCode;

	private String productName;

	private float quantity;

	private float quantityLimit;

	private String unit;
	
	private String supplier;
	
	private int createdBy;
	
	private int modifiedBy;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getQuantityLimit() {
		return quantityLimit;
	}

	public void setQuantityLimit(float quantityLimit) {
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
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
