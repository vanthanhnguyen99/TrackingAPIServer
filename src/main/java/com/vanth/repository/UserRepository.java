package com.vanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vanth.entity.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {

}
