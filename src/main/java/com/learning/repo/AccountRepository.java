package com.learning.repo;

import org.springframework.stereotype.Repository;



import com.learning.entity.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	@Query("SELECT a.accountNumber, a.accountType, a.balance, a.creationDate, a.enabled, "
			+ "a.customerInfo from Account a "
			+ "where a.enabled = 0")
	List<Object[]> fetchAccountsToBeApproved();
	

}
