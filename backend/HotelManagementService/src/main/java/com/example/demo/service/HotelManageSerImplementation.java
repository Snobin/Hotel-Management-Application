package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ManagementDto;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.entity.ManagementEntity;
import com.example.demo.entity.ManagementPk;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.exception.RecordUpdateException;
import com.example.demo.repository.HotelManagementRepository;
import com.example.demo.repository.specifiation.ManagementSpec;
import com.example.demo.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class HotelManageSerImplementation implements HotelManagementService{
	
	@Autowired
	private HotelManagementRepository managementRepository;
	@Autowired
	MessageSource messageSource;
	
	private static Logger logger = LogManager.getLogger(HotelManageSerImplementation.class);

	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getData(String param) {
//	    System.out.println("service reached");

	    JSONObject result = new JSONObject();
	    Specification<ManagementEntity> spec = ManagementSpec.getManagementSpec(param);
//	    System.out.println("spec" + spec.toString()); // Assuming you have a custom specification class for ManagementEntity
	    
	    List<ManagementEntity> managementEntities = new ArrayList<>();
	    JSONArray countByStatus = new JSONArray();

	    try {
	        managementEntities = managementRepository.findAll(spec);
	        countByStatus = countByStatus(spec);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    JSONArray array = new JSONArray();
	    for (ManagementEntity entity : managementEntities) {
	        JSONObject obj = new JSONObject();
	        ManagementPk pk = entity.getManagepkobj();
	        obj.put("id", pk.getId());
	        obj. put("name", pk.getName());
	        obj.put("address", entity.getAddress());
	        obj.put("phone_number", entity.getPhoneNumber());
	        obj.put("date", entity.getDate());
	        obj.put("ac_or_non_ac", entity.getAcOrNonAc());
	        obj.put("age", entity.getAge());
	        obj.put("status", entity.getStatus());
	        array.add(obj);
	    }

	    result.put("aaData", array);
	    result.put("countByStatus", countByStatus);

	    return result;
	}

	@SuppressWarnings({  "unchecked" })
	private JSONArray countByStatus(Specification<ManagementEntity> spec) {
	    JSONArray array = new JSONArray();
	    try {
	        List<ManagementEntity> managementList = managementRepository.findAll(spec);
	        Map<String, Long> countByStatus = managementList.stream()
	                .collect(Collectors.groupingBy(ManagementEntity::getStatus, Collectors.counting()));

	        for (String status : countByStatus.keySet()) {
	            JSONObject obj = new JSONObject();
	            obj.put("name", status);
	            obj.put("count", countByStatus.get(status));
	            array.add(obj);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return array;
	}

	
	@Override
	public ServiceResponse createUser(ManagementDto obj) {
		System.out.println("createuser");
	    try {
//	    	System.out.println(obj.toString()+"anc");
	        ManagementPk managementPk = new ManagementPk();
	        managementPk.setId(obj.getId());
	        managementPk.setName(obj.getName());

	        ManagementEntity managementEntity = new ManagementEntity();
	        managementEntity.setAddress(obj.getAddress());
	        managementEntity.setPhoneNumber(obj.getPhoneNumber());
	        managementEntity.setDate(obj.getLogdate());
	        managementEntity.setAcOrNonAc(obj.getAcOrNonAc());
	        managementEntity.setAge(obj.getAge());
	        managementEntity.setStatus("PROCESSD");
	        managementEntity.setCreatedBy(getUsername());
	        managementEntity.setVerifiedBy(null);
	        managementEntity.setModifiedBy(getUsername());
	        managementEntity.setVerifiedDate(null);
	        managementEntity.setCreatedDate(new Date());
	        managementEntity.setModifiedDate(new Date());
	        managementEntity.setManagepkobj(managementPk);

	        managementRepository.save(managementEntity);

	        return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, "Record Created Successfully", null);
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, "An error occurred while creating the user.", null);
	    }
	}

	
	@Override
	public ResponseEntity<ManagementEntity> getById(ManagementPk obj) {
	    try {
	        Optional<ManagementEntity> managementEntity = managementRepository.findById(obj);

	        if (!managementEntity.isPresent()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        return new ResponseEntity<>(managementEntity.get(), HttpStatus.ACCEPTED);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@Override
	public ServiceResponse updateManagement(ManagementDto dtoobj) throws RecordUpdateException {
	    ManagementPk compositeKey = new ManagementPk();
	    compositeKey.setId(dtoobj.getId());
	    compositeKey.setName(dtoobj.getName());

	    Optional<ManagementEntity> optionalManagementEntity = managementRepository.findById(compositeKey);
	    
	    if (!optionalManagementEntity.isPresent()) {
	        throw new RecordNotFoundException("No Record with with Key");
	    }
	    
	    ManagementEntity existingManagementEntity = optionalManagementEntity.get();
	    
	    try {
	        existingManagementEntity.setAddress(dtoobj.getAddress());
	        existingManagementEntity.setPhoneNumber(dtoobj.getPhoneNumber());
//	        existingManagementEntity.setDate(dtoobj.getLogdate());
	        existingManagementEntity.setAcOrNonAc(dtoobj.getAcOrNonAc());
	        existingManagementEntity.setAge(dtoobj.getAge());
	        existingManagementEntity.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
	        existingManagementEntity.setModifiedBy(getUsername());
	        existingManagementEntity.setModifiedDate(new Date());

	        managementRepository.save(existingManagementEntity);

	        return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
	            "Updated Successfully", null);
	    } catch (RecordUpdateException e) {
	        throw e;
	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage(), e);
	        return new ServiceResponse("Error","An error occurred while updating the record.", null);
	    }
	}



	@Override
	public ServiceResponse deleteManagement(ManagementPk compositeKey) {
	    try {
	        Optional<ManagementEntity> optionalManagementEntity = managementRepository.findById(compositeKey);

	        if (optionalManagementEntity.isPresent()) {
	            ManagementEntity managementEntity = optionalManagementEntity.get();
	            managementEntity.setStatus(Constants.MESSAGE_STATUS.DELETED);
	            managementRepository.save(managementEntity);
	            return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, "Deleted successfully", null);
	        } else {
	            return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, "An error occurred while deleting the record.", null);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, "An error occurred", null);
	    }
	}

	
	@Override
	public ServiceResponse verify(ManagementPk compositeKey) {
	    try {
	        String id = compositeKey.getId();
	        Optional<ManagementEntity> optionalManagementEntity = managementRepository.findById(compositeKey);

	        if (!optionalManagementEntity.isPresent()) {
	            throw new RecordNotFoundException("No Record Found");
	        } else {
	            ManagementEntity managementEntity = optionalManagementEntity.get();

	            if (managementEntity.getStatus().contentEquals(Constants.MESSAGE_STATUS.VERIFIED)) {
	                return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, "Record Already Verified", null);
	            }

	            if (managementEntity.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
	                return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, "Deleted Recoed Cannot Verify", null);
	            }

	            if (managementEntity.getModifiedBy().equalsIgnoreCase(getUsername())) {
	                return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, "Created user cant Verify", null);
	            }

	            managementEntity.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
	            managementEntity.setVerifiedBy(getUsername());
	            managementEntity.setVerifiedDate(new Date());
	            managementEntity.setModifiedBy(getUsername());
	            managementEntity.setModifiedDate(new Date());
	            managementRepository.save(managementEntity);
	        }
	        return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, "Record Verified Successfully", null);
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, "An error occurred", null);
	    }
	}






	public String getUsername() {
		return "manager";
	}

}
