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
	
	public void addCustomer(String customerName, String customerAddress, String customerContactNo, int userId) throws Exception {
		Customer customer = new Customer();
		customer.setCustomerName(customerName);
		customer.setCustomerAddress(customerAddress);
		customer.setCustomerNumber(customerContactNo);
		customer.setCreatedBy(userId);
		customer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		customer.setModifiedBy(userId);
		customer.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		
		customerRepo.addCustomer(em, customer);
	}
}
