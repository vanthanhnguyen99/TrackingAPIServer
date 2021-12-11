package com.vanth.converter;

import com.vanth.DTO.VehicleDTO;
import com.vanth.entity.Users;
import com.vanth.entity.Vehicle;

public class VehicleConverter {
	public static Vehicle convertVehicleDTOToVehicle(VehicleDTO vehicleDTO)
	{
		Vehicle vehicle = new Vehicle();
		vehicle.setId(vehicleDTO.getId());
		vehicle.setTitle(vehicleDTO.getTilte());
		vehicle.setDelayTime(vehicleDTO.getDelay_time());
		Users users = new Users();
		users.setId(vehicleDTO.getId_user());
		vehicle.setUsers(users);
		
		return vehicle;
	}
}
