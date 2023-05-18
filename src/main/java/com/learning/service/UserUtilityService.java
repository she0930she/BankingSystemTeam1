package com.learning.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.learning.entity.Authority;
import com.learning.entity.CustomerInfo;
import com.learning.entity.User;
import com.learning.exception.UserNotExistException;
import com.learning.repo.AuthorityRepository;
import com.learning.repo.CustomerInfoRepository;
import com.learning.repo.UserRepository;



@Service
public class UserUtilityService {

	UserRepository userRepo;
	AuthorityRepository authRepo;
	CustomerInfoRepository customerInfoRepo;
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public UserUtilityService(UserRepository userRepo, AuthorityRepository authRepo,
			CustomerInfoRepository customerInfoRepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.authRepo = authRepo;
		this.customerInfoRepo = customerInfoRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> getUsers() {
		return userRepo.findAll();
	}
	
	public List<Authority> getAuthorities() {
		return authRepo.findAll();
	}
	
	public List<CustomerInfo> getAllCustomerInfo() {
		return customerInfoRepo.findAll();
	}
	
	public CustomerInfo getCustomerInfo(@PathVariable("username") String username) {

		Optional<CustomerInfo> optional = customerInfoRepo.findById(username);
		if (!optional.isPresent()) {
			throw new UserNotExistException("User does not exist");
		}
		return optional.get();

	}
	
	public ResponseEntity<User> updateCustomerInfo(String username, 
			String password) {
		//Optional<CustomerInfo> existingCust = customerInfoRepo.findById(username); 
		Optional<User> existingUser = userRepo.findById(username);

		if (existingUser.isPresent()) { 

			//CustomerInfo tempCust = existingCust.get();
			User tempUser = existingUser.get();
			
			tempUser.setPassword(passwordEncoder.encode(password));

			return new ResponseEntity<>(userRepo.save(tempUser), HttpStatus.OK);

		} else {
			throw new UserNotExistException("Data Not there");

		}
	}
}
