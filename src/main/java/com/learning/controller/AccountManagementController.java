package com.learning.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.Account;
import com.learning.entity.CustomerInfo;
import com.learning.service.AccountManagementService;
import com.learning.util.JwtVerify;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AccountManagementController {

	@Autowired
	AccountManagementService accountManagementService;

	public AccountManagementController(AccountManagementService accountManagementService) {
		super();
		this.accountManagementService = accountManagementService;
	}

	@PostMapping("customer/{username}/account")
//	public @ResponseBody ResponseEntity<String> createCustomerAccount(@PathVariable("username") String username,
	public @ResponseBody Account createCustomerAccount(@PathVariable("username") String username,
			@RequestBody Account newAccount, HttpServletRequest httpServletRequest) {
		JwtVerify.jwtVerify(httpServletRequest, username);
		return accountManagementService.createAccount(username, newAccount);
//		return new ResponseEntity<String>("Account ("+accnum+") created", HttpStatus.CREATED);
	}

	@GetMapping("customer/{username}/account")
	public List<Object[]> getCustomerAccounts(@PathVariable("username") String username,
			HttpServletRequest httpServletRequest) {
		JwtVerify.jwtVerify(httpServletRequest, username);
		return accountManagementService.getCustomerInfoAccounts(username);
	}
	
	@GetMapping("customer/{username}/account/{accountNumber}")
	public Account getCustomerAccountDetails(@PathVariable("username") String username,
			@PathVariable("accountNumber") Long accountNumber, HttpServletRequest httpServletRequest) {
		JwtVerify.jwtVerify(httpServletRequest, username);
		return accountManagementService.getCustomerInfoAccountDetails(username, accountNumber);
	}
	
	@GetMapping("staff/accounts/approve")
	public List<Object[]> getAccountsToBeApproved() {
		return accountManagementService.listOfAccountsToBeApproved();
	}
	
	@PutMapping("staff/{username}/account/{accountNumber}")
	public ResponseEntity<Account> enableCustomerAccount(@PathVariable("username") String username, 
			@PathVariable("accountNumber") Long accountNumber) {
		return accountManagementService.enableAccount(username, accountNumber);
		
	}

	@PutMapping("staff/accounts/approve")
	public void approveListOfAccounts(@RequestBody CustomerInfo newCustomerInfo) {
		accountManagementService.approveCustomerAccounts(newCustomerInfo);
	}



}
