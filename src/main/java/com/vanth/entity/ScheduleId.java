package com.vanth.entity;

import java.io.Serializable;

public class ScheduleId implements Serializable {
	 Vehicle vehicle;
     java.time.LocalDateTime startTime;
     
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public java.time.LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(java.time.LocalDateTime startTime) {
		this.startTime = startTime;
	}
}
