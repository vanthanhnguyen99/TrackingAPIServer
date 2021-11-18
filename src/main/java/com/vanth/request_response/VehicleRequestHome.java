package com.vanth.request_response;

import com.vanth.entity.Users;

public class VehicleRequestHome {
	private String id;
    private String title;
    private int delayTime;
    private int user_id;
    private boolean isOnline;
    
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
	public int getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public boolean getisOnline() {
		return isOnline;
	}
	public void setisOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
    
    
}
