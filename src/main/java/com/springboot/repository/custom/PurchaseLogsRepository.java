package com.springboot.repository.custom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.entities.PurchasesLog;
import com.springboot.entities.User;
import com.springboot.util.TimestampParser;

@Repository("purchaseLogsRepository")
public class PurchaseLogsRepository {
	
	@Autowired
	UserRepository userRepo;
	
	@Transactional
	public PurchasesLog addPurchaseLogs(EntityManager em, int userId) throws Exception{
		PurchasesLog purchaseLog = new PurchasesLog();
		purchaseLog.setCreatedBy(userId);
		purchaseLog.setModifiedBy(userId);
		purchaseLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		purchaseLog.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		
		em.persist(purchaseLog);
		em.flush();
		
		return purchaseLog;
	}
	
	public List<PurchasesLog> getPurchaseLogs(EntityManager em) throws Exception {
		List<PurchasesLog> purchasesLog = null;

		StringBuilder purchasesLogsQuery = new StringBuilder("FROM PurchasesLog");
		TypedQuery<PurchasesLog> query = em.createQuery(purchasesLogsQuery.toString(), PurchasesLog.class);
		purchasesLog = query.getResultList();

		return purchasesLog;
	}
	
	public List<PurchasesLog> getPurchaseLogs(EntityManager em, Map<String, String> searchMap) throws Exception {
		List<PurchasesLog> purchasesLog = null;
		boolean first = true;
		
		StringBuilder purchasesLogsQuery = new StringBuilder("FROM PurchasesLog");
		if(!searchMap.get("id").equals("")) {
			first = false;
			purchasesLogsQuery.append(" WHERE (");
			purchasesLogsQuery.append("(purchasesLogsId = " + searchMap.get("id") + ")");
		}
		
		if(!searchMap.get("addedDateFrom").equals("") && !searchMap.get("addedDateTo").equals("")) {
			if(!first) {
				purchasesLogsQuery.append(" AND ");
			} else {
				purchasesLogsQuery.append(" WHERE (");
			}
			first = false;
			purchasesLogsQuery.append("(createdDate >= '" + TimestampParser.TimestampStart(searchMap.get("addedDateFrom"))
				+ "' AND " + "createdDate <= '" + TimestampParser.TimestampEnd(searchMap.get("addedDateTo")) + "')");
		}
		
		/*if(!searchMap.get("addedBy").equals("")) {
			if(!first) {
				purchasesLogsQuery.append(" AND ");
			} else {
				purchasesLogsQuery.append(" WHERE (");
			}
			first = false;
			User user = userRepo.searchUserByUsername(em, searchMap.get("addedBy"));
			purchasesLogsQuery.append("(createdBy = " + user.getUserId() + ")");
		}*/
		purchasesLogsQuery.append(")");
		
		TypedQuery<PurchasesLog> query = em.createQuery(purchasesLogsQuery.toString(), PurchasesLog.class);
		purchasesLog = query.getResultList();
		
		List<PurchasesLog> temp = new ArrayList<PurchasesLog>();
		
		if(purchasesLog.size() != 0 && !searchMap.get("addedBy").equals("")) {
			try {
				User user = userRepo.searchUserByUsername(em, searchMap.get("addedBy"));
				if(user != null) {
					for(int i = 0; i < purchasesLog.size(); i++) {
						if(purchasesLog.get(i).getCreatedBy() == user.getUserId())
							temp.add(purchasesLog.get(i));
					}
				}
			} catch(Exception e) {
				
			}
			purchasesLog = temp;
		}

		return purchasesLog;
	}
	
	public PurchasesLog getPurchaseLogsById(EntityManager em, int purchasesLogsId) throws Exception {
		PurchasesLog purchasesLog = null;

		StringBuilder purchasesLogsQuery = new StringBuilder("FROM PurchasesLog WHERE purchasesLogsId = :id");
		TypedQuery<PurchasesLog> query = em.createQuery(purchasesLogsQuery.toString(), PurchasesLog.class);
		query.setParameter("id", purchasesLogsId);
		purchasesLog = query.getSingleResult();

		return purchasesLog;
	}
}
