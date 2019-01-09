package com.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;

import com.springboot.entities.Customer;
import com.springboot.entities.Order;
import com.springboot.entities.Supplier;
import com.springboot.entities.User;
import com.springboot.model.OrderModel;
import com.springboot.model.ProductModel;
import com.springboot.model.PurchasesLogsModel;
import com.springboot.model.ResponseModel;
import com.springboot.model.SaleModel;
import com.springboot.model.StocksModel;
import com.springboot.service.CustomerService;
import com.springboot.service.OrdersService;
import com.springboot.service.ProductService;
import com.springboot.service.PurchaseLogsService;
import com.springboot.service.SalesService;
import com.springboot.service.SupplierService;
import com.springboot.service.UserService;
import com.springboot.util.Constants;
import com.springboot.util.HashUtil;
import com.springboot.util.MailSender;

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
	private OrdersService ordersService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SalesService salesService;
	
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
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<User>> getUsers(String username, String password) {
		ResponseModel<List<User>> responseModel = new ResponseModel<List<User>>();
		List<User> users;
		
		try {
			users = userService.getUsers();
			responseModel.setData(users);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
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
	public ResponseModel<Boolean> updateUser(@RequestBody User user) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			userService.updateUser(user);
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
	
	/*@RequestMapping(value = "/updateUserTemp", method = RequestMethod.POST)
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
	}*/
	
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
	
	@RequestMapping(value = "/getSupplierById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Supplier> getSupplierById(Integer id) {
		ResponseModel<Supplier> responseModel = new ResponseModel<Supplier>();
		Supplier supplier;
		
		try {
			supplier = supplierService.getSupplierById(id);
			responseModel.setData(supplier);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/updateSupplier", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> updateSupplier(String supplierName, String supplierAddress, String supplierNumber, Integer supplierId, Integer id) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			supplierService.updateSupplier(supplierName, supplierAddress, supplierNumber, supplierId, id);
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
	public ResponseModel<Boolean> addCustomer(String customerName, String customerAddress, String customerContactNo, String customerContactPerson, Integer customerLevel, Integer userId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			customerService.addCustomer(customerName, customerAddress, customerContactNo, customerContactPerson, customerLevel, userId);
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
	
	@RequestMapping(value = "/getCustomerById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Customer> getCustomerById(Integer id) {
		ResponseModel<Customer> responseModel = new ResponseModel<Customer>();
		Customer customer;
		
		try {
			customer = customerService.getCustomerById(id);
			responseModel.setData(customer);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> updateCustomer(String customerName, String customerAddress, String customerNumber, Integer customerId, String customerContactPerson, Integer customerLevel, Integer id) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			customerService.updateCustomer(customerName, customerAddress, customerNumber, customerId, customerContactPerson, customerLevel,  id);
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
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel<List<ProductModel>> getProductsSearch(@RequestParam Map<String, String> queryMap) {
		ResponseModel<List<ProductModel>> responseModel = new ResponseModel<List<ProductModel>>();
		List<ProductModel> products;
		
		try {
			products = productService.getAllProducts(queryMap);
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
	public ResponseModel<Order> removeStocks(@RequestBody List<ProductModel> products, @PathVariable String customerName) {
		ResponseModel<Order> responseModel = new ResponseModel<Order>();
		Order order = null;
		
		try {
			order = productService.removeStocks(products, customerName);
			responseModel.setData(order);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(order);
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
	
	@RequestMapping(value = "/getPurchasesLogs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel<List<PurchasesLogsModel>> getPurchasesLogsSearch(@RequestParam Map<String, String> queryMap) {
		ResponseModel<List<PurchasesLogsModel>> responseModel = new ResponseModel<List<PurchasesLogsModel>>();
		List<PurchasesLogsModel> purchasesLogs;
		
		try {
			purchasesLogs = purchaseLogsService.getPurchasesLogs(queryMap);
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
	
	@RequestMapping(value = "/getOrders", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<OrderModel>> getOrders() {
		ResponseModel<List<OrderModel>> responseModel = new ResponseModel<List<OrderModel>>();
		List<OrderModel> orders;
		
		try {
			orders = ordersService.getOrders();
			responseModel.setData(orders);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getActiveOrders", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<OrderModel>> getActiveOrders(Boolean active) {
		ResponseModel<List<OrderModel>> responseModel = new ResponseModel<List<OrderModel>>();
		List<OrderModel> orders;
		
		try {
			orders = ordersService.getActiveOrders(active);
			responseModel.setData(orders);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getActiveOrders", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel<List<OrderModel>> getActiveOrdersSearch(@RequestParam Map<String, String> queryMap) {
		ResponseModel<List<OrderModel>> responseModel = new ResponseModel<List<OrderModel>>();
		List<OrderModel> orders;
		
		try {
			Boolean active = Boolean.parseBoolean(queryMap.get("active"));
			orders = ordersService.getActiveOrders(active, queryMap);
			responseModel.setData(orders);
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
	public ResponseModel<Boolean> collect(String orderId, Integer userId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			ordersService.collect(orderId, userId);
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
	public ResponseModel<List<OrderModel>> getCollections() {
		ResponseModel<List<OrderModel>> responseModel = new ResponseModel<List<OrderModel>>();
		List<OrderModel> orders;
		
		try {
			orders = ordersService.getCollections();
			responseModel.setData(orders);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getCollections", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel<List<OrderModel>> getCollectionsSearch(@RequestParam Map<String, String> queryMap) {
		ResponseModel<List<OrderModel>> responseModel = new ResponseModel<List<OrderModel>>();
		List<OrderModel> orders;
		
		try {
			orders = ordersService.getCollections(queryMap);
			responseModel.setData(orders);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getOrderById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<OrderModel> getOrderById(String id) {
		ResponseModel<OrderModel> responseModel = new ResponseModel<OrderModel>();
		OrderModel order;
		
		try {
			order = ordersService.getOrdersById(id);
			responseModel.setData(order);
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
	public ResponseModel<Boolean> regionalManagerApproved(String orderId, boolean approved) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			ordersService.regionalManagerApproved(orderId, approved);
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
	public ResponseModel<Boolean> accountingApproved(String orderId, boolean approved, double cashAmount, 
			double termAmount, String termDueDate, Integer userId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			ordersService.accountingApproved(orderId, approved, cashAmount, termAmount, termDueDate, userId);
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
	
	@RequestMapping(value = "/confirmQuantity", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<String>> confirmQuantity(@RequestBody OrderModel orderModel) {
		ResponseModel<List<String>> responseModel = new ResponseModel<List<String>>();
		List<String> insufficientStocks = null;
		try {
			insufficientStocks = productService.confirmQuantity(orderModel);
			responseModel.setData(insufficientStocks);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setData(insufficientStocks);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/checkerConfirmOrder/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> checkerConfirmOrder(@RequestBody OrderModel orderModel, @PathVariable Integer userId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			ordersService.checkerConfirmOrder(orderModel, userId);
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
	
	@RequestMapping(value = "/emailRegionalManager", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> emailRegionalManager(String orderId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			List<User> regionalManager = userService.getRegionalManager();
			OrderModel order = ordersService.getOrdersById(orderId);
			MailSender.emailRegionalManager(regionalManager, order);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/emailAccounting", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> emailAccounting(String orderId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			List<User> accounting = userService.getAccounting();
			OrderModel order = ordersService.getOrdersById(orderId);
			MailSender.emailAccounting(accounting, order);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/emailChecker", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> emailChecker(String orderId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			List<User> checker = userService.getChecker();
			OrderModel order = ordersService.getOrdersById(orderId);
			MailSender.emailChecker(checker, order);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/sendGatePass", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public ResponseModel<Boolean> sendGatePass(Integer userId, String orderId, String file) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			//Buffer buff = new com.mysql.jdbc.Buffer(file.getBytes());
			User user = userService.getUserById(userId);
			OrderModel order = ordersService.getOrdersById(orderId);
			MailSender.emailGatePass(user, file, order);
			responseModel.setData(true);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getSales", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<SaleModel>> getSales() {
		ResponseModel<List<SaleModel>> responseModel = new ResponseModel<List<SaleModel>>();
		List<SaleModel> sales;
		
		try {
			sales = salesService.getSales();
			responseModel.setData(sales);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getSales", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel<List<SaleModel>> getSalesSearch(@RequestParam Map<String, String> queryMap) {
		ResponseModel<List<SaleModel>> responseModel = new ResponseModel<List<SaleModel>>();
		List<SaleModel> sales;
		
		try {
			sales = salesService.getSales(queryMap);
			responseModel.setData(sales);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/dailySales", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Double> dailySales() {
		ResponseModel<Double> responseModel = new ResponseModel<Double>();
		Double sales;
		
		try {
			sales = salesService.getDailySales();
			responseModel.setData(sales);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/dailySalesChart", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<Double>> dailySalesChart() {
		ResponseModel<List<Double>> responseModel = new ResponseModel<List<Double>>();
		List<Double> sales;
		
		try {
			sales = salesService.getDailySalesChart();
			responseModel.setData(sales);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/weeklySales", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Double> weeklySales() {
		ResponseModel<Double> responseModel = new ResponseModel<Double>();
		Double sales;
		
		try {
			sales = salesService.getWeeklySales();
			responseModel.setData(sales);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/monthlySales", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Double> monthlySales() {
		ResponseModel<Double> responseModel = new ResponseModel<Double>();
		Double sales;
		
		try {
			sales = salesService.getMonthlySales();
			responseModel.setData(sales);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/monthlySalesChart", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<Double>> monthlySalesChart() {
		ResponseModel<List<Double>> responseModel = new ResponseModel<List<Double>>();
		List<Double> sales;
		
		try {
			sales = salesService.getMonthlySalesChart();
			responseModel.setData(sales);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getStocks", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<List<StocksModel>> getStocks() {
		ResponseModel<List<StocksModel>> responseModel = new ResponseModel<List<StocksModel>>();
		List<StocksModel> stocks;
		
		try {
			stocks = productService.getStocks();
			responseModel.setData(stocks);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
	
	@RequestMapping(value = "/getStocks", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel<List<StocksModel>> getStocksSearch(@RequestParam Map<String, String> queryMap) {
		ResponseModel<List<StocksModel>> responseModel = new ResponseModel<List<StocksModel>>();
		List<StocksModel> stocks;
		
		try {
			stocks = productService.getStocks(queryMap);
			responseModel.setData(stocks);
			responseModel.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseModel.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			responseModel.setMessage(e.getMessage());
		}
		
		return responseModel;
	}
}