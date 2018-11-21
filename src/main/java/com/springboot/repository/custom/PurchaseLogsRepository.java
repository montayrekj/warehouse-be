package com.springboot.repository.custom;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springboot.entities.Product;
import com.springboot.entities.PurchasesLog;

@Repository("purchaseLogsRepository")
public class PurchaseLogsRepository {
	
	@Transactional
	public PurchasesLog addPurchaseLogs(EntityManager em, int userId) throws Exception{
		PurchasesLog purchaseLog = new PurchasesLog();
		purchaseLog.setCreatedBy(userId);
		purchaseLog.setModifiedBy(userId);
		purchaseLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		purchaseLog.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		
		em.persist(purchaseLog);
		em.flush();
		
		return purchaseLog;
	}
	
	public List<PurchasesLog> getPurchaseLogs(EntityManager em) throws Exception {
		List<PurchasesLog> purchasesLog = null;

		StringBuilder purchasesLogsQuery = new StringBuilder("FROM PurchasesLog");
		TypedQuery<PurchasesLog> query = em.createQuery(purchasesLogsQuery.toString(), PurchasesLog.class);
		purchasesLog = query.getResultList();

		return purchasesLog;
	}
	
	public PurchasesLog getPurchaseLogsById(EntityManager em, int purchasesLogsId) throws Exception {
		PurchasesLog purchasesLog = null;

		StringBuilder purchasesLogsQuery = new StringBuilder("FROM PurchasesLog WHERE purchasesLogsId = :id");
		TypedQuery<PurchasesLog> query = em.createQuery(purchasesLogsQuery.toString(), PurchasesLog.class);
		query.setParameter("id", purchasesLogsId);
		purchasesLog = query.getSingleResult();

		return purchasesLog;
	}
}
