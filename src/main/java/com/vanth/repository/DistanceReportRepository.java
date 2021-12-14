package com.vanth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vanth.entity.DistanceReport;

public interface DistanceReportRepository extends JpaRepository<DistanceReport,Integer> {

	@Query(value = "EXEC REPORTDISTANCE ?1,?2,?3", nativeQuery = true)
	List<DistanceReport> getMonthDistance(String id_vehicle, int month, int year);
}
