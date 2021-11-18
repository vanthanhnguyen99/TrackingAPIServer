package com.vanth.request_response;

public class DeviceInfoResponse {
	String id;
	String title;
	boolean state; // online or offline
	String lastConnectTime;
	double lastX;
	double lastY;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getLastConnectTime() {
		return lastConnectTime;
	}
	public void setLastConnectTime(String lastConnectTime) {
		this.lastConnectTime = lastConnectTime;
	}
	public double getLastX() {
		return lastX;
	}
	public void setLastX(double lastX) {
		this.lastX = lastX;
	}
	public double getLastY() {
		return lastY;
	}
	public void setLastY(double lastY) {
		this.lastY = lastY;
	}
	
	
}
