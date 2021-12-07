package com.vanth.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.sun.istack.NotNull;
import com.vanth.entity.ScheduleReport;
import com.vanth.entity.Vehicle;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String>  {
	@Query(value = "INSERT INTO SCHEDULE(VEHICLE_ID, START_TIME,START_X,START_Y,FINISH_TIME,FINISH_X,FINISH_Y,STATUS,FINISH) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9)", nativeQuery = true)
	void insertSchedule(@NotNull String vehicle_id,@NotNull LocalDateTime start_time,@NotNull double start_x, @NotNull double start_y, @NotNull LocalDateTime finish_time, @NotNull double finish_x, @NotNull double finish_y, @NotNull int status, @Nullable LocalDateTime finish );
	
	
}
