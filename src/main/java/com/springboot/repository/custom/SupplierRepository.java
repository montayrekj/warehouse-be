package com.springboot.repository.custom;

import java.sql.Timestamp;
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
	
	public Supplier getSupplierById(EntityManager em, int id) {
		Supplier supplier = null;
		
		StringBuilder supplierQuery = new StringBuilder("FROM Supplier WHERE supplierId = :id");
		TypedQuery<Supplier> query = em.createQuery(supplierQuery.toString(), Supplier.class);
		query.setParameter("id", id);
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
	
	@Transactional
	public void addCustomer(EntityManager em, Customer customer) {
		em.persist(customer);
	}
	
	@Transactional
	public void updateSupplier(EntityManager em, String supplierName, String supplierAddress, String supplierNumber, Integer supplierId, int id) {
		Supplier supplierExist = getSupplierById(em, supplierId);
		if(supplierExist != null) {
			supplierExist.setSupplierName(supplierName);
			supplierExist.setSupplierNumber(supplierNumber);
			supplierExist.setSupplierAddress(supplierAddress);
			supplierExist.setModifiedBy(id);
			supplierExist.setDateModified(new Timestamp(System.currentTimeMillis()));
		}
	}
}
