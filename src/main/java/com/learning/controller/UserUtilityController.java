package com.learning.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.Authority;
import com.learning.entity.CustomerInfo;
import com.learning.entity.User;
import com.learning.service.UserUtilityService;



@CrossOrigin
@RestController
@RequestMapping("/api/util")
public class UserUtilityController {
	
	@Autowired
	UserUtilityService userUtilService;
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return userUtilService.getUsers();
	}
	
	@GetMapping("/authorities")
	public List<Authority> geAuthorities() {
		return userUtilService.getAuthorities();
	}
	
	@GetMapping("/customerinfos")
	public List<CustomerInfo> getCustomerInfos() {
		return userUtilService.getAllCustomerInfo();
	}
	
	@GetMapping("/customer/{username}")
	public CustomerInfo forgetCustomerPassword(@PathVariable("username") String username) {
		
		return userUtilService.getCustomerInfo(username);
	}
	
	@PutMapping("/customer/updatepassword")
	public ResponseEntity<User> updateCustomerPassword(@RequestBody User user) {
		
		return userUtilService.updateCustomerInfo(user.getUsername(), user.getPassword());

	}
}
