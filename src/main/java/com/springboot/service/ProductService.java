package com.springboot.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Product;
import com.springboot.model.ProductModel;
import com.springboot.repository.custom.ProductRepository;
import com.springboot.repository.custom.SupplierRepository;
import com.springboot.repository.custom.UserRepository;

@Service("productService")
public class ProductService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private UserRepository userRepo;
	
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
			productM.setPrice(product.getPrice());
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
			productM.setPrice(product.getPrice());
			productM.setQuantityLimit(product.getQuantityLimit());
			productM.setSupplier(product.getSupplierBean().getSupplierName());
			
			productsM.add(productM);
		}
		
		return productsM;
	}
	
	public void addProduct(ProductModel productM) throws Exception {
		Product product = new Product();
		product.setProductName(productM.getProductName());
		product.setProductCode(productM.getProductCode());
		product.setQuantity(productM.getQuantity());
		product.setUnit(productM.getUnit());
		product.setPrice(productM.getPrice());
		product.setQuantityLimit(productM.getQuantityLimit());
		product.setSupplierBean(supplierRepo.getSupplierByName(em, productM.getSupplier()));
		
		//Tempo values
		product.setCreatedBy(1);
		product.setModifiedBy(1);
		
		productRepo.addProduct(em, product);
	}
	
	public void updateProduct(ProductModel productM) throws Exception {
		productRepo.updateProduct(em, productM);
	}
	
	public void deleteProduct(int productId) throws Exception{
		productRepo.deleteProduct(em, productId);
	}
	
	public void addStocks(List<ProductModel> products) throws Exception {
		for(ProductModel productM : products) {
			productRepo.addStocks(em, productM);
		}
	}
}
