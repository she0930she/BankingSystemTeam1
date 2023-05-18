package com.learning.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learning.entity.CustomerInfo;
import com.learning.entity.User;
import com.learning.service.CustomerManagementService;
import com.learning.util.JwtVerify;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CustomerManagementController {

	@Autowired
	CustomerManagementService customerManagementService;

	@PutMapping("/customer/{username}")
	public ResponseEntity<CustomerInfo> updateCustomer(@PathVariable("username") String username,
													   @RequestBody CustomerInfo newCustomerInfo, HttpServletRequest httpServletRequest) {

		JwtVerify.jwtVerify(httpServletRequest, username);
		return customerManagementService.updateCustomerInfo(username, newCustomerInfo);

	}

	@GetMapping("/customer/{username}")
	public CustomerInfo getCustomer(@PathVariable("username") String username,
									HttpServletRequest httpServletRequest) {

		JwtVerify.jwtVerify(httpServletRequest, username);
		return customerManagementService.getCustomerInfo(username);
	}

	@PutMapping("/staff/customer")
	public ResponseEntity<User> enableOrDisableCustomer(
			@RequestBody User newUser) {
		return customerManagementService.customerEnableOrDisable(newUser);

	}

	@GetMapping("/staff/customer/{username}")
	public User getCustomerWithUsername(@PathVariable("username") String username) {
		return customerManagementService.getCustomer(username);
	}

}