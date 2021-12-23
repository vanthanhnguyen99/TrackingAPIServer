package com.vanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vanth.entity.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {
	@Query(value = "SELECT TOP 1 ID  FROM USERS WHERE USERNAME = ?1", nativeQuery = true)
	int getIdUserFromUsername(String username);
}
