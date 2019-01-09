package com.springboot.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Customer;
import com.springboot.entities.Order;
import com.springboot.entities.Sale;
import com.springboot.entities.User;
import com.springboot.model.SaleModel;
import com.springboot.repository.custom.CustomerRepository;
import com.springboot.repository.custom.OrdersRepository;
import com.springboot.repository.custom.SaleRepository;
import com.springboot.repository.custom.UserRepository;

@Service("salesService")
public class SalesService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private SaleRepository saleRepo;
	
	@Autowired
	private OrdersRepository orderRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	public List<SaleModel> getSales() throws Exception {
		List<Sale> sales = saleRepo.getSales(em);
		List<SaleModel> salesModel = new ArrayList<SaleModel>();
		if(sales != null) {
			for(Sale sale: sales) {
				SaleModel saleModel = new SaleModel();
				saleModel.setAmount(sale.getAmount());
				User user = userRepo.getUserById(em, sale.getCreatedBy());
				saleModel.setCreatedBy(user.getUsername());
				saleModel.setCreatedDate(sale.getCreatedDate());
				Order order = orderRepo.getOrdersById(em, sale.getOrderId());
				Customer customer = customerRepo.getCustomerById(em, order.getCustomer());
				saleModel.setCustomer(customer.getCustomerName());
				saleModel.setOrderId(sale.getOrderId());
				saleModel.setSalesId(sale.getSalesId());
				saleModel.setSalesType(sale.getSalesType());
				
				salesModel.add(saleModel);
			}
		}
		
		return salesModel;
	}
	
	public List<SaleModel> getSales(Map<String, String> searchMap) throws Exception {
		List<Sale> sales = saleRepo.getSales(em, searchMap);
		List<SaleModel> salesModel = new ArrayList<SaleModel>();
		if(sales != null) {
			for(Sale sale: sales) {
				SaleModel saleModel = new SaleModel();
				saleModel.setAmount(sale.getAmount());
				User user = userRepo.getUserById(em, sale.getCreatedBy());
				saleModel.setCreatedBy(user.getUsername());
				saleModel.setCreatedDate(sale.getCreatedDate());
				Order order = orderRepo.getOrdersById(em, sale.getOrderId());
				Customer customer = customerRepo.getCustomerById(em, order.getCustomer());
				saleModel.setCustomer(customer.getCustomerName());
				saleModel.setOrderId(sale.getOrderId());
				saleModel.setSalesId(sale.getSalesId());
				saleModel.setSalesType(sale.getSalesType());
				
				salesModel.add(saleModel);
			}
		}
		
		return salesModel;
	}
	
	public Double getDailySales() throws Exception {
		Double salesAmount = 0.0;
		Map<String, String> searchMap = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		searchMap.put("paidAmount", "");
		searchMap.put("customerName", "");
		searchMap.put("searchType", "");
		searchMap.put("paidDateFrom", format.format(new Date()).toString());
		searchMap.put("paidDateTo", format.format(new Date()).toString());
		searchMap.put("paidTo", "");
		
		List<Sale> sales = saleRepo.getSales(em, searchMap);
		if(sales != null) {
			for(Sale sale: sales) {
				salesAmount += sale.getAmount();
			}
		}
		
		return salesAmount;
	}
	
	@SuppressWarnings("deprecation")
	public List<Double> getDailySalesChart() throws Exception {
		List<Double> salesList = new ArrayList<Double>();
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
		int today = cal.get(Calendar.DAY_OF_WEEK);
		if(today != cal.getFirstDayOfWeek())
			cal.add( Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK)-1));
		Date dateStart = new Date((cal.get(Calendar.YEAR) - 1900), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		cal.setTime(dateStart);
		for(int i = 0; i <= 6; i++) {
			Map<String, String> searchMap = new HashMap<String, String>();
			Double salesAmount = 0.0;
	        cal.add(Calendar.HOUR, 23);
	        cal.add(Calendar.MINUTE, 59);
	        cal.add(Calendar.SECOND, 59);
	
	        Date dateEnd = cal.getTime();
	        
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			searchMap.put("paidAmount", "");
			searchMap.put("customerName", "");
			searchMap.put("searchType", "");
			searchMap.put("paidDateFrom", format.format(dateStart).toString());
			searchMap.put("paidDateTo", format.format(dateEnd).toString());
			searchMap.put("paidTo", "");
			
			List<Sale> sales = saleRepo.getSales(em, searchMap);
			if(sales != null) {
				for(Sale sale: sales) {
					salesAmount += sale.getAmount();
				}
			}
			
	        cal.add(Calendar.SECOND, 1);
	        dateStart = cal.getTime();
			
			salesList.add(salesAmount);
		}
		return salesList;
	}
	
	@SuppressWarnings("deprecation")
	public Double getWeeklySales() throws Exception {
		Double salesAmount = 0.0;
		Map<String, String> searchMap = new HashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
		int today = cal.get(Calendar.DAY_OF_WEEK);
		if(today != cal.getFirstDayOfWeek())
			cal.add( Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK)-1));
		Date dateStart = new Date((cal.get(Calendar.YEAR) - 1900), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		cal.setTime(dateStart);

        cal.add(Calendar.DATE, 6); 
        cal.add(Calendar.HOUR, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);

        Date dateEnd = cal.getTime();
        
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		searchMap.put("paidAmount", "");
		searchMap.put("customerName", "");
		searchMap.put("searchType", "");
		searchMap.put("paidDateFrom", format.format(dateStart).toString());
		searchMap.put("paidDateTo", format.format(dateEnd).toString());
		searchMap.put("paidTo", "");
		
		List<Sale> sales = saleRepo.getSales(em, searchMap);
		if(sales != null) {
			for(Sale sale: sales) {
				salesAmount += sale.getAmount();
			}
		}
		
		return salesAmount;
	}
	
	@SuppressWarnings("deprecation")
	public List<Double> getMonthlySalesChart() throws Exception {
		List<Double> salesList = new ArrayList<Double>();
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));

		for(int i = 0; i < 12; i++) {
			Double salesAmount = 0.0;
			Map<String, String> searchMap = new HashMap<String, String>();
			cal.set(2018, i, 1);
			Date dateStart = new Date((cal.get(Calendar.YEAR) - 1900), cal.get(Calendar.MONTH), 1);
			int lastDate = cal.getActualMaximum(Calendar.DATE);
		    
	        cal.set(Calendar.DATE, lastDate);
	        
	        Date tempDate = cal.getTime();
	        Date dateEnd = new Date(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate(), 23, 59, 59);
	        
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			searchMap.put("paidAmount", "");
			searchMap.put("customerName", "");
			searchMap.put("searchType", "");
			searchMap.put("paidDateFrom", format.format(dateStart).toString());
			searchMap.put("paidDateTo", format.format(dateEnd).toString());
			searchMap.put("paidTo", "");
			
			List<Sale> sales = saleRepo.getSales(em, searchMap);
			if(sales != null) {
				for(Sale sale: sales) {
					salesAmount += sale.getAmount();
				}
			}
			
			salesList.add(salesAmount);
		}
		
		return salesList;
	}
	
	@SuppressWarnings("deprecation")
	public Double getMonthlySales() throws Exception {
		Double salesAmount = 0.0;
		Map<String, String> searchMap = new HashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));

		Date dateStart = new Date((cal.get(Calendar.YEAR) - 1900), cal.get(Calendar.MONTH), 1);
		int lastDate = cal.getActualMaximum(Calendar.DATE);
	    
        cal.set(Calendar.DATE, lastDate);
        
        Date tempDate = cal.getTime();
        Date dateEnd = new Date(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate(), 23, 59, 59);
        
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		searchMap.put("paidAmount", "");
		searchMap.put("customerName", "");
		searchMap.put("searchType", "");
		searchMap.put("paidDateFrom", format.format(dateStart).toString());
		searchMap.put("paidDateTo", format.format(dateEnd).toString());
		searchMap.put("paidTo", "");
		
		List<Sale> sales = saleRepo.getSales(em, searchMap);
		if(sales != null) {
			for(Sale sale: sales) {
				salesAmount += sale.getAmount();
			}
		}
		
		return salesAmount;
	}
}
