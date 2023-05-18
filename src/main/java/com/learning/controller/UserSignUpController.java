package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.User;
import com.learning.service.CustomerService;
import com.learning.service.StaffService;
import com.learning.service.SuperAdminService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserSignUpController {
	
	SuperAdminService superAdminService;
	CustomerService customerService;
	StaffService staffService;
	
	@Autowired
	public UserSignUpController(SuperAdminService superAdminService, CustomerService customerService,
			StaffService staffService) {
		super();
		this.superAdminService = superAdminService;
		this.customerService = customerService;
		this.staffService = staffService;
	}

	@PostMapping("/admin/register")
	public ResponseEntity<String> adminSignUp(@RequestBody User user) {
		String userInfo = superAdminService.signUp(user).toString();
		return new ResponseEntity<String>(userInfo, HttpStatus.CREATED);
	}
	
	@PostMapping("/customer/register")
	public ResponseEntity<String> customerSignUp(@RequestBody User user) {
		String userInfo = customerService.signUp(user).toString();
		return new ResponseEntity<String>(userInfo, HttpStatus.CREATED);
	}
	
	@PostMapping("/admin/staff")
	public ResponseEntity<String> createStaff(@RequestBody User user) {
		String userInfo = staffService.createStaff(user).toString();
		return new ResponseEntity<String>(userInfo, HttpStatus.CREATED);
	}
}
