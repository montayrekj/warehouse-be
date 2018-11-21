package com.springboot.repository.custom;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.entities.SalesLog;
import com.springboot.model.ProductModel;
import com.springboot.util.Constants;

@Repository("salesLogsRepository")
public class SalesLogsRepository {
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Transactional
	public SalesLog addSalesLogs(EntityManager em, int userId, String customerName, List<ProductModel> products) throws Exception{
		SalesLog salesLog = new SalesLog();
		salesLog.setCreatedBy(userId);
		salesLog.setModifiedBy(userId);
		salesLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		salesLog.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		salesLog.setCustomer(customerName);
		
		double total = 0.0;
		for(ProductModel product : products) {
			total += product.getSellPrice() * product.getQuantity();
		}
		
		salesLog.setTotalAmount(total);
		
		
		em.persist(salesLog);
		em.flush();
		
		return salesLog;
	}
	
	public List<SalesLog> getSalesLogs(EntityManager em) throws Exception {
		List<SalesLog> salesLog = null;

		StringBuilder salesLogsQuery = new StringBuilder("FROM SalesLog");
		TypedQuery<SalesLog> query = em.createQuery(salesLogsQuery.toString(), SalesLog.class);
		salesLog = query.getResultList();

		return salesLog;
	}
	
	public List<SalesLog> getCollections(EntityManager em) throws Exception {
		List<SalesLog> salesLog = null;

		StringBuilder salesLogsQuery = new StringBuilder("FROM SalesLog WHERE collected = 0 AND termAmount > 0");
		TypedQuery<SalesLog> query = em.createQuery(salesLogsQuery.toString(), SalesLog.class);
		salesLog = query.getResultList();

		return salesLog;
	}
	
	public SalesLog getSalesLogsById(EntityManager em, int salesLogsId) throws Exception {
		SalesLog salesLog = null;

		StringBuilder salesLogsQuery = new StringBuilder("FROM SalesLog WHERE salesLogsId = :id");
		TypedQuery<SalesLog> query = em.createQuery(salesLogsQuery.toString(), SalesLog.class);
		query.setParameter("id", salesLogsId);
		salesLog = query.getSingleResult();

		return salesLog;
	}
	
	@Transactional
	public void regionalManagerApproved(EntityManager em, SalesLog salesLog, Boolean approved) {
		int status = approved? Constants.PO_STATUS_RM_APPROVED : Constants.PO_STATUS_RM_DECLINED;
		salesLog.setPurchaseOrderStatus(status);
	}
	
	@Transactional
	public void accountingApproved(EntityManager em, SalesLog salesLog, boolean approved, double cashAmount, 
			double termAmount, String termDueDate) throws ParseException {
		int status;
		if(approved) {
			status = Constants.PO_STATUS_ACCT_APPROVED;
			salesLog.setCashAmount(cashAmount);
			salesLog.setTermAmount(termAmount);
			salesLog.setPaidAmount(cashAmount);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = dateFormat.parse(termDueDate);
			salesLog.setTermDueDate(date);
		} else {
			status = Constants.PO_STATUS_ACCT_DECLINED;
			salesLog.setCashAmount(0);
			salesLog.setTermAmount(0);
			salesLog.setTermDueDate(null);
		}
		salesLog.setPurchaseOrderStatus(status);
	}
	
	@Transactional
	public void completeOrder(EntityManager em, SalesLog salesLog) {
		salesLog.setPurchaseOrderStatus(Constants.PO_STATUS_DONE);
	}
	
	@Transactional
	public void collect(EntityManager em, SalesLog salesLog) {
		salesLog.setPaidAmount(salesLog.getPaidAmount() + salesLog.getTermAmount());
		salesLog.setCollected(true);
	}
}
