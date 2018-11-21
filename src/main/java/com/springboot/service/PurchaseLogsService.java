package com.springboot.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Product;
import com.springboot.entities.PurchasesLog;
import com.springboot.entities.PurchasesLogsItem;
import com.springboot.entities.User;
import com.springboot.model.ProductModel;
import com.springboot.model.PurchasesLogsModel;
import com.springboot.repository.custom.ProductRepository;
import com.springboot.repository.custom.PurchaseLogsItemRepository;
import com.springboot.repository.custom.PurchaseLogsRepository;
import com.springboot.repository.custom.UserRepository;

@Service("purchaseLogsService")
public class PurchaseLogsService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PurchaseLogsRepository purchaseLogsRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private PurchaseLogsItemRepository purchaseLogsItemRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public boolean addPurchaseLogs(EntityManager em, List<ProductModel> products, int userId) throws Exception {
		boolean flag = false;
		PurchasesLog purchaseLog = purchaseLogsRepo.addPurchaseLogs(em, userId);
		if(purchaseLog != null) {
			for(ProductModel productM : products) {
				PurchasesLogsItem pli = new PurchasesLogsItem();
				pli.setProductName(productM.getProductName());
				pli.setProductCode(productM.getProductCode());
				pli.setBuyPrice(productM.getBuyPrice());
				pli.setSellPrice(productM.getSellPrice());
				pli.setSupplier(productM.getSupplier());
				pli.setUnit(productM.getUnit());
				pli.setPurchasesLogId(purchaseLog.getPurchasesLogsId());
				pli.setQuantityAdded(productM.getQuantity());
				
				purchaseLogsItemRepo.addPurchaseLogsItem(em, pli);
			}
			flag = true;
		}
		
		return flag;
	}
	
	public List<PurchasesLogsModel> getPurchasesLogs() throws Exception {
		List<PurchasesLogsModel> purchasesLogsModelList = new ArrayList<PurchasesLogsModel>();
		List<PurchasesLog> purchasesLogs = purchaseLogsRepo.getPurchaseLogs(em);
		for(PurchasesLog purchasesLog : purchasesLogs) {
			PurchasesLogsModel purchasesLogsModel = new PurchasesLogsModel();
			purchasesLogsModel.setPurchasesLogsId(purchasesLog.getPurchasesLogsId());
			purchasesLogsModel.setCreatedDate(purchasesLog.getCreatedDate());
			List<PurchasesLogsItem> purchasesLogsItem = purchaseLogsItemRepo.getPurchaseLogsItemByPurchasesLogsId(em, purchasesLog.getPurchasesLogsId());
			purchasesLogsModel.setPurchasesLogsItem(purchasesLogsItem);
			
			User user = userRepo.getUserById(em, purchasesLog.getCreatedBy());
			purchasesLogsModel.setCreatedBy(user.getUsername());
			
			purchasesLogsModelList.add(purchasesLogsModel);
		}
		return purchasesLogsModelList;
	}
	
	public PurchasesLogsModel getPurchasesLogsById(int purchasesLogsId) throws Exception {
		PurchasesLog purchasesLog = purchaseLogsRepo.getPurchaseLogsById(em, purchasesLogsId);
		if(purchasesLog != null) {
			PurchasesLogsModel purchasesLogModel = new PurchasesLogsModel();
			purchasesLogModel.setPurchasesLogsId(purchasesLog.getPurchasesLogsId());
			purchasesLogModel.setCreatedDate(purchasesLog.getCreatedDate());
			List<PurchasesLogsItem> purchasesLogsItem = purchaseLogsItemRepo.getPurchaseLogsItemByPurchasesLogsId(em, purchasesLog.getPurchasesLogsId());
			purchasesLogModel.setPurchasesLogsItem(purchasesLogsItem);	
			
			User user = userRepo.getUserById(em, purchasesLog.getCreatedBy());
			purchasesLogModel.setCreatedBy(user.getUsername());
			
			return purchasesLogModel;
		}
		
		return null;
	}
}
