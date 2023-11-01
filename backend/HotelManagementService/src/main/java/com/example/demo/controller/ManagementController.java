package com.example.demo.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ManagementDto;
import com.example.demo.entity.ManagementPk;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.service.HotelManagementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hotelmanagement")
public class ManagementController {

	@Autowired
	private HotelManagementService managementService;

	@GetMapping
	public ResponseEntity<JSONObject> getdata(@RequestParam("searchParam") String searchParam) {
//		System.out.println("searchparam=" + searchParam);

		JSONObject data = managementService.getData(searchParam);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createManagement(@Valid @RequestBody ManagementDto obj) {
//		System.out.println("obj.tostring" + obj.toString());
		return new ResponseEntity<>(managementService.createUser(obj), HttpStatus.ACCEPTED);
	}

	@PostMapping("/byid")
	public ResponseEntity<?> getManagementById(@RequestBody ManagementPk obj) {
		return new ResponseEntity<>(managementService.getById(obj), HttpStatus.ACCEPTED);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateManagement(@Valid @RequestBody ManagementDto obj) {
		return new ResponseEntity<>(managementService.updateManagement(obj), HttpStatus.ACCEPTED);
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteManagement(@RequestBody ManagementPk obj) {
//		System.out.println("hello");
		return new ResponseEntity<>(managementService.deleteManagement(obj), HttpStatus.ACCEPTED);
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody ManagementPk compositeKey) throws RecordNotFoundException {
		return new ResponseEntity<>(managementService.verify(compositeKey), HttpStatus.ACCEPTED);
	}

}
