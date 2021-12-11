package com.vanth.tcpserver;

public class DistanceRequest {
	CoordRequest origins;
	CoordRequest destinations;
	
	public CoordRequest getOrigins() {
		return origins;
	}
	public void setOrigins(CoordRequest origins) {
		this.origins = origins;
	}
	public CoordRequest getDestinations() {
		return destinations;
	}
	public void setDestinations(CoordRequest destinations) {
		this.destinations = destinations;
	}
	
	public DistanceRequest(CoordRequest start, CoordRequest finish)
	{
		this.origins = start;
		this.destinations = finish;
	}
	
}
