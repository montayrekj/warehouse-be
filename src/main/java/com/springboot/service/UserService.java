package com.springboot.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.User;
import com.springboot.repository.custom.UserRepository;

@Service("userService")
public class UserService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserRepository userRepo;
	
	public List<User> getUsers(){
		return userRepo.getUsers(em);
	}
	
	public User getUserById(Integer id){
		return userRepo.getUserById(em, id);
	}
	
	public User searchUserByUsername(String username){
		return userRepo.searchUserByUsername(em, username);
	}
	
	public User searchUserByUsernameAndPassword(String username, String password){
		return userRepo.searchUserByUsernameAndPassword(em, username, password);
	}
	
	public void createUser(String username, String password, String userType) throws Exception{
		userRepo.createUser(em, username, password, userType);
	}
	
	public void updateUser(User user) throws Exception{
		userRepo.updateUser(em, user);
	}
	
	public List<User> getRegionalManager() {
		return userRepo.getRegionalManager(em);
	}
	
	public List<User> getAccounting() {
		return userRepo.getAccounting(em);
	}
	
	public List<User> getChecker() {
		return userRepo.getChecker(em);
	}
	
	/*public void updateUser(String username, String password, String userType, boolean deleted) throws Exception{
		userRepo.updateUser(em, username, password, userType, deleted);
	}*/
}
