package com.vanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vanth.entity.Tracking;
import com.vanth.entity.TrackingId;

public interface TrackingRepository extends JpaRepository<Tracking, TrackingId> {
	@Query(value = "SELECT TOP(1) * FROM TRACKING WHERE ID_VEHICLE = ?1", nativeQuery = true)
	public Tracking getLastTracking(String id_vehile); 
}
