package com.learning.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.learning.entity.Account;
import com.learning.entity.Authority;
import com.learning.entity.User;
import com.learning.exception.AccountNotExistException;
import com.learning.exception.UserNotExistException;
import com.learning.repo.AccountRepository;
import com.learning.repo.UserRepository;




@Service
public class StaffManagementService {

	@Autowired
	UserUtilityService userUtilityService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	AccountRepository accRepo;





	// get all STAFF users
	public List<User> getStaffUsers(){

		List<Authority> staffsInAuth = listAuthorityStaffs();
		List<User> staffUsers = getStaffUserList(staffsInAuth);

		return staffUsers;
	}
	
	// get all Customer users
		public List<User> getCustomerUsers(){

			List<Authority> customersInAuth = listAuthorityCustomers();
			List<User> customerUsers = getStaffUserList(customersInAuth);

			return customerUsers;
		}


	// get list of authority as STAFF
	public List<Authority> listAuthorityStaffs(){

		// get all authorities from DB
		List<Authority> allAuthorities = userUtilityService.getAuthorities();

		// put STAFF in a list
		List<Authority> staffsInAuth = new ArrayList<>();
		for (Authority user: allAuthorities) {

			// get authority is STAFF
			if (user.getAuthority().equals("STAFF")) {
				staffsInAuth.add(user);
			}
		}

		return staffsInAuth;
	}
	
	// get list of authority as Customer
		public List<Authority> listAuthorityCustomers(){

			// get all authorities from DB
			List<Authority> allAuthorities = userUtilityService.getAuthorities();

			// put STAFF in a list
			List<Authority> staffsInAuth = new ArrayList<>();
			for (Authority user: allAuthorities) {

				// get authority is STAFF
				if (user.getAuthority().equals("CUSTOMER")) {
					staffsInAuth.add(user);
				}
			}

			return staffsInAuth;
		}

	// get only staff users
	public List<User> getStaffUserList(List<Authority> staffs){

		List<User> staffUsers = new ArrayList<>();

		for (Authority user: staffs) {
			String username = user.getUsername(); // get id from authority

			// if username in users, add in list
			Optional<User> optional = userRepo.findById(username);
			if (!optional.isPresent()) {
				continue;
			}
			User staffUser = optional.get();
			staffUsers.add(staffUser);

		}

		return staffUsers;
	}


	// enable or disable a staff
	public ResponseEntity<User> enableOrDisableUserStaff(String username, boolean enableOrDisable) {

		Optional<User> optional = userRepo.findById(username);
		if (!optional.isPresent()) {
			throw new UserNotExistException("User does not exist");
		}

		User userStaff = optional.get();
		userStaff.setEnabled(enableOrDisable);

		return new ResponseEntity<>(userRepo.save(userStaff), HttpStatus.OK);

	}


	// get Account detail
	public Account getAccountDetail(long accountNumber) {
		Optional<Account> optional = accRepo.findById(accountNumber);
		if (!optional.isPresent()) {
			throw new AccountNotExistException("account does not exist");
		}

		Account account = optional.get();

		return account;
	}

}