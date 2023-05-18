package com.learning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learning.entity.CustomerInfo;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, String>{
	
	@Query("SELECT c.accountList from CustomerInfo c where c.username = :username")
	List<Object[]> fetchCustomerAccounts(@Param("username") String username);
	

}
