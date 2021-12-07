package com.vanth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vanth.entity.ScheduleReport;

public interface ScheduleReportRepository extends JpaRepository<ScheduleReport,Integer> {
	@Query(value = "EXEC REPORTSCHEDULE ?1", nativeQuery = true)
	List<ScheduleReport>  getScheduleReport(String id_vehicle);

}
