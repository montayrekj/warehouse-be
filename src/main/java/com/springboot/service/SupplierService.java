package com.springboot.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		Supplier supplierExist = null;
		try {
			supplierExist = supplierRepo.getSupplierByName(em, supplierName);
		} catch(Exception e) {
			
		}
		if(supplierExist == null) {
			Supplier supplier = new Supplier();
			supplier.setSupplierName(supplierName);
			supplier.setSupplierAddress(supplierAddress);
			supplier.setSupplierNumber(supplierContactNo);
			supplier.setCreatedBy(userId);
			supplier.setDateCreated(new Timestamp(System.currentTimeMillis()));
			supplier.setModifiedBy(userId);
			
			supplierRepo.addSupplier(em, supplier);
		} else {
			throw new Exception("Supplier name exists!");
		}
	}
	
	public Supplier getSupplierById(int customerId) {
		return supplierRepo.getSupplierById(em, customerId);
	}
	
	public void updateSupplier(String supplierName, String supplierAddress, String supplierNumber, Integer supplierId, int id) throws Exception{
		Supplier supplierExist = null;
		try {
			supplierExist = supplierRepo.getSupplierByName(em, supplierName);
		} catch(Exception e) {
			
		}
		
		if(supplierExist == null || (supplierExist != null && supplierId == supplierExist.getSupplierId())) {
			supplierRepo.updateSupplier(em, supplierName, supplierAddress, supplierNumber, supplierId, id);
		} else {
			throw new Exception("Supplier name exists!");
		}
	}
}
