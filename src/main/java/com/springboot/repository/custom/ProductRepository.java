package com.springboot.repository.custom;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Product;
import com.springboot.model.ProductModel;

@Repository("productRepository")
public class ProductRepository {
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	public List<Product> getAllProducts(EntityManager em) {
		List<Product> products = null;

		StringBuilder productsQuery = new StringBuilder("FROM Product WHERE deleted = false");
		TypedQuery<Product> query = em.createQuery(productsQuery.toString(), Product.class);
		products = query.getResultList();

		return products;
	}
	
	public List<Product> getAllProductsBelowLimit(EntityManager em) {
		List<Product> products = null;

		StringBuilder productsQuery = new StringBuilder("FROM Product WHERE deleted = false AND quantity <= quantity_limit");
		TypedQuery<Product> query = em.createQuery(productsQuery.toString(), Product.class);
		products = query.getResultList();

		return products;
	}
	
	public Product getProductById(EntityManager em, int productId) {
		Product product = null;
		
		StringBuilder productsQuery = new StringBuilder("FROM Product WHERE product_id = :id");
		TypedQuery<Product> query = em.createQuery(productsQuery.toString(), Product.class);
		query.setParameter("id", productId);
		product = query.getSingleResult();

		return product;
	}
	
	@Transactional
	public void addProduct(EntityManager em, Product product) throws Exception{
		Product productExist; 
		
		try {
			productExist = getProductById(em, product.getProductId());
		} catch(NoResultException nre) {
			productExist = null;
		}
		
		if(productExist == null) {
			em.persist(product);
		} else {
			if(productExist.getDeleted() == true) {
				productExist.setDeleted(false);
			} else {
				throw new Exception("Product exists!");
			}
		}
	}
	
	@Transactional
	public void updateProduct(EntityManager em, ProductModel productM) throws Exception{
		Product productExist; 
		
		try {
			productExist = getProductById(em, productM.getProductId());
		} catch(NoResultException nre) {
			productExist = null;
		}
		
		if(productExist != null) {
			productExist.setProductName(productM.getProductName());
			productExist.setProductCode(productM.getProductCode());
			productExist.setQuantity(productM.getQuantity());
			productExist.setUnit(productM.getUnit());
			productExist.setBuyPrice(productM.getBuyPrice());
			productExist.setSellPrice(productM.getSellPrice());
			productExist.setQuantityLimit(productM.getQuantityLimit());
			productExist.setSupplierBean(supplierRepo.getSupplierByName(em, productM.getSupplier()));
			
			//Tempo values
			productExist.setModifiedBy(productM.getCreatedBy());
			productExist.setDateModified(new Timestamp(System.currentTimeMillis()));
		} else {
			throw new Exception("Product doesn't exist!");
		}
	}
	
	@Transactional
	public void deleteProduct(EntityManager em, int productId, int userId) throws Exception{
		Product productExist; 
		
		try {
			productExist = getProductById(em, productId);
		} catch(NoResultException nre) {
			productExist = null;
		}
		
		if(productExist != null) {
			productExist.setDeleted(true);
			productExist.setModifiedBy(userId);
			productExist.setDateModified(new Timestamp(System.currentTimeMillis()));
		} else {
			throw new Exception("Product doesn't exist!");
		}
	}
	
	@Transactional
	public void addStocks(EntityManager em, ProductModel productM, int modifiedBy) throws Exception{
		Product productExist; 
		
		try {
			productExist = getProductById(em, productM.getProductId());
		} catch(NoResultException nre) {
			productExist = null;
		}
		
		if(productExist != null) {
			productExist.setQuantity(productExist.getQuantity() + productM.getQuantity());
			productExist.setModifiedBy(modifiedBy);
			productExist.setDateModified(new Timestamp(System.currentTimeMillis()));
		} else {
			throw new Exception("Product doesn't exist!");
		}
	}
	
	@Transactional
	public void removeStocks(EntityManager em, ProductModel productM, int modifiedBy) throws Exception{
		Product productExist; 
		
		try {
			productExist = getProductById(em, productM.getProductId());
		} catch(NoResultException nre) {
			productExist = null;
		}
		
		if(productExist != null) {
			productExist.setQuantity(productExist.getQuantity() - productM.getQuantity());
			productExist.setModifiedBy(modifiedBy);
			productExist.setDateModified(new Timestamp(System.currentTimeMillis()));
		} else {
			throw new Exception("Product doesn't exist!");
		}
	}
	
	@Transactional
	public void confirmCheckerOrder(Product product, float quantityOut) {
		product.setQuantity(product.getQuantity() - quantityOut);
	}
}
