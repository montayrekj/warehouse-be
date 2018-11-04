package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.entities.User;
import com.springboot.model.ProductModel;
import com.springboot.model.ResponseModel;
import com.springboot.service.ProductService;
import com.springboot.service.UserService;
import com.springboot.util.Constants;
import com.springboot.util.HashUtil;

@CrossOrigin(origins = "*")
@RestController
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
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
	public ResponseModel<Boolean> deleteProduct(Integer productId) {
		ResponseModel<Boolean> responseModel = new ResponseModel<Boolean>();
		
		try {
			productService.deleteProduct(productId);
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
}