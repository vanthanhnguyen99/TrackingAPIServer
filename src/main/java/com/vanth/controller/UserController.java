package com.vanth.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.entity.Users;
import com.vanth.repository.UserRepository;

import net.bytebuddy.asm.Advice.Return;

@RestController
public class UserController {
	@Autowired
	UserRepository repo;
	
	@GetMapping("/user")
	public ResponseEntity<Object> getAllUsers()
	{
		return new ResponseEntity<Object>(repo.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/vehicle-user/{id_user}")
	public ResponseEntity<Object> getUserVehicle(@PathVariable int id_user)
	{
		try
		{
			if (!repo.existsById(id_user) ) return new ResponseEntity<Object>("102",HttpStatus.BAD_REQUEST);
			
			Users user =  repo.getById(id_user);
			
			return new ResponseEntity<Object>(user.getVehicle(),HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}	
		
		return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
