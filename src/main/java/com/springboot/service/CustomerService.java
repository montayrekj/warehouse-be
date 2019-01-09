package com.springboot.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Customer;
import com.springboot.repository.custom.CustomerRepository;

@Service("customerService")
public class CustomerService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	public List<Customer> getAllCustomers() throws Exception {
		return customerRepo.getAllCustomers(em);
	}
	
	public void addCustomer(String customerName, String customerAddress, String customerContactNo, String customerContactPerson, int customerLevel, int userId) throws Exception {
		Customer customerExist = null;
		try {
			customerExist = customerRepo.getCustomerByName(em, customerName);
		} catch(Exception e) {
			
		}
		if(customerExist == null) { 
			Customer customer = new Customer();
			customer.setCustomerName(customerName);
			customer.setCustomerAddress(customerAddress);
			customer.setCustomerNumber(customerContactNo);
			customer.setCustomerContactPerson(customerContactPerson);
			customer.setCustomerLevel(customerLevel);
			customer.setCreatedBy(userId);
			customer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			customer.setModifiedBy(userId);
			customer.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			
			customerRepo.addCustomer(em, customer);
		} else {
			throw new Exception("Customer name exists!");
		}
	}
	
	public Customer getCustomerById(int customerId) {
		return customerRepo.getCustomerById(em, customerId);
	}
	
	public void updateCustomer(String customerName, String customerAddress, String customerNumber, Integer customerId, String customerContactPerson, int customerLevel, int id) throws Exception {
		Customer customerExist = null;
		try {
			customerExist = customerRepo.getCustomerByName(em, customerName);
		} catch(Exception e) {
			
		}
		
		if(customerExist == null || (customerExist != null && customerExist.getCustomerId() == customerId))
			customerRepo.updateCustomer(em, customerName, customerAddress, customerNumber, customerId, customerContactPerson, customerLevel, id);
		else {
			throw new Exception("Customer name exists!");
		}
	}
}
