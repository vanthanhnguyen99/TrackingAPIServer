package com.vanth.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.entity.Account;
import com.vanth.entity.Users;
import com.vanth.repository.AccountRepository;
import com.vanth.repository.UserRepository;
import com.vanth.request_response.RegistAccountRequest;

@RestController
public class LoginController 
{
	@Autowired
	AccountRepository repo;
	
	@Autowired
	UserRepository repoUsers;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Account account)
	{
		 int res = repo.login(account.getUsername(), account.getPassword());
			return new ResponseEntity<Object>(res,HttpStatus.OK);
				
		
	}
	
	@PostMapping("/regist")
	public ResponseEntity<Object> registAccount(@RequestBody RegistAccountRequest registAccountRequest)
	{
		if (repo.existsById(registAccountRequest.getUsername()))
		{
			return new ResponseEntity<Object>("101", HttpStatus.BAD_REQUEST);
		}
			
		
		Users users = new Users();
		Account account = new Account();
		
		account.setUsername(registAccountRequest.getUsername());
		account.setPassword(registAccountRequest.getPassword());
			
		users.setName(registAccountRequest.getName());
		users.setGender(registAccountRequest.isGender());
		users.setPhone(registAccountRequest.getPhone());
		users.setEmail(registAccountRequest.getEmail());
		users.setAccount(account);
		
		// save data
		try
		{
			System.out.println("sasdjajsdajd:" + registAccountRequest.getUsername());
			repo.save(account);
			repoUsers.save(users);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Object>("200",HttpStatus.OK);
	}
}