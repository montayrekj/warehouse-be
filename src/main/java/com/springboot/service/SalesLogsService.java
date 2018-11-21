package com.springboot.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Product;
import com.springboot.entities.SalesLog;
import com.springboot.entities.SalesLogsItem;
import com.springboot.entities.User;
import com.springboot.model.ProductModel;
import com.springboot.model.SalesLogsModel;
import com.springboot.repository.custom.ProductRepository;
import com.springboot.repository.custom.SalesLogsItemRepository;
import com.springboot.repository.custom.SalesLogsRepository;
import com.springboot.repository.custom.UserRepository;

@Service("salesLogsService")
public class SalesLogsService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private SalesLogsRepository salesLogsRepo;
	
	@Autowired
	private SalesLogsItemRepository salesLogsItemRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public boolean addSalesLogs(EntityManager em, List<ProductModel> products, int userId, String customerName) throws Exception {
		boolean flag = false;
		SalesLog salesLog = salesLogsRepo.addSalesLogs(em, userId, customerName, products);
		if(salesLog != null) {
			for(ProductModel productM : products) {
				SalesLogsItem sli = new SalesLogsItem();
				sli.setProductName(productM.getProductName());
				sli.setProductCode(productM.getProductCode());
				sli.setBuyPrice(productM.getBuyPrice());
				sli.setSellPrice(productM.getSellPrice());
				sli.setSupplier(productM.getSupplier());
				sli.setUnit(productM.getUnit());
				sli.setSalesLogsId(salesLog.getSalesLogsId());
				sli.setQuantitySold(productM.getQuantity());
				sli.setQuantityLeft(productM.getQuantity());
				sli.setProductId(productM.getProductId());
				
				salesLogsItemRepo.addSalesLogsItem(em, sli);
			}
			flag = true;
		}
		
		return flag;
	}
	
	public List<SalesLogsModel> getSalesLogs() throws Exception {
		List<SalesLogsModel> salesLogsModelList = new ArrayList<SalesLogsModel>();
		List<SalesLog> salesLogs = salesLogsRepo.getSalesLogs(em);
		for(SalesLog salesLog : salesLogs) {
			SalesLogsModel salesLogsModel = new SalesLogsModel();
			salesLogsModel.setSalesLogsId(salesLog.getSalesLogsId());
			salesLogsModel.setCreatedDate(salesLog.getCreatedDate());
			List<SalesLogsItem> salesLogsItem = salesLogsItemRepo.getSalesLogsItemByPurchasesLogsId(em, salesLog.getSalesLogsId());
			salesLogsModel.setSalesLogsItem(salesLogsItem);
			
			User user = userRepo.getUserById(em, salesLog.getCreatedBy());
			salesLogsModel.setCreatedBy(user.getUsername());
			salesLogsModel.setCustomer(salesLog.getCustomer());
			salesLogsModel.setPaidAmount(salesLog.getPaidAmount());
			salesLogsModel.setTotalAmount(salesLog.getTotalAmount());
			salesLogsModel.setCashAmount(salesLog.getCashAmount());
			salesLogsModel.setTermAmount(salesLog.getTermAmount());
			salesLogsModel.setTermDueDate(salesLog.getTermDueDate());
			salesLogsModel.setPurchaseOrderStatus(salesLog.getPurchaseOrderStatus());
			
			salesLogsModelList.add(salesLogsModel);
		}
		return salesLogsModelList;
	}
	
	public List<SalesLogsModel> getCollections() throws Exception {
		List<SalesLogsModel> salesLogsModelList = new ArrayList<SalesLogsModel>();
		List<SalesLog> salesLogs = salesLogsRepo.getCollections(em);
		for(SalesLog salesLog : salesLogs) {
			SalesLogsModel salesLogsModel = new SalesLogsModel();
			salesLogsModel.setSalesLogsId(salesLog.getSalesLogsId());
			salesLogsModel.setCreatedDate(salesLog.getCreatedDate());
			List<SalesLogsItem> salesLogsItem = salesLogsItemRepo.getSalesLogsItemByPurchasesLogsId(em, salesLog.getSalesLogsId());
			salesLogsModel.setSalesLogsItem(salesLogsItem);
			
			User user = userRepo.getUserById(em, salesLog.getCreatedBy());
			salesLogsModel.setCreatedBy(user.getUsername());
			salesLogsModel.setCustomer(salesLog.getCustomer());
			salesLogsModel.setPaidAmount(salesLog.getPaidAmount());
			salesLogsModel.setTotalAmount(salesLog.getTotalAmount());
			salesLogsModel.setCashAmount(salesLog.getCashAmount());
			salesLogsModel.setTermAmount(salesLog.getTermAmount());
			salesLogsModel.setTermDueDate(salesLog.getTermDueDate());
			salesLogsModel.setPurchaseOrderStatus(salesLog.getPurchaseOrderStatus());
			
			salesLogsModelList.add(salesLogsModel);
		}
		return salesLogsModelList;
	}
	
	public SalesLogsModel getSalesLogsById(int salesLogsId) throws Exception {
		SalesLog salesLog = salesLogsRepo.getSalesLogsById(em, salesLogsId);
		if(salesLog != null) {
			SalesLogsModel salesLogsModel = new SalesLogsModel();
			salesLogsModel.setSalesLogsId(salesLog.getSalesLogsId());
			salesLogsModel.setCreatedDate(salesLog.getCreatedDate());
			List<SalesLogsItem> salesLogsItem = salesLogsItemRepo.getSalesLogsItemByPurchasesLogsId(em, salesLog.getSalesLogsId());
			salesLogsModel.setSalesLogsItem(salesLogsItem);	
			
			User user = userRepo.getUserById(em, salesLog.getCreatedBy());
			salesLogsModel.setCreatedBy(user.getUsername());
			salesLogsModel.setCustomer(salesLog.getCustomer());
			salesLogsModel.setPaidAmount(salesLog.getPaidAmount());
			salesLogsModel.setTotalAmount(salesLog.getTotalAmount());
			salesLogsModel.setCashAmount(salesLog.getCashAmount());
			salesLogsModel.setTermAmount(salesLog.getTermAmount());
			salesLogsModel.setTermDueDate(salesLog.getTermDueDate());
			salesLogsModel.setPurchaseOrderStatus(salesLog.getPurchaseOrderStatus());
			
			return salesLogsModel;
		}
		
		return null;
	}
	
	public void regionalManagerApproved(int salesLogsId, Boolean approved) throws Exception {
		SalesLog salesLog = salesLogsRepo.getSalesLogsById(em, salesLogsId);
		if(salesLog != null) {
			salesLogsRepo.regionalManagerApproved(em, salesLog, approved);
		}
	}
	
	public void checkerConfirmOrder(SalesLogsModel salesLogsModel) throws Exception {
		Boolean flag = true;
		for(SalesLogsItem sls : salesLogsModel.getSalesLogsItem()) {
			SalesLogsItem salesLogsItem = salesLogsItemRepo.getSalesLogsItemById(em, sls.getItemId());
			salesLogsItemRepo.updateQuantityLeft(salesLogsItem, sls.getQuantityLeft());
			
			float quantityOut = sls.getQuantitySold() - sls.getQuantityLeft();
			Product product = productRepo.getProductById(em, salesLogsItem.getProductId());
			productRepo.confirmCheckerOrder(product, quantityOut);
			if(sls.getQuantityLeft() != 0) {
				flag = false;
			}
		}
		
		if(flag) {
			SalesLog salesLog = salesLogsRepo.getSalesLogsById(em, salesLogsModel.getSalesLogsId());
			if(salesLog != null) {
				salesLogsRepo.completeOrder(em, salesLog);
			}
		}
	}
	
	public void accountingApproved(Integer salesLogsId, boolean approved, double cashAmount, 
			double termAmount, String termDueDate) throws Exception {
		SalesLog salesLog = salesLogsRepo.getSalesLogsById(em, salesLogsId);
		if(salesLog != null) {
			salesLogsRepo.accountingApproved(em, salesLog, approved, cashAmount, termAmount, termDueDate);
		}
	}
	
	public void collect(Integer salesLogsId) throws Exception {
		SalesLog salesLog = salesLogsRepo.getSalesLogsById(em, salesLogsId);
		if(salesLog != null) {
			salesLogsRepo.collect(em, salesLog);
		}
	}
}
