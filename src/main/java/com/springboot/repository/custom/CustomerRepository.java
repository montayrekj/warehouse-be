package com.springboot.repository.custom;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springboot.entities.Customer;

@Repository("customerRepository")
public class CustomerRepository {
	public Customer getCustomerByName(EntityManager em, String name) {
		Customer customer = null;

		StringBuilder customerQuery = new StringBuilder("FROM Customer WHERE customerName = :name");
		TypedQuery<Customer> query = em.createQuery(customerQuery.toString(), Customer.class);
		query.setParameter("name", name);
		customer = query.getSingleResult();

		return customer;
	}
	
	public Customer getCustomerById(EntityManager em, int id) {
		Customer customer = null;

		StringBuilder customerQuery = new StringBuilder("FROM Customer WHERE customerId = :id");
		TypedQuery<Customer> query = em.createQuery(customerQuery.toString(), Customer.class);
		query.setParameter("id", id);
		customer = query.getSingleResult();

		return customer;
	}

	public List<Customer> getAllCustomers(EntityManager em) {
		List<Customer> customers = null;

		StringBuilder customerQuery = new StringBuilder("FROM Customer");
		TypedQuery<Customer> query = em.createQuery(customerQuery.toString(), Customer.class);
		customers = query.getResultList();

		return customers;
	}
	
	@Transactional
	public void addCustomer(EntityManager em, Customer customer) {
		em.persist(customer);
	}
	
	@Transactional
	public void updateCustomer(EntityManager em, String customerName, String customerAddress, String customerNumber, Integer customerId, String customerContactPerson, int customerLevel, int id) {
		Customer customerExist = getCustomerById(em, customerId);
		if(customerExist != null) {
			customerExist.setCustomerName(customerName);
			customerExist.setCustomerNumber(customerNumber);
			customerExist.setCustomerAddress(customerAddress);
			customerExist.setCustomerContactPerson(customerContactPerson);
			customerExist.setCustomerLevel(customerLevel);
			customerExist.setModifiedBy(id);
			customerExist.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		}
	}
}
