package com.example.demo.repository.specifiation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.ManagementEntity;
import com.example.demo.util.Constants;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ManagementSpec { 
	@SuppressWarnings("serial")
	public static Specification<ManagementEntity> getManagementSpec(String searchParam) {
		return new Specification<ManagementEntity>() {
			@SuppressWarnings({ "unused", "deprecation" })
			@Override
			public Predicate toPredicate(Root<ManagementEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				System.out.println("search"+searchParam);
				try {
					System.out.println("efgdbgd"+searchParam);
					searchObject = (JSONObject) parser.parse(searchParam);
					System.out.println("searchObject: " + searchObject); // Add this line for debugging
					String id = (String) searchObject.get(Constants.USER_ID);
					String name = (String) searchObject.get(Constants.USER_NAME);
					String date=(String) searchObject.get(Constants.USER_DATE);
					String status = (String) searchObject.get(Constants.HOTEL_STATUS);
		            
					if(!StringUtils.isEmpty(status)) {
		            	Predicate statusPredicate = criteriaBuilder.equal(root.get(Constants.HOTEL_STATUS), status);
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(statusPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(statusPredicate);
		            	}
		            	
		            }
		            
		            if(!StringUtils.isEmpty(name)) {
		            	Predicate userNamePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("managepkobj").get("name")),"%"+name.toUpperCase()+"%");
		            	
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, userNamePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(userNamePredicate);
		            	}
		            	
		            }

		            if(!StringUtils.isEmpty(date)) {
		            	Predicate userTypePredicate = criteriaBuilder.equal(root.get(Constants.USER_DATE), date);
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, userTypePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(userTypePredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(id)) {
		            	Predicate userIdPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("managepkobj").get("id")),"%"+id.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, userIdPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(userIdPredicate);
		            	}
		            }
		            
		            Order proTimeOrder = criteriaBuilder.desc(root.get("modifiedDate"));
		            query.orderBy(proTimeOrder);
		            
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            
				return finalPredicate;
			}
		};
	}

	@Override
	public String toString() {
		return "SecurityUserSpec [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
