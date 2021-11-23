package com.vanth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vanth.entity.Schedule;
import com.vanth.entity.ScheduleId;

public interface ScheduleRepository extends JpaRepository<Schedule,ScheduleId> {
	@Query(value = "SELECT * FROM SCHEDULE WHERE VEHICLE_ID = ?1", nativeQuery = true)
	List<Schedule> getVehicleSchedule(String vehicle_id);
}
