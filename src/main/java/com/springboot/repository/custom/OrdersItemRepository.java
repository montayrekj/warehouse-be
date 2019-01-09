package com.springboot.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springboot.entities.OrdersItem;

@Repository("ordersItemRepository")
public class OrdersItemRepository {
	
	@Transactional
	public void addSalesLogsItem(EntityManager em, OrdersItem sli) throws Exception{
		em.persist(sli);
		em.flush();
	}
	
	public List<OrdersItem> getOrdersItemByOrderId(EntityManager em, String id) throws Exception {
		List<OrdersItem> ordersItem = null;

		StringBuilder ordersItemQuery = new StringBuilder("FROM OrdersItem WHERE orderId = :id");
		TypedQuery<OrdersItem> query = em.createQuery(ordersItemQuery.toString(), OrdersItem.class);
		query.setParameter("id", id);
		ordersItem = query.getResultList();

		return ordersItem;
	}
	
	public OrdersItem getOrdersItemById(EntityManager em, int id) throws Exception {
		OrdersItem ordersItem = null;

		StringBuilder ordersItemQuery = new StringBuilder("FROM OrdersItem WHERE itemId = :id");
		TypedQuery<OrdersItem> query = em.createQuery(ordersItemQuery.toString(), OrdersItem.class);
		query.setParameter("id", id);
		ordersItem = query.getSingleResult();

		return ordersItem;
	}
	
	public Double getQuantityOutByProduct(EntityManager em, int productId) throws Exception {
		Double quantityOut = 0.0;
		
		StringBuilder ordersItemQuery = new StringBuilder("SELECT SUM(o.quantitySold) FROM OrdersItem o WHERE productId = :id");
		Query query = em.createQuery(ordersItemQuery.toString());
		query.setParameter("id", productId);
		Object result = query.getSingleResult();
		
		if(result != null) {
			quantityOut = (Double) result;
		}
		
		return quantityOut;
	}
	
	@Transactional
	public void updateQuantityLeft(OrdersItem orderItem, float quantityLeft) {
		orderItem.setQuantityLeft(quantityLeft);
	}
}
