package com.springboot.repository.custom;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entities.User;

@Repository("userRepository")
public class UserRepository {
	public User searchUserByUsername(EntityManager em, String username) {
		User user = null;

		StringBuilder userQuery = new StringBuilder("FROM User WHERE username = :username");
		Query query = em.createQuery(userQuery.toString());
		query.setParameter("username", username);
		user = (User) query.getSingleResult();

		return user;
	}
	
	public User searchUserByUsernameAndPassword(EntityManager em, String username, String password) {
		User user = null;

		StringBuilder userQuery = new StringBuilder("FROM User WHERE username = :username AND password = :password");
		Query query = em.createQuery(userQuery.toString());
		query.setParameter("username", username);
		query.setParameter("password", password);
		user = (User) query.getSingleResult();

		return user;
	}
	
	public User getUserById(EntityManager em, int id) {
		User user = null;

		StringBuilder userQuery = new StringBuilder("FROM User WHERE user_id = :id");
		Query query = em.createQuery(userQuery.toString());
		query.setParameter("id", id);
		user = (User) query.getSingleResult();

		return user;
	}
	
	@Transactional
	public void createUser(EntityManager em, String username, String password, String userType) throws Exception{
		User user = new User();
		User userExist; 
		
		try {
			userExist = searchUserByUsername(em, username);
		} catch(NoResultException nre) {
			userExist = null;
		}
		
		if(userExist == null) {
			user.setUsername(username);
			user.setUsertype(userType);
			user.setPassword(password);
			user.setDateCreated(new Date());
			user.setDeleted(false);
			
			em.persist(user);
		} else {
			if(userExist.getDeleted() == true) {
				userExist.setDeleted(false);
			} else {
				throw new Exception("User exists!");
			}
		}
	}
	
	@Transactional
	public void updateUser(EntityManager em, String username, String password, String userType, boolean deleted) throws Exception{
		User userExist; 
		
		try {
			userExist = searchUserByUsername(em, username);
		} catch(NoResultException nre) {
			userExist = null;
		}
		
		if(userExist != null) {
			userExist.setUsername(username);
			userExist.setUsertype(userType);
			userExist.setPassword(password);
			userExist.setDeleted(deleted);
			
			em.merge(userExist);
		} else {
			throw new Exception("User doesnt exist!");
		}
	}
}
