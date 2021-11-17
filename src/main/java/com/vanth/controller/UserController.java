package com.vanth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.entity.Users;
import com.vanth.entity.Vehicle;
import com.vanth.repository.UserRepository;
import com.vanth.request.VehicleRequestHome;
import com.vanth.tcpserver.Coord;
import com.vanth.tcpserver.TCPServer;

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
			
			List<VehicleRequestHome> vehicleRequestHomes = new ArrayList<VehicleRequestHome>();
			
			for (Vehicle vehicle:user.getVehicle())
			{
				VehicleRequestHome vehicleRequestHome = new VehicleRequestHome();
				vehicleRequestHome.setId(vehicle.getId());
				vehicleRequestHome.setTitle(vehicle.getTitle());
				vehicleRequestHome.setUser_id(vehicle.getUsers().getId());
				vehicleRequestHome.setDelayTime(vehicle.getDelayTime());
				vehicleRequestHome.setisOnline(false);
				
				for (Coord coord:TCPServer.listLocation)
				{
					if (coord == null) continue;
					if (coord.getName().trim().equalsIgnoreCase(vehicle.getId()))
					{
						vehicleRequestHome.setisOnline(true);
						System.out.println("hello");
						break;
					}
				}
				
				vehicleRequestHomes.add(vehicleRequestHome);
			}
			
			return new ResponseEntity<Object>(vehicleRequestHomes,HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}	
		
		return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}