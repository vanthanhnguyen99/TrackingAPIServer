package com.vanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vanth.entity.Tracking;
import com.vanth.entity.Tracking.TrackingId;

public interface TrackingRepository extends JpaRepository<Tracking, TrackingId> {

}
