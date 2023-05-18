package com.learning.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learning.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("SELECT u.username, u.enabled, u.id from User u where u.username = :username")
	List<Object[]> fetchCustomer(@Param("username") String username);
}


