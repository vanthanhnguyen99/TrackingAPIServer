package com.vanth.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.DTO.ScheduleDTO;
import com.vanth.DTO.VehicleDTO;
import com.vanth.converter.ScheduleConverter;
import com.vanth.converter.VehicleConverter;
import com.vanth.entity.DistanceReport;
import com.vanth.entity.Schedule;
import com.vanth.entity.ScheduleReport;
import com.vanth.entity.Tracking;
import com.vanth.entity.Vehicle;
import com.vanth.repository.DistanceReportRepository;
import com.vanth.repository.ScheduleReportRepository;
import com.vanth.repository.ScheduleRepository;
import com.vanth.repository.TrackingRepository;
import com.vanth.repository.UserRepository;
import com.vanth.repository.VehicleRepository;
import com.vanth.request_response.DeviceInfoResponse;
import com.vanth.request_response.DistanceRequestEntity;
import com.vanth.tcpserver.Coord;
import com.vanth.tcpserver.DistanceRequest;
import com.vanth.tcpserver.NotifyTimeTask;
import com.vanth.tcpserver.TCPServer;

@RestController
@CrossOrigin
public class VehicleController {
	@Autowired
	VehicleRepository repo;
	
	@Autowired
	TrackingRepository trackingRepo;
	
	@Autowired
	ScheduleRepository scheduleRepo;
	
	@Autowired
	ScheduleReportRepository scheduleReportRepo;
	
	@Autowired
	DistanceReportRepository distanceReportRepo;
	
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
	
	@GetMapping("/schedule/{id_vehicle}")
	public ResponseEntity<Object> getListVehicleSchedule(@PathVariable String id_vehicle)
	{
		if (!repo.existsById(id_vehicle))
			return new ResponseEntity<Object>("102",HttpStatus.BAD_REQUEST);
		
		System.out.println("id = " + id_vehicle);
		
		try
		{
			List<Schedule> list = scheduleRepo.getVehicleSchedule(id_vehicle);
			
			List<ScheduleDTO> dtos = new ArrayList<ScheduleDTO>();
			for (Schedule schedule:list)
			{
				dtos.add(ScheduleConverter.convertScheduleToScheduleDTO(schedule));
			}
			
			return new ResponseEntity<Object>(dtos,HttpStatus.OK);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/add-schedule")
	public ResponseEntity<Object> addVehicleSchedule(@RequestBody ScheduleDTO scheduleDTO)
	{
		try
		{
			if (!repo.existsById(scheduleDTO.getVehicle_id()))
				return new ResponseEntity<Object>("102",HttpStatus.BAD_REQUEST);
			
//			Vehicle vehicle = repo.getById(scheduleDTO.getVehicle_id());
//			
//			Schedule schedule = ScheduleConverter.convertScheduleDTOToSchedule(scheduleDTO);
//			schedule.setVehicle(vehicle);
//			System.out.println(schedule.getVehicle().getId());
			
			String vehicle_id = scheduleDTO.getVehicle_id();
			LocalDateTime start_time = scheduleDTO.getStart_time();
			double start_x = scheduleDTO.getStart_x();
			double start_y = scheduleDTO.getStart_y();
			LocalDateTime finish_time = scheduleDTO.getFinish_time();
			double finish_x = scheduleDTO.getFinish_x();
			double finish_y = scheduleDTO.getFinish_y();
			int status = scheduleDTO.getStatus();
			LocalDateTime finish = scheduleDTO.getFinish();
			
			repo.insertSchedule(vehicle_id, start_time, start_x, start_y, finish_time, finish_x, finish_y, status, finish);
			
			if (NotifyTimeTask.current.getStartTime().isAfter(start_time))
			{
				NotifyTimeTask.timer.cancel();
				
				
				Schedule schedule = new Schedule();
				Vehicle vehicle = new Vehicle();
				vehicle.setId(vehicle_id);
				schedule.setVehicle(vehicle);
				schedule.setStartTime(start_time);
				NotifyTimeTask.current = schedule;
				
				Timestamp timestamp = Timestamp.valueOf(schedule.getStartTime());
				Date date = new Date(timestamp.getTime());
				
				NotifyTimeTask.timer.schedule(new NotifyTimeTask(), date);
			}
			
			return new ResponseEntity<Object>("200",HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			if (e.getMessage().toString().equalsIgnoreCase("Schedule not acceptable"))
				return new ResponseEntity<Object>("104",HttpStatus.NOT_ACCEPTABLE);
			e.printStackTrace();
		}
		return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/schedule-report/{id}")
	public ResponseEntity<Object> getScheduleVehicleReport(@PathVariable String id)
	{
		if (!repo.existsById(id))
			return new ResponseEntity<Object>("102",HttpStatus.BAD_REQUEST);
		
		try
		{
			List<ScheduleReport> list = scheduleReportRepo.getScheduleReport(id);
			return new ResponseEntity<Object>(list,HttpStatus.OK);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/add-vehicle")
	public ResponseEntity<Object> addVehicle(@RequestBody VehicleDTO vehicleDTO)
	{
		try
		{
			Vehicle vehicle = VehicleConverter.convertVehicleDTOToVehicle(vehicleDTO);
			
			System.out.println(vehicle.getId());
			if (repo.existsById(vehicle.getId()))
				return new ResponseEntity<Object>("101",HttpStatus.BAD_REQUEST);
			
			System.out.println("test");
			repo.save(vehicle);
			return new ResponseEntity<Object>("200",HttpStatus.OK);
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@PostMapping("/distance-report")
	public ResponseEntity<Object> getDistanceReport(@RequestBody DistanceRequestEntity distanceRequestEntity)
	{
		try 
		{
			if (!repo.existsById(distanceRequestEntity.getId_vehicle()))
				return new ResponseEntity<Object>("101", HttpStatus.BAD_REQUEST);
			
			List<DistanceReport> distanceReports = distanceReportRepo.getMonthDistance(distanceRequestEntity.getId_vehicle(), distanceRequestEntity.getMonth(),distanceRequestEntity.getYear());
			
			return new ResponseEntity<Object>(distanceReports,HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<Object>("103",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
