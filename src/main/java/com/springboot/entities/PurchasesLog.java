package com.springboot.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the purchases_logs database table.
 * 
 */
@Entity
@Table(name="purchases_logs")
@NamedQuery(name="PurchasesLog.findAll", query="SELECT p FROM PurchasesLog p")
public class PurchasesLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="purchases_logs_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int purchasesLogsId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="modified_by")
	private int modifiedBy;

	@Column(name="modified_date")
	private Timestamp modifiedDate;

	public PurchasesLog() {
	}

	public int getPurchasesLogsId() {
		return this.purchasesLogsId;
	}

	public void setPurchasesLogsId(int purchasesLogsId) {
		this.purchasesLogsId = purchasesLogsId;
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

}