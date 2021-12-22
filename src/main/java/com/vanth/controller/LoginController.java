package com.vanth.controller;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.jwt.configs.MyUserDetailsService;
import com.vanth.jwt.configs.JwtTokenProvider;
import com.vanth.jwt.configs.CustomUserDetail;
import com.vanth.entity.Account;
import com.vanth.entity.Users;
import com.vanth.repository.AccountRepository;
import com.vanth.repository.UserRepository;
import com.vanth.request_response.RegistAccountRequest;

@RestController
@CrossOrigin
public class LoginController 
{
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	AccountRepository repo;
	
	@Autowired
	UserRepository repoUsers;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Account account)
	{
		
		try
		{
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			return new ResponseEntity<Object>("",HttpStatus.OK);
		}
		CustomUserDetail userDetails = (CustomUserDetail) userDetailsService.loadUserByUsername(account.getUsername());
		String jwt = jwtTokenProvider.generateToken(userDetails);
		return new ResponseEntity<Object>(jwt,HttpStatus.OK);
		
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