package com.vanth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.entity.Tracking;
import com.vanth.entity.Vehicle;
import com.vanth.repository.TrackingRepository;
import com.vanth.repository.UserRepository;
import com.vanth.repository.VehicleRepository;
import com.vanth.request_response.DeviceInfoResponse;
import com.vanth.tcpserver.Coord;
import com.vanth.tcpserver.TCPServer;

@RestController
public class VehicleController {
	@Autowired
	VehicleRepository repo;
	
	@Autowired
	TrackingRepository trackingRepo;
	
	@GetMapping("/vehicle")
	public ResponseEntity<Object> getAllVehicle()
	{
		return new ResponseEntity<Object>(repo.findAll(),HttpStatus.OK);
	}
	
	
	@GetMapping("/vehicle-info/{id}")
	public ResponseEntity<Object> getVehicleInfo(@PathVariable String id)
	{
		if (!repo.existsById(id))
			return new ResponseEntity<Object>("102",HttpStatus.BAD_REQUEST);
		
		Vehicle vehicle = repo.getById(id);
		
		DeviceInfoResponse deviceInfoResponse = new DeviceInfoResponse();
		deviceInfoResponse.setId(id);
		deviceInfoResponse.setTitle(vehicle.getTitle());
		for (Coord coord : TCPServer.listLocation)
		{
			if (coord == null) continue;
			if (coord.getName().equalsIgnoreCase(id))
			{
				deviceInfoResponse.setState(true);
				deviceInfoResponse.setLastConnectTime("Đang kết nối");
				deviceInfoResponse.setLastX(coord.getX());
				deviceInfoResponse.setLastY(coord.getY());
				
				return new ResponseEntity<Object>(deviceInfoResponse,HttpStatus.OK);
			}
		}
		
		try
		{
			deviceInfoResponse.setState(false);
			Tracking tracking = trackingRepo.getLastTracking(id);
			
			deviceInfoResponse.setLastConnectTime(tracking.getTracktime().toString());
			deviceInfoResponse.setLastX(tracking.getX());
			deviceInfoResponse.setLastY(tracking.getY());
		}
		catch (Exception e) 
		{
			// TODO: handle exception
//			e.printStackTrace();
			deviceInfoResponse.setLastConnectTime("N/A");
			deviceInfoResponse.setLastX(-1);
			deviceInfoResponse.setLastY(-1);
		}
		
		return new ResponseEntity<Object>(deviceInfoResponse,HttpStatus.OK);
		
	}
}
