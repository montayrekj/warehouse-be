package com.springboot.model;

import java.sql.Timestamp;
import java.util.List;

import com.springboot.entities.PurchasesLogsItem;

public class PurchasesLogsModel {
	
	private int purchasesLogsId;

	private String createdBy;

	private Timestamp createdDate;
	
	private List<PurchasesLogsItem> purchasesLogsItem;

	public int getPurchasesLogsId() {
		return purchasesLogsId;
	}

	public void setPurchasesLogsId(int purchasesLogsId) {
		this.purchasesLogsId = purchasesLogsId;
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

	public List<PurchasesLogsItem> getPurchasesLogsItem() {
		return purchasesLogsItem;
	}

	public void setPurchasesLogsItem(List<PurchasesLogsItem> purchasesLogsItem) {
		this.purchasesLogsItem = purchasesLogsItem;
	}
}
