package com.springboot.repository.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Customer;
import com.springboot.entities.Order;
import com.springboot.entities.Sale;
import com.springboot.entities.User;
import com.springboot.util.TimestampParser;

@Repository("saleRepository")
public class SaleRepository {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	OrdersRepository orderRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Transactional
	public void addSales(EntityManager em, Sale sale) {
		em.persist(sale);
	}
	
	public List<Sale> getSales(EntityManager em) {
		List<Sale> sales = null;

		StringBuilder salesQuery = new StringBuilder("FROM Sale");
		TypedQuery<Sale> query = em.createQuery(salesQuery.toString(), Sale.class);
		sales = query.getResultList();

		return sales;
	}
	
	public List<Sale> getSales(EntityManager em, Map<String, String> searchMap) throws Exception {
		List<Sale> sales = new ArrayList<Sale>();
		List<Sale> temp = null;

		StringBuilder salesQuery = new StringBuilder("FROM Sale WHERE (");
		salesQuery.append("(salesType LIKE '%" + searchMap.get("searchType") + "%')");
		if(!searchMap.get("paidAmount").equals(""))
			salesQuery.append(" AND (amount = " + searchMap.get("paidAmount") + ")");
		
		if(!searchMap.get("paidDateFrom").equals("") && !searchMap.get("paidDateTo").equals(""))
			salesQuery.append(" AND (createdDate >= '" + TimestampParser.TimestampStart(searchMap.get("paidDateFrom"))
				+ "' AND " + "createdDate <= '" + TimestampParser.TimestampEnd(searchMap.get("paidDateTo")) + "')");
		
		/*if(!searchMap.get("paidTo").equals("")) {
			User user = userRepo.searchUserByUsername(em, searchMap.get("paidTo"));
			salesQuery.append(" AND (createdBy = " + user.getUserId() + ")");
		}*/
		salesQuery.append(")");
		TypedQuery<Sale> query = em.createQuery(salesQuery.toString(), Sale.class);
		temp = query.getResultList();
		
		if(!searchMap.get("customerName").equals("")) {
			for(Sale sale : temp) {
				Order order = orderRepo.getOrdersById(em, sale.getOrderId());
				Customer customer = customerRepo.getCustomerById(em, order.getCustomer());
				if(customer.getCustomerName().toUpperCase().equals(searchMap.get("customerName").toUpperCase())) {
					sales.add(sale);
				}
			}
		} else {
			sales = temp;
		}
		
		temp = new ArrayList<Sale>();
		
		if(sales.size() != 0 && !searchMap.get("paidTo").equals("")) {
			try {
				User user = userRepo.searchUserByUsername(em, searchMap.get("paidTo"));
				if(user != null) {
					for(int i = 0; i < sales.size(); i++) {
						if(sales.get(i).getCreatedBy() == user.getUserId())
							temp.add(sales.get(i));
					}
				}
			} catch(Exception e) {
				
			}
			sales = temp;
		}

		return sales;
	}
}
