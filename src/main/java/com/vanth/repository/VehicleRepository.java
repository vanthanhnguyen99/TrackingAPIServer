package com.vanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vanth.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,String> {

}
