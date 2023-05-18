package com.learning.service;

import java.util.Optional;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.learning.entity.CustomerInfo;
import com.learning.entity.User;
import com.learning.exception.UserNotExistException;
import com.learning.repo.CustomerInfoRepository;
import com.learning.repo.UserRepository;

@Service
public class CustomerManagementService {

	UserRepository userRepo;
	CustomerInfoRepository customerInfoRepo;

	@Autowired
	public CustomerManagementService(UserRepository userRepo,
									 CustomerInfoRepository customerInfoRepo) {
		super();
		this.userRepo = userRepo;
		this.customerInfoRepo = customerInfoRepo;
	}

	//Updating the CustomerInfo table
	public ResponseEntity<CustomerInfo> updateCustomerInfo(@PathVariable("username") String username,
														   @RequestBody CustomerInfo newCustomerInfo) {
		Optional<CustomerInfo> existingCust = customerInfoRepo.findById(username);
		Optional<User> existingUser = userRepo.findById(username);

		if (existingCust.isPresent() && existingUser.isPresent()) {

			CustomerInfo tempCust = existingCust.get();
			User tempUser = existingUser.get();

			tempCust.setPhone(newCustomerInfo.getPhone());
			tempCust.setPan(newCustomerInfo.getPan());
			tempCust.setAadhar(newCustomerInfo.getAadhar());
			tempCust.setSecurityQuestion(newCustomerInfo.getSecurityQuestion());
			tempCust.setSecurityAnswer(newCustomerInfo.getSecurityAnswer());
			tempCust.setFullname(newCustomerInfo.getFullname());
			tempUser.setFullname(newCustomerInfo.getFullname());

			userRepo.save(tempUser);

			return new ResponseEntity<>(customerInfoRepo.save(tempCust), HttpStatus.OK);

		} else {
			throw new UserNotExistException("Data Not there");

		}
	}

	//Fetching the CustomerInfo Table
	public CustomerInfo getCustomerInfo(@PathVariable("username") String username) {

		Optional<CustomerInfo> optional = customerInfoRepo.findById(username);
		if (!optional.isPresent()) {
			throw new UserNotExistException("User does not exist");
		}
		return optional.get();

	}

	//Customer enabled or disabled by staff
	public ResponseEntity<User> customerEnableOrDisable(
			@RequestBody User newUser) {
		Optional<User> existingCust = userRepo.findById(newUser.getUsername());

		if (existingCust.isPresent()) {

			User tempCust = existingCust.get();

			tempCust.setEnabled(newUser.isEnabled());

			return new ResponseEntity<>(userRepo.save(tempCust), HttpStatus.OK);

		} else {
			throw new UserNotExistException("Data Not there");

		}
	}


	public User getCustomer(@PathVariable("username") String username) {

		Optional <User> optional = userRepo.findById(username);
		if (!optional.isPresent()) {
			throw new UserNotExistException("User does not exist");
		}
		return optional.get();

	}

}