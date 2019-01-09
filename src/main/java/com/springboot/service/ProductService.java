package com.springboot.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Order;
import com.springboot.entities.OrdersItem;
import com.springboot.entities.Product;
import com.springboot.model.OrderModel;
import com.springboot.model.ProductModel;
import com.springboot.model.StocksModel;
import com.springboot.repository.custom.OrdersItemRepository;
import com.springboot.repository.custom.ProductRepository;
import com.springboot.repository.custom.PurchaseLogsItemRepository;
import com.springboot.repository.custom.SupplierRepository;

@Service("productService")
public class ProductService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired 
	private PurchaseLogsService purchaseLogsService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrdersItemRepository ordersItemRepo;
	
	@Autowired
	private PurchaseLogsItemRepository purchaseLogsItemRepo;
	
	public List<ProductModel> getAllProducts() throws IllegalAccessException, InvocationTargetException{
		List<Product> products = productRepo.getAllProducts(em);
		List<ProductModel> productsM = new ArrayList<ProductModel>();
		
		for(Product product : products) {
			ProductModel productM = new ProductModel();
			productM.setProductId(product.getProductId());
			productM.setProductName(product.getProductName());
			productM.setProductCode(product.getProductCode());
			productM.setQuantity(product.getQuantity());
			productM.setUnit(product.getUnit());
			productM.setBuyPrice(product.getBuyPrice());
			productM.setSellPrice(product.getSellPrice());
			productM.setQuantityLimit(product.getQuantityLimit());
			productM.setSupplier(product.getSupplierBean().getSupplierName());
			
			productsM.add(productM);
		}
		
		return productsM;
	}
	
	public List<ProductModel> getAllProducts(Map<String, String> searchMap) throws IllegalAccessException, InvocationTargetException{
		List<Product> products = productRepo.getAllProducts(em, searchMap);
		List<ProductModel> productsM = new ArrayList<ProductModel>();
		
		for(Product product : products) {
			ProductModel productM = new ProductModel();
			productM.setProductId(product.getProductId());
			productM.setProductName(product.getProductName());
			productM.setProductCode(product.getProductCode());
			productM.setQuantity(product.getQuantity());
			productM.setUnit(product.getUnit());
			productM.setBuyPrice(product.getBuyPrice());
			productM.setSellPrice(product.getSellPrice());
			productM.setQuantityLimit(product.getQuantityLimit());
			productM.setSupplier(product.getSupplierBean().getSupplierName());
			
			productsM.add(productM);
		}
		
		return productsM;
	}
	
	public List<ProductModel> getAllProductsBelowLimit() throws IllegalAccessException, InvocationTargetException{
		List<Product> products = productRepo.getAllProductsBelowLimit(em);
		List<ProductModel> productsM = new ArrayList<ProductModel>();
		
		for(Product product : products) {
			ProductModel productM = new ProductModel();
			productM.setProductId(product.getProductId());
			productM.setProductName(product.getProductName());
			productM.setProductCode(product.getProductCode());
			productM.setQuantity(product.getQuantity());
			productM.setUnit(product.getUnit());
			productM.setBuyPrice(product.getBuyPrice());
			productM.setSellPrice(product.getSellPrice());
			productM.setQuantityLimit(product.getQuantityLimit());
			productM.setSupplier(product.getSupplierBean().getSupplierName());
			
			productsM.add(productM);
		}
		
		return productsM;
	}
	
	public ProductModel getProductById(int productId) throws Exception {
		Product product = productRepo.getProductById(em, productId);
		if(product != null) {
			ProductModel productM = new ProductModel();
			productM.setProductId(product.getProductId());
			productM.setProductName(product.getProductName());
			productM.setProductCode(product.getProductCode());
			productM.setQuantity(product.getQuantity());
			productM.setUnit(product.getUnit());
			productM.setBuyPrice(product.getBuyPrice());
			productM.setSellPrice(product.getSellPrice());
			productM.setQuantityLimit(product.getQuantityLimit());
			productM.setSupplier(product.getSupplierBean().getSupplierName());
			productM.setCreatedBy(product.getCreatedBy());
			
			return productM;
		}
		
		return null;
	}
	
	public void addProduct(ProductModel productM) throws Exception {
		Product product = new Product();
		product.setProductName(productM.getProductName());
		product.setProductCode(productM.getProductCode());
		product.setQuantity(productM.getQuantity());
		product.setUnit(productM.getUnit());
		product.setBuyPrice(productM.getBuyPrice());
		product.setSellPrice(productM.getSellPrice());
		product.setQuantityLimit(productM.getQuantityLimit());
		product.setSupplierBean(supplierRepo.getSupplierByName(em, productM.getSupplier()));
		
		//Tempo values
		product.setCreatedBy(productM.getCreatedBy());
		product.setModifiedBy(productM.getCreatedBy());
		
		productRepo.addProduct(em, product);
	}
	
	public void updateProduct(ProductModel productM) throws Exception {
		productRepo.updateProduct(em, productM);
	}
	
	public void deleteProduct(int productId, int userId) throws Exception{
		productRepo.deleteProduct(em, productId, userId);
	}
	
	public void addStocks(List<ProductModel> products) throws Exception {
		int modifiedBy = products.get(0).getModifiedBy();
		boolean flag = purchaseLogsService.addPurchaseLogs(em, products, modifiedBy);
		if(flag) {
			
			for(ProductModel productM : products) {
				productRepo.addStocks(em, productM, modifiedBy);
			}
		}
	}
	
	public Order removeStocks(List<ProductModel> products, String customerName) throws Exception {
		int modifiedBy = products.get(0).getModifiedBy();
		return ordersService.addOrders(em, products, modifiedBy, customerName);
	}
	
	public List<StocksModel> getStocks() throws Exception{
		List<Product> products = productRepo.getAllProducts(em);
		List<StocksModel> stocks = new ArrayList<StocksModel>();
		if(products != null) {
			for(Product product : products) {
				StocksModel stockModel = new StocksModel();
				stockModel.setProductName(product.getProductName());
				
				//Get Quantity Out
				Double quantityOut = ordersItemRepo.getQuantityOutByProduct(em, product.getProductId());
				stockModel.setQuantityOut(quantityOut);
				
				Double quantityIn = purchaseLogsItemRepo.getQuantityInByProduct(em, product.getProductId());
				stockModel.setQuantityIn(quantityIn);
				
				stocks.add(stockModel);
			}
		}
		return stocks;
	}
	
	public List<StocksModel> getStocks(Map<String, String> tempMap) throws Exception{
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("productName", tempMap.get("productName"));
		searchMap.put("productCode", "");
		searchMap.put("unit", "");
		searchMap.put("supplier", "");
		
		List<Product> products = productRepo.getAllProducts(em, searchMap);
		List<StocksModel> stocks = new ArrayList<StocksModel>();
		if(products != null) {
			for(Product product : products) {
				StocksModel stockModel = new StocksModel();
				stockModel.setProductName(product.getProductName());
				
				//Get Quantity Out
				Double quantityOut = ordersItemRepo.getQuantityOutByProduct(em, product.getProductId());
				stockModel.setQuantityOut(quantityOut);
				
				Double quantityIn = purchaseLogsItemRepo.getQuantityInByProduct(em, product.getProductId());
				stockModel.setQuantityIn(quantityIn);
				
				stocks.add(stockModel);
			}
		}
		return stocks;
	}
	
	public List<String> confirmQuantity(OrderModel orderModel) throws Exception {
		List<String> insufficientStocks = new ArrayList<String>();
		for(OrdersItem oi : orderModel.getSalesLogsItem()) {
			Product product = productRepo.getProductById(em, oi.getProductId());
			OrdersItem tempOrderItem = ordersItemRepo.getOrdersItemById(em, oi.getItemId());
			double out = tempOrderItem.getQuantityLeft() - oi.getQuantityLeft();
			if(out > product.getQuantity()) {
				insufficientStocks.add(product.getProductName());
			}
		}
		
		return insufficientStocks;
	}
}
