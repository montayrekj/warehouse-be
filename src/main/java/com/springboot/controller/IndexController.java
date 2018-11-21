package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;

import com.springboot.entities.Customer;
import com.springboot.entities.Supplier;
import com.springboot.entities.User;
import com.springboot.model.ProductModel;
import com.springboot.model.PurchasesLogsModel;
import com.springboot.model.ResponseModel;
import com.springboot.model.SalesLogsModel;
import com.springboot.service.CustomerService;
import com.springboot.service.ProductService;
import com.springboot.service.PurchaseLogsService;
import com.springboot.service.SalesLogsService;
import com.springboot.service.SupplierService;
import com.springboot.service.UserService;
import com.springboot.util.Constants;
import com.springboot.util.HashUtil;

@CrossOrigin(
        origins = { "*" }, 
        allowCredentials = "true",
        allowedHeaders = CorsConfiguration.ALL,  
        exposedHeaders = {})
@RestController
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PurchaseLogsService purchaseLogsService;
	
	@Autowired
	private SalesLogsService salesLogService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<User> login(String username, String password) {
		ResponseModel<User> responseModel = new ResponseModel<User>();
		
		try {
			User user = userService.searchUserByUsernameAndPassword(username, password);
			responseModel.setData(user);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage("User doesn't Exist");
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<User> createUser(String username, String password, String userType, String accessToken) {
		ResponseModel<User> responseModel = new ResponseModel<User>();
		
		try {
			if (Constants.LOGIN_ACCESS_TOKEN.equals(HashUtil.getHashMD5(accessToken))) {
				userService.createUser(username, password, userType);
				responseModel.setStatusCode(HttpStatus.OK);
			} else {
				responseModel.setMessage("Invalid access token");
				responseModel.setStatusCode(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<User> deleteUser(String username, String password, String userType, boolean deleted, String accessToken) {
		ResponseModel<User> responseModel = new ResponseModel<User>();
		
		try {
			if (Constants.LOGIN_ACCESS_TOKEN.equals(HashUtil.getHashMD5(accessToken))) {
				userService.updateUser(username, password, userType, deleted);
				responseModel.setStatusCode(HttpStatus.OK);
			} else {
				responseModel.setMessage("Invalid access token");
				responseModel.setStatusCode(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getSuppliers", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<Supplier>> getAllSuppliers() {
		ResponseModel<List<Supplier>> responseModel = new ResponseModel<List<Supplier>>();
		List<Supplier> suppliers;
		
		try {
			suppliers = supplierService.getAllSuplier();
			responseModel.setData(suppliers);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/addSupplier", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> addSuplier(String supplierName, String supplierAddress, String supplierContactNo, Integer userId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			supplierService.addSupplier(supplierName, supplierAddress, supplierContactNo, userId);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getCustomers", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<Customer>> getAllCustomers() {
		ResponseModel<List<Customer>> responseModel = new ResponseModel<List<Customer>>();
		List<Customer> customers;
		
		try {
			customers = customerService.getAllCustomers();
			responseModel.setData(customers);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> addCustomer(String customerName, String customerAddress, String customerContactNo, Integer userId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			customerService.addCustomer(customerName, customerAddress, customerContactNo, userId);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<ProductModel>> getProducts() {
		ResponseModel<List<ProductModel>> responseModel = new ResponseModel<List<ProductModel>>();
		List<ProductModel> products;
		
		try {
			products = productService.getAllProducts();
			responseModel.setData(products);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getProductsBelowLimit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<ProductModel>> getProductsBelowLimit() {
		ResponseModel<List<ProductModel>> responseModel = new ResponseModel<List<ProductModel>>();
		List<ProductModel> products;
		
		try {
			products = productService.getAllProductsBelowLimit();
			responseModel.setData(products);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> addProduct(ProductModel product) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			productService.addProduct(product);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getProductById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<ProductModel> getProductById(Integer id) {
		ResponseModel<ProductModel> responseModel = new ResponseModel<ProductModel>();
		ProductModel product;
		
		try {
			product = productService.getProductById(id);
			responseModel.setData(product);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> updateProduct(ProductModel product) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			productService.updateProduct(product);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> deleteProduct(Integer productId, Integer userId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			productService.deleteProduct(productId, userId);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/addStocks", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> addStocks(@RequestBody List<ProductModel> products) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			productService.addStocks(products);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/removeStocks/{customerName}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> removeStocks(@RequestBody List<ProductModel> products, @PathVariable String customerName) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			productService.removeStocks(products, customerName);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getPurchasesLogs", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<PurchasesLogsModel>> getPurchasesLogs() {
		ResponseModel<List<PurchasesLogsModel>> responseModel = new ResponseModel<List<PurchasesLogsModel>>();
		List<PurchasesLogsModel> purchasesLogs;
		
		try {
			purchasesLogs = purchaseLogsService.getPurchasesLogs();
			responseModel.setData(purchasesLogs);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getPurchasesLogsById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<PurchasesLogsModel> getPurchasesLogsById(Integer id) {
		ResponseModel<PurchasesLogsModel> responseModel = new ResponseModel<PurchasesLogsModel>();
		PurchasesLogsModel purchasesLog;
		
		try {
			purchasesLog = purchaseLogsService.getPurchasesLogsById(id);
			responseModel.setData(purchasesLog);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getSalesLogs", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<SalesLogsModel>> getSalesLogs() {
		ResponseModel<List<SalesLogsModel>> responseModel = new ResponseModel<List<SalesLogsModel>>();
		List<SalesLogsModel> salesLogs;
		
		try {
			salesLogs = salesLogService.getSalesLogs();
			responseModel.setData(salesLogs);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/collect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> collect(Integer salesLogsId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			salesLogService.collect(salesLogsId);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(true);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getCollections", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<SalesLogsModel>> getCollections() {
		ResponseModel<List<SalesLogsModel>> responseModel = new ResponseModel<List<SalesLogsModel>>();
		List<SalesLogsModel> salesLogs;
		
		try {
			salesLogs = salesLogService.getCollections();
			responseModel.setData(salesLogs);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getSalesLogsById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<SalesLogsModel> getSalesLogsById(Integer id) {
		ResponseModel<SalesLogsModel> responseModel = new ResponseModel<SalesLogsModel>();
		SalesLogsModel salesLog;
		
		try {
			salesLog = salesLogService.getSalesLogsById(id);
			responseModel.setData(salesLog);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/regionalManagerApproved", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> regionalManagerApproved(Integer salesLogsId, boolean approved) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			salesLogService.regionalManagerApproved(salesLogsId, approved);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/accountingApproved", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> accountingApproved(Integer salesLogsId, boolean approved, double cashAmount, 
			double termAmount, String termDueDate) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			salesLogService.accountingApproved(salesLogsId, approved, cashAmount, termAmount, termDueDate);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/checkerConfirmOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> checkerConfirmOrder(@RequestBody SalesLogsModel salesLogsModel) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			salesLogService.checkerConfirmOrder(salesLogsModel);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(false);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
}