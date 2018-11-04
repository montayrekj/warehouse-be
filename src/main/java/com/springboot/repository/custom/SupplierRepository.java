package com.springboot.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.springboot.entities.Supplier;

@Repository("supplierRepository")
public class SupplierRepository {
	public Supplier getSupplierByName(EntityManager em, String name) {
		Supplier supplier = null;
		
		StringBuilder supplierQuery = new StringBuilder("FROM Supplier WHERE supplier_name = :name");
		TypedQuery<Supplier> query = em.createQuery(supplierQuery.toString(), Supplier.class);
		query.setParameter("name", name);
		supplier = query.getSingleResult();

		return supplier;
	}
}
