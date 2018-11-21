package com.springboot.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springboot.entities.Customer;
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
	
	public List<Supplier> getAllSuplier(EntityManager em) {
		List<Supplier> suppliers = null;
		
		StringBuilder supplierQuery = new StringBuilder("FROM Supplier");
		TypedQuery<Supplier> query = em.createQuery(supplierQuery.toString(), Supplier.class);
		suppliers = query.getResultList();

		return suppliers;
	}
	
	@Transactional
	public void addSupplier(EntityManager em, Supplier supplier) {
		em.persist(supplier);
	}
}
