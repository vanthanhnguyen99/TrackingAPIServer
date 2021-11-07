package com.vanth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.entity.Account;
import com.vanth.repository.AccountRepository;

@RestController
public class LoginController 
{
	@Autowired
	AccountRepository repo;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Account account)
	{
		
		return new ResponseEntity<Object>("Success",HttpStatus.OK);
	}
}