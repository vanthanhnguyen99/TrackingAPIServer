package com.vanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vanth.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> 
{
	@Query(value = "EXEC SP_LOGIN ?1,?2", nativeQuery = true)
	int login(String username, String password);
}
