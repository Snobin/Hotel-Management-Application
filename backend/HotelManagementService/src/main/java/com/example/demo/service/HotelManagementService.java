package com.example.demo.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.ManagementDto;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.entity.ManagementEntity;
import com.example.demo.entity.ManagementPk;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.exception.RecordUpdateException;

public interface HotelManagementService {

	JSONObject getData(String param);

	ServiceResponse createUser(ManagementDto obj);

	ResponseEntity<ManagementEntity> getById(ManagementPk obj);

	ServiceResponse updateManagement(ManagementDto obj) throws RecordUpdateException;

	ServiceResponse deleteManagement(ManagementPk obj)  throws RecordNotFoundException;
	
	ServiceResponse verify(ManagementPk compositeKey) throws RecordNotFoundException;

}
