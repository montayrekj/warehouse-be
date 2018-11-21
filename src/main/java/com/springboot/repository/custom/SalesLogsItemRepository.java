package com.springboot.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springboot.entities.SalesLogsItem;

@Repository("salesLogsItemRepository")
public class SalesLogsItemRepository {
	
	@Transactional
	public void addSalesLogsItem(EntityManager em, SalesLogsItem sli) throws Exception{
		em.persist(sli);
		em.flush();
	}
	
	public List<SalesLogsItem> getSalesLogsItemByPurchasesLogsId(EntityManager em, int id) throws Exception {
		List<SalesLogsItem> salesLogsItem = null;

		StringBuilder salesLogsItemQuery = new StringBuilder("FROM SalesLogsItem WHERE salesLogsId = :id");
		TypedQuery<SalesLogsItem> query = em.createQuery(salesLogsItemQuery.toString(), SalesLogsItem.class);
		query.setParameter("id", id);
		salesLogsItem = query.getResultList();

		return salesLogsItem;
	}
	
	public SalesLogsItem getSalesLogsItemById(EntityManager em, int id) throws Exception {
		SalesLogsItem salesLogsItem = null;

		StringBuilder salesLogsItemQuery = new StringBuilder("FROM SalesLogsItem WHERE itemId = :id");
		TypedQuery<SalesLogsItem> query = em.createQuery(salesLogsItemQuery.toString(), SalesLogsItem.class);
		query.setParameter("id", id);
		salesLogsItem = query.getSingleResult();

		return salesLogsItem;
	}
	
	@Transactional
	public void updateQuantityLeft(SalesLogsItem sls, float quantityLeft) {
		sls.setQuantityLeft(quantityLeft);
	}
}
