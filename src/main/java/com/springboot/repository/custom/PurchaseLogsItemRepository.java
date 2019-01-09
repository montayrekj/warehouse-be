package com.springboot.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springboot.entities.PurchasesLogsItem;

@Repository("purchaseLogsItemRepository")
public class PurchaseLogsItemRepository {
	
	@Transactional
	public void addPurchaseLogsItem(EntityManager em, PurchasesLogsItem pli) throws Exception{
		em.persist(pli);
		em.flush();
	}
	
	public List<PurchasesLogsItem> getPurchaseLogsItemByPurchasesLogsId(EntityManager em, int id) throws Exception {
		List<PurchasesLogsItem> purchasesLogsItem = null;

		StringBuilder purchasesLogsItemQuery = new StringBuilder("FROM PurchasesLogsItem WHERE purchasesLogsId = :id");
		TypedQuery<PurchasesLogsItem> query = em.createQuery(purchasesLogsItemQuery.toString(), PurchasesLogsItem.class);
		query.setParameter("id", id);
		purchasesLogsItem = query.getResultList();

		return purchasesLogsItem;
	}
	
	public Double getQuantityInByProduct(EntityManager em, int productId) throws Exception {
		Double quantityIn = 0.0;
		
		StringBuilder purchaseLogsItemQuery = new StringBuilder("SELECT SUM(p.quantityAdded) FROM PurchasesLogsItem p WHERE productId = :id");
		Query query = em.createQuery(purchaseLogsItemQuery.toString());
		query.setParameter("id", productId);
		Object result = query.getSingleResult();
		
		if(result != null) {
			quantityIn = (Double) result;
		}
		
		return quantityIn;
	}
}
