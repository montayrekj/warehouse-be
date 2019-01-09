package com.springboot.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sales_logs_item database table.
 * 
 */
@Entity
@Table(name="orders_item")
@NamedQuery(name="OrdersItem.findAll", query="SELECT o FROM OrdersItem o")
public class OrdersItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int itemId;

	@Column(name="buy_price")
	private double buyPrice;

	private String supplier;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;

	@Column(name="quantity_sold")
	private float quantitySold;
	
	@Column(name="quantity_left")
	private float quantityLeft;

	@Column(name="sell_price")
	private double sellPrice;

	private String unit;

	@Column(name="order_id")
	private String orderId;
	
	@Column(name="product_id")
	private int productId;

	public OrdersItem() {
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

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	public float getQuantitySold() {
		return this.quantitySold;
	}

	public void setQuantitySold(float quantitySold) {
		this.quantitySold = quantitySold;
	}

	public double getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public float getQuantityLeft() {
		return quantityLeft;
	}

	public void setQuantityLeft(float quantityLeft) {
		this.quantityLeft = quantityLeft;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

}