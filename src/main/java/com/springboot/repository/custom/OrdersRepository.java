package com.springboot.repository.custom;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Customer;
import com.springboot.entities.Order;
import com.springboot.entities.User;
import com.springboot.model.ProductModel;
import com.springboot.util.Common;
import com.springboot.util.Constants;
import com.springboot.util.TimestampParser;

@Repository("ordersRepository")
public class OrdersRepository {
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@SuppressWarnings("deprecation")
	@Transactional
	public Order addOrders(EntityManager em, int userId, String customerName, List<ProductModel> products) throws Exception{
		Order order = new Order();
		order.setCreatedBy(userId);
		order.setModifiedBy(userId);
		order.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		order.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		Customer customer = customerRepo.getCustomerByName(em, customerName);
		order.setCustomer(customer.getCustomerId());
		Date today = new Date(System.currentTimeMillis());
		int month = today.getMonth() + 1;
		int day = today.getDate();
		int year = today.getYear() + 1900;
		String id = "PO" + month + String.format("%02d", day) + String.valueOf(year).substring(2, 4);
		
		int length = getOrdersToday(em, month + "/" + day + "/" + year).size();
		id += Common.orderIdParser(length);
		order.setOrderId(id);
		
		double total = 0.0;
		for(ProductModel product : products) {
			total += product.getSellPrice() * product.getQuantity();
		}
		
		order.setTotalAmount(total);
		
		
		em.persist(order);
		em.flush();
		
		return order;
	}
	
	public List<Order> getOrders(EntityManager em) throws Exception {
		List<Order> orders = null;

		StringBuilder ordersQuery = new StringBuilder("FROM Order");
		TypedQuery<Order> query = em.createQuery(ordersQuery.toString(), Order.class);
		orders = query.getResultList();

		return orders;
	}
	
	public List<Order> getOrdersToday(EntityManager em, String date) throws Exception {
		List<Order> orders = null;

		StringBuilder ordersQuery = new StringBuilder("FROM Order WHERE ");
		ordersQuery.append("(createdDate >= '" + TimestampParser.TimestampStart(date)
		+ "' AND " + "createdDate <= '" + TimestampParser.TimestampEnd(date) + "')");

		TypedQuery<Order> query = em.createQuery(ordersQuery.toString(), Order.class);
		orders = query.getResultList();

		return orders;
	}
	
	public List<Order> getActiveOrders(EntityManager em, boolean active) throws Exception {
		List<Order> orders = null;

		StringBuilder ordersQuery = new StringBuilder("FROM Order WHERE ");
		if(active) {
			ordersQuery.append("purchaseOrderStatus = 3 OR purchaseOrderStatus < 0" );
		} else {
			ordersQuery.append("purchaseOrderStatus != 3");
		}
		TypedQuery<Order> query = em.createQuery(ordersQuery.toString(), Order.class);
		orders = query.getResultList();

		return orders;
	}
	
	public List<Order> getActiveOrders(EntityManager em, boolean active, Map<String, String> searchMap) throws Exception {
		List<Order> orders = null;

		StringBuilder ordersQuery = new StringBuilder("FROM Order WHERE ");
		if(active) {
			ordersQuery.append("((purchaseOrderStatus = 3) ");
		} else {
			ordersQuery.append("((purchaseOrderStatus != 3) ");
		}
		
		//ordersQuery.append("(customer = '" + searchMap.get("customerName") + "')");
		if(!searchMap.get("orderId").equals(""))
			ordersQuery.append("AND (orderId = " + searchMap.get("orderId") + ")");
		
		if(!searchMap.get("paymentStatus").equals("")) {
			switch(searchMap.get("paymentStatus")) {
				case "Unpaid": ordersQuery.append(" AND (paidAmount = " + 0 + ")");
					break;
				case "Partial": ordersQuery.append(" AND (paidAmount != " + 0 + " AND paidAmount != totalAmount)");
					break;
				case "Full": ordersQuery.append(" AND (paidAmount != " + 0 + " AND paidAmount = totalAmount)");
					break;
			}
		}
		
		if(!searchMap.get("totalAmount").equals(""))
			ordersQuery.append(" AND (totalAmount = " + searchMap.get("totalAmount") + ")");
		
		if(!searchMap.get("orderedDateFrom").equals("") && !searchMap.get("orderedDateTo").equals(""))
			ordersQuery.append(" AND (createdDate >= '" + TimestampParser.TimestampStart(searchMap.get("orderedDateFrom"))
				+ "' AND " + "createdDate <= '" + TimestampParser.TimestampEnd(searchMap.get("orderedDateTo")) + "')");
		
		/*if(!searchMap.get("orderedFrom").equals("")) {
			User user = userRepo.searchUserByUsername(em, searchMap.get("orderedFrom"));
			ordersQuery.append(" AND (createdBy = " + user.getUserId() + ")");
		}*/
		
		ordersQuery.append(")");
		TypedQuery<Order> query = em.createQuery(ordersQuery.toString(), Order.class);
		orders = query.getResultList();
		
		List<Order> temp = new ArrayList<Order>();
		
		if(orders.size() != 0 && !searchMap.get("orderedFrom").equals("")) {
			try {
				User user = userRepo.searchUserByUsername(em, searchMap.get("orderedFrom"));
				if(user != null) {
					for(int i = 0; i < orders.size(); i++) {
						if(orders.get(i).getCreatedBy() == user.getUserId())
							temp.add(orders.get(i));
					}
				}
			} catch(Exception e) {
				
			}
			orders = temp;
		}
		
		temp = new ArrayList<Order>();
		
		if(orders.size() != 0 && !searchMap.get("customerName").equals("")) {
			try {
				Customer customer = customerRepo.getCustomerByName(em, searchMap.get("customerName"));
				if(customer != null) {
					for(int i = 0; i < orders.size(); i++) {
						if(orders.get(i).getCustomer() == customer.getCustomerId()) {
							temp.add(orders.get(i));
						}
					}
				}
			} catch(Exception e) {
				
			}
			orders = temp;
		}
		
		temp = new ArrayList<Order>();
		
		if(orders.size() != 0 && !searchMap.get("customerLevel").equals("")) {
			try {
				for(int i = 0; i < orders.size(); i++) {
					Customer customer = customerRepo.getCustomerById(em, orders.get(i).getCustomer());
					if(String.valueOf(customer.getCustomerLevel()).equals(searchMap.get("customerLevel"))) {
						temp.add(orders.get(i));
					}
				}
			} catch(Exception e) {
				
			}
			orders = temp;
		}

		return orders;
	}
	
	public List<Order> getCollections(EntityManager em) throws Exception {
		List<Order> orders = null;

		StringBuilder ordersQuery = new StringBuilder("FROM Order WHERE collected = 0 AND termAmount > 0");
		TypedQuery<Order> query = em.createQuery(ordersQuery.toString(), Order.class);
		orders = query.getResultList();

		return orders;
	}
	
	public List<Order> getCollections(EntityManager em, Map<String, String> searchMap) throws Exception {
		List<Order> orders = null;

		StringBuilder ordersQuery = new StringBuilder("FROM Order WHERE (termAmount > 0) AND ((collected = 0)");
		if(!searchMap.get("orderId").equals(""))
			ordersQuery.append("AND (orderId = " + searchMap.get("orderId") + ")");
		
		if(!searchMap.get("termAmount").equals(""))
			ordersQuery.append(" AND (termAmount = " + searchMap.get("termAmount") + ")");
		
		if(!searchMap.get("termDueDateFrom").equals("") && !searchMap.get("termDueDateTo").equals(""))
			ordersQuery.append(" AND (termDueDate >= '" + TimestampParser.TimestampStart(searchMap.get("termDueDateFrom"))
				+ "' AND " + "termDueDate <= '" + TimestampParser.TimestampEnd(searchMap.get("termDueDateTo")) + "')");
		if(!searchMap.get("orderedDateFrom").equals("") && !searchMap.get("orderedDateTo").equals(""))
			ordersQuery.append(" AND (createdDate >= '" + TimestampParser.TimestampStart(searchMap.get("orderedDateFrom"))
				+ "' AND " + "createdDate <= '" + TimestampParser.TimestampEnd(searchMap.get("orderedDateTo")) + "')");
		
		/*if(!searchMap.get("orderedFrom").equals("")) {
			User user = userRepo.searchUserByUsername(em, searchMap.get("orderedFrom"));
			ordersQuery.append(" AND (createdBy = " + user.getUserId() + ")");
		}*/
		ordersQuery.append(")");
		
		TypedQuery<Order> query = em.createQuery(ordersQuery.toString(), Order.class);
		orders = query.getResultList();
		
		List<Order> temp = new ArrayList<Order>();
		
		if(orders.size() != 0 && !searchMap.get("orderedFrom").equals("")) {
			try {
				User user = userRepo.searchUserByUsername(em, searchMap.get("orderedFrom"));
				if(user != null) {
					for(int i = 0; i < orders.size(); i++) {
						if(orders.get(i).getCreatedBy() == user.getUserId())
							temp.add(orders.get(i));
					}
				}
			} catch(Exception e) {
				
			}
			orders = temp;
		}
		
		if(orders.size() != 0 && !searchMap.get("customerName").equals("")) {
			try {
				Customer customer = customerRepo.getCustomerByName(em, searchMap.get("customerName"));
				if(customer != null) {
					for(int i = 0; i < orders.size(); i++) {
						if(orders.get(i).getCustomer() == customer.getCustomerId())
							temp.add(orders.get(i));
					}
				}
			} catch(Exception e) {
				
			}
			orders = temp;
		}

		return orders;
	}
	
	public Order getOrdersById(EntityManager em, String orderId) throws Exception {
		Order orders = null;

		StringBuilder ordersQuery = new StringBuilder("FROM Order WHERE orderId = :id");
		TypedQuery<Order> query = em.createQuery(ordersQuery.toString(), Order.class);
		query.setParameter("id", orderId);
		orders = query.getSingleResult();

		return orders;
	}
	
	@Transactional
	public void regionalManagerApproved(EntityManager em, Order order, Boolean approved) {
		int status = approved? Constants.PO_STATUS_RM_APPROVED : Constants.PO_STATUS_RM_DECLINED;
		order.setPurchaseOrderStatus(status);
	}
	
	@Transactional
	public void accountingApproved(EntityManager em, Order order, boolean approved, double cashAmount, 
			double termAmount, String termDueDate) throws ParseException {
		int status;
		if(approved) {
			status = Constants.PO_STATUS_ACCT_APPROVED;
			order.setCashAmount(cashAmount);
			order.setTermAmount(termAmount);
			order.setPaidAmount(cashAmount);
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = dateFormat.parse(termDueDate);
			order.setTermDueDate(date);
		} else {
			status = Constants.PO_STATUS_ACCT_DECLINED;
			order.setCashAmount(0);
			order.setTermAmount(0);
			order.setTermDueDate(null);
		}
		order.setPurchaseOrderStatus(status);
	}
	
	@Transactional
	public void completeOrder(EntityManager em, Order order) {
		order.setPurchaseOrderStatus(Constants.PO_STATUS_DONE);
	}
	
	@Transactional
	public void collect(EntityManager em, Order order) {
		order.setPaidAmount(order.getPaidAmount() + order.getTermAmount());
		order.setCollected(true);
	}
}
