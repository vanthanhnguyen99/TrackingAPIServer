package com.vanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanth.entity.Vehicle;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String>  {

}
