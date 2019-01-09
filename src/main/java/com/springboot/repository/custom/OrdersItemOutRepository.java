package com.springboot.repository.custom;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springboot.entities.OrdersItemOut;

@Repository("ordersItemOutRepository")
public class OrdersItemOutRepository {
	
	@Transactional
	public void addOrdersItemOut(EntityManager em, OrdersItemOut oio) {
		em.persist(oio);
		em.flush();
	}
}
