package com.vanth.DTO;

public class VehicleDTO {
	 	String id;
	    String tilte;
	    int id_user;
	    int delay_time;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getTilte() {
	        return tilte;
	    }

	    public void setTilte(String tilte) {
	        this.tilte = tilte;
	    }

	    public int getId_user() {
	        return id_user;
	    }

	    public void setId_user(int id_user) {
	        this.id_user = id_user;
	    }

	    public int getDelay_time() {
	        return delay_time;
	    }

	    public void setDelay_time(int delay_time) {
	        this.delay_time = delay_time;
	    }
}
