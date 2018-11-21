package com.springboot.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Customer;
import com.springboot.entities.Supplier;
import com.springboot.repository.custom.SupplierRepository;

@Service("supplierService")
public class SupplierService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	public List<Supplier> getAllSuplier() throws Exception {
		return supplierRepo.getAllSuplier(em);
	}
	
	public void addSupplier(String supplierName, String supplierAddress, String supplierContactNo, int userId) throws Exception {
		Supplier supplier = new Supplier();
		supplier.setSupplierName(supplierName);
		supplier.setSupplierAddress(supplierAddress);
		supplier.setSupplierNumber(supplierContactNo);
		supplier.setCreatedBy(userId);
		supplier.setDateCreated(new Timestamp(System.currentTimeMillis()));
		supplier.setModifiedBy(userId);

		
		supplierRepo.addSupplier(em, supplier);
	}
}
