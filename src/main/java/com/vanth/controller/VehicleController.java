package com.vanth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.repository.UserRepository;
import com.vanth.repository.VehicleRepository;

@RestController
public class VehicleController {
	@Autowired
	VehicleRepository repo;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/vehicle")
	public ResponseEntity<Object> getAllVehicle()
	{
		return new ResponseEntity<Object>(repo.findAll(),HttpStatus.OK);
	}
	
	
}
