package com.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.Account;
import com.learning.entity.Authority;
import com.learning.entity.User;
import com.learning.service.StaffManagementService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class StaffManagementController {


	@Autowired
	StaffManagementService staffManagementService;


	@GetMapping("admin/staff")
	public List<User> getStaff( ) {

		return staffManagementService.getStaffUsers();
	}
	
	@GetMapping("/staff/customer")
	public List<User> getCustomer( ) {

		return staffManagementService.getCustomerUsers();
	}

	@PutMapping("admin/staff")
	public ResponseEntity<User> enableOrDisableStaff(@RequestBody User user) {

		return staffManagementService.enableOrDisableUserStaff(user.getUsername(), user.isEnabled());
	}

	@GetMapping("/staff/account/detail/{accountNumber}")
	public Account getAccountDetail(@PathVariable("accountNumber") long accountNumber) {
		return staffManagementService.getAccountDetail(accountNumber);
	}

}