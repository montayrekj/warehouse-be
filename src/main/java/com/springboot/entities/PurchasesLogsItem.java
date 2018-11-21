package com.springboot.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the purchases_logs_item database table.
 * 
 */
@Entity
@Table(name="purchases_logs_item")
@NamedQuery(name="PurchasesLogsItem.findAll", query="SELECT p FROM PurchasesLogsItem p")
public class PurchasesLogsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int itemId;

	@Column(name="buy_price")
	private double buyPrice;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;

	@Column(name="quantity_added")
	private float quantityAdded;

	@Column(name="sell_price")
	private double sellPrice;

	private String supplier;

	private String unit;

	@Column(name="purchases_logs_id")
	private int purchasesLogsId;

	public PurchasesLogsItem() {
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public double getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getQuantityAdded() {
		return this.quantityAdded;
	}

	public void setQuantityAdded(float quantityAdded) {
		this.quantityAdded = quantityAdded;
	}

	public double getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getPurchasesLogsId() {
		return this.purchasesLogsId;
	}

	public void setPurchasesLogId(int purchasesLogsId) {
		this.purchasesLogsId = purchasesLogsId;
	}

}