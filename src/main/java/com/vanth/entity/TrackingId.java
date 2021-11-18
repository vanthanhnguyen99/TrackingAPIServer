package com.vanth.entity;

import java.io.Serializable;

public class TrackingId implements Serializable {
	Vehicle vehicle;
	java.time.LocalDateTime tracktime;
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public java.time.LocalDateTime getTracktime() {
		return tracktime;
	}
	public void setTracktime(java.time.LocalDateTime tracktime) {
		this.tracktime = tracktime;
	}
	
	
}
