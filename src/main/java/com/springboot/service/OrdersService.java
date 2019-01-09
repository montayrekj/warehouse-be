package com.springboot.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Customer;
import com.springboot.entities.Order;
import com.springboot.entities.OrdersItem;
import com.springboot.entities.OrdersItemOut;
import com.springboot.entities.Product;
import com.springboot.entities.Sale;
import com.springboot.entities.User;
import com.springboot.model.OrderModel;
import com.springboot.model.ProductModel;
import com.springboot.repository.custom.CustomerRepository;
import com.springboot.repository.custom.OrdersItemOutRepository;
import com.springboot.repository.custom.OrdersItemRepository;
import com.springboot.repository.custom.OrdersRepository;
import com.springboot.repository.custom.ProductRepository;
import com.springboot.repository.custom.SaleRepository;
import com.springboot.repository.custom.UserRepository;
import com.springboot.util.Constants;

@Service("ordersService")
public class OrdersService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private OrdersRepository ordersRepo;
	
	@Autowired
	private OrdersItemRepository ordersItemRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private SaleRepository saleRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private OrdersItemOutRepository ordersItemOutRepo;
	
	public Order addOrders(EntityManager em, List<ProductModel> products, int userId, String customerName) throws Exception {
		Order order = ordersRepo.addOrders(em, userId, customerName, products);
		if(order != null) {
			for(ProductModel productM : products) {
				OrdersItem sli = new OrdersItem();
				sli.setProductName(productM.getProductName());
				sli.setProductCode(productM.getProductCode());
				sli.setBuyPrice(productM.getBuyPrice());
				sli.setSellPrice(productM.getSellPrice());
				sli.setSupplier(productM.getSupplier());
				sli.setUnit(productM.getUnit());
				sli.setOrderId(order.getOrderId());
				sli.setQuantitySold(productM.getQuantity());
				sli.setQuantityLeft(productM.getQuantity());
				sli.setProductId(productM.getProductId());
				
				ordersItemRepo.addSalesLogsItem(em, sli);
			}
		}
		
		return order;
	}
	
	public List<OrderModel> getOrders() throws Exception {
		List<OrderModel> ordersModelList = new ArrayList<OrderModel>();
		List<Order> orders = ordersRepo.getOrders(em);
		for(Order salesLog : orders) {
			OrderModel salesLogsModel = new OrderModel();
			salesLogsModel.setOrderId(salesLog.getOrderId());
			salesLogsModel.setCreatedDate(salesLog.getCreatedDate());
			List<OrdersItem> ordersItem = ordersItemRepo.getOrdersItemByOrderId(em, salesLog.getOrderId());
			salesLogsModel.setSalesLogsItem(ordersItem);
			
			User user = userRepo.getUserById(em, salesLog.getCreatedBy());
			salesLogsModel.setCreatedBy(user.getUsername());
			Customer customer = customerRepo.getCustomerById(em, salesLog.getCustomer());
			salesLogsModel.setCustomer(customer.getCustomerName());
			salesLogsModel.setCustomerLevel(customer.getCustomerLevel());
			salesLogsModel.setPaidAmount(salesLog.getPaidAmount());
			salesLogsModel.setTotalAmount(salesLog.getTotalAmount());
			salesLogsModel.setCashAmount(salesLog.getCashAmount());
			salesLogsModel.setTermAmount(salesLog.getTermAmount());
			salesLogsModel.setTermDueDate(salesLog.getTermDueDate());
			salesLogsModel.setPurchaseOrderStatus(salesLog.getPurchaseOrderStatus());
			
			ordersModelList.add(salesLogsModel);
		}
		return ordersModelList;
	}
	
	public List<OrderModel> getActiveOrders(boolean active) throws Exception {
		List<OrderModel> ordersModelList = new ArrayList<OrderModel>();
		List<Order> orders = ordersRepo.getActiveOrders(em, active);
		for(Order salesLog : orders) {
			OrderModel salesLogsModel = new OrderModel();
			salesLogsModel.setOrderId(salesLog.getOrderId());
			salesLogsModel.setCreatedDate(salesLog.getCreatedDate());
			List<OrdersItem> ordersItem = ordersItemRepo.getOrdersItemByOrderId(em, salesLog.getOrderId());
			salesLogsModel.setSalesLogsItem(ordersItem);
			
			User user = userRepo.getUserById(em, salesLog.getCreatedBy());
			salesLogsModel.setCreatedBy(user.getUsername());
			Customer customer = customerRepo.getCustomerById(em, salesLog.getCustomer());
			salesLogsModel.setCustomer(customer.getCustomerName());
			salesLogsModel.setCustomerLevel(customer.getCustomerLevel());
			salesLogsModel.setPaidAmount(salesLog.getPaidAmount());
			salesLogsModel.setTotalAmount(salesLog.getTotalAmount());
			salesLogsModel.setCashAmount(salesLog.getCashAmount());
			salesLogsModel.setTermAmount(salesLog.getTermAmount());
			salesLogsModel.setTermDueDate(salesLog.getTermDueDate());
			salesLogsModel.setPurchaseOrderStatus(salesLog.getPurchaseOrderStatus());
			
			ordersModelList.add(salesLogsModel);
		}
		return ordersModelList;
	}
	
	public List<OrderModel> getActiveOrders(boolean active, Map<String, String> searchMap) throws Exception {
		List<OrderModel> ordersModelList = new ArrayList<OrderModel>();
		List<Order> orders = ordersRepo.getActiveOrders(em, active, searchMap);
		for(Order salesLog : orders) {
			OrderModel salesLogsModel = new OrderModel();
			salesLogsModel.setOrderId(salesLog.getOrderId());
			salesLogsModel.setCreatedDate(salesLog.getCreatedDate());
			List<OrdersItem> ordersItem = ordersItemRepo.getOrdersItemByOrderId(em, salesLog.getOrderId());
			salesLogsModel.setSalesLogsItem(ordersItem);
			
			User user = userRepo.getUserById(em, salesLog.getCreatedBy());
			salesLogsModel.setCreatedBy(user.getUsername());
			Customer customer = customerRepo.getCustomerById(em, salesLog.getCustomer());
			salesLogsModel.setCustomer(customer.getCustomerName());
			salesLogsModel.setCustomerLevel(customer.getCustomerLevel());
			salesLogsModel.setPaidAmount(salesLog.getPaidAmount());
			salesLogsModel.setTotalAmount(salesLog.getTotalAmount());
			salesLogsModel.setCashAmount(salesLog.getCashAmount());
			salesLogsModel.setTermAmount(salesLog.getTermAmount());
			salesLogsModel.setTermDueDate(salesLog.getTermDueDate());
			salesLogsModel.setPurchaseOrderStatus(salesLog.getPurchaseOrderStatus());
			
			ordersModelList.add(salesLogsModel);
		}
		return ordersModelList;
	}
	
	public List<OrderModel> getCollections() throws Exception {
		List<OrderModel> ordersModelList = new ArrayList<OrderModel>();
		List<Order> orders = ordersRepo.getCollections(em);
		for(Order order : orders) {
			OrderModel orderModel = new OrderModel();
			orderModel.setOrderId(order.getOrderId());
			orderModel.setCreatedDate(order.getCreatedDate());
			List<OrdersItem> orderItem = ordersItemRepo.getOrdersItemByOrderId(em, order.getOrderId());
			orderModel.setSalesLogsItem(orderItem);
			
			User user = userRepo.getUserById(em, order.getCreatedBy());
			orderModel.setCreatedBy(user.getUsername());
			Customer customer = customerRepo.getCustomerById(em, order.getCustomer());
			orderModel.setCustomer(customer.getCustomerName());
			orderModel.setCustomerLevel(customer.getCustomerLevel());
			orderModel.setPaidAmount(order.getPaidAmount());
			orderModel.setTotalAmount(order.getTotalAmount());
			orderModel.setCashAmount(order.getCashAmount());
			orderModel.setTermAmount(order.getTermAmount());
			orderModel.setTermDueDate(order.getTermDueDate());
			orderModel.setPurchaseOrderStatus(order.getPurchaseOrderStatus());
			
			ordersModelList.add(orderModel);
		}
		return ordersModelList;
	}
	
	public List<OrderModel> getCollections(Map<String, String> searchMap) throws Exception {
		List<OrderModel> ordersModelList = new ArrayList<OrderModel>();
		List<Order> orders = ordersRepo.getCollections(em, searchMap);
		for(Order order : orders) {
			OrderModel orderModel = new OrderModel();
			orderModel.setOrderId(order.getOrderId());
			orderModel.setCreatedDate(order.getCreatedDate());
			List<OrdersItem> orderItem = ordersItemRepo.getOrdersItemByOrderId(em, order.getOrderId());
			orderModel.setSalesLogsItem(orderItem);
			
			User user = userRepo.getUserById(em, order.getCreatedBy());
			orderModel.setCreatedBy(user.getUsername());
			Customer customer = customerRepo.getCustomerById(em, order.getCustomer());
			orderModel.setCustomer(customer.getCustomerName());
			orderModel.setCustomerLevel(customer.getCustomerLevel());
			orderModel.setPaidAmount(order.getPaidAmount());
			orderModel.setTotalAmount(order.getTotalAmount());
			orderModel.setCashAmount(order.getCashAmount());
			orderModel.setTermAmount(order.getTermAmount());
			orderModel.setTermDueDate(order.getTermDueDate());
			orderModel.setPurchaseOrderStatus(order.getPurchaseOrderStatus());
			
			ordersModelList.add(orderModel);
		}
		return ordersModelList;
	}
	
	public OrderModel getOrdersById(String orderId) throws Exception {
		Order order = ordersRepo.getOrdersById(em, orderId);
		if(order != null) {
			OrderModel orderModel = new OrderModel();
			orderModel.setOrderId(order.getOrderId());
			orderModel.setCreatedDate(order.getCreatedDate());
			List<OrdersItem> salesLogsItem = ordersItemRepo.getOrdersItemByOrderId(em, order.getOrderId());
			orderModel.setSalesLogsItem(salesLogsItem);	
			
			User user = userRepo.getUserById(em, order.getCreatedBy());
			orderModel.setCreatedBy(user.getUsername());
			Customer customer = customerRepo.getCustomerById(em, order.getCustomer());
			orderModel.setCustomer(customer.getCustomerName());
			orderModel.setCustomerLevel(customer.getCustomerLevel());
			orderModel.setPaidAmount(order.getPaidAmount());
			orderModel.setTotalAmount(order.getTotalAmount());
			orderModel.setCashAmount(order.getCashAmount());
			orderModel.setTermAmount(order.getTermAmount());
			orderModel.setTermDueDate(order.getTermDueDate());
			orderModel.setPurchaseOrderStatus(order.getPurchaseOrderStatus());
			
			return orderModel;
		}
		
		return null;
	}
	
	public void regionalManagerApproved(String orderId, Boolean approved) throws Exception {
		Order order = ordersRepo.getOrdersById(em, orderId);
		if(order != null) {
			ordersRepo.regionalManagerApproved(em, order, approved);
		}
	}
	
	public void checkerConfirmOrder(OrderModel orderModel, Integer userId) throws Exception {
		Boolean flag = true;
		for(OrdersItem oi : orderModel.getSalesLogsItem()) {
			OrdersItem orderItem = ordersItemRepo.getOrdersItemById(em, oi.getItemId());
			double out = orderItem.getQuantityLeft() - oi.getQuantityLeft();
			OrdersItemOut oio = new OrdersItemOut();
			oio.setOutQuantity(out);
			oio.setOrderItemId(orderItem.getItemId());
			oio.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			oio.setCreatedBy(userId);
			ordersItemOutRepo.addOrdersItemOut(em, oio);
			ordersItemRepo.updateQuantityLeft(orderItem, oi.getQuantityLeft());
			
			Product product = productRepo.getProductById(em, orderItem.getProductId());
			productRepo.confirmCheckerOrder(product, (float) out);
			if(oi.getQuantityLeft() != 0) {
				flag = false;
			}
		}
		
		if(flag) {
			Order order = ordersRepo.getOrdersById(em, orderModel.getOrderId());
			if(order != null) {
				ordersRepo.completeOrder(em, order);
			}
		}
	}
	
	public void accountingApproved(String orderId, boolean approved, double cashAmount, 
			double termAmount, String termDueDate, int userId) throws Exception {
		Order order = ordersRepo.getOrdersById(em, orderId);
		if(order != null) {
			ordersRepo.accountingApproved(em, order, approved, cashAmount, termAmount, termDueDate);
			if(cashAmount > 0) {
				Sale sale = new Sale();
				sale.setAmount(cashAmount);
				sale.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				sale.setCreatedBy(userId);
				sale.setOrderId(order.getOrderId());
				sale.setSalesType(Constants.SALE_TYPE_CASH);
				saleRepo.addSales(em, sale);
			}
		}
	}
	
	public void collect(String orderId, Integer userId) throws Exception {
		Order order = ordersRepo.getOrdersById(em, orderId);
		if(order != null) {
			Sale sale = new Sale();
			sale.setAmount(order.getTermAmount());
			sale.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			sale.setCreatedBy(userId);
			sale.setOrderId(order.getOrderId());
			sale.setSalesType(Constants.SALE_TYPE_TERM);
			saleRepo.addSales(em, sale);
			ordersRepo.collect(em, order);
		}
	}
}
