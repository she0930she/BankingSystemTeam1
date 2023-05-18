package com.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.Transaction;
import com.learning.service.TransactionManagementService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TransactionManagementController {
	
	TransactionManagementService transactionManagementService;

	@Autowired
	public TransactionManagementController(TransactionManagementService transactionManagementService) {
		super();
		this.transactionManagementService = transactionManagementService;
	}
	
	
	@PutMapping("/customer/transfer")
	public Transaction customerTransfer(@RequestBody Transaction transaction) throws CloneNotSupportedException {
		return transactionManagementService.transfer(transaction);
	}
	
	@PutMapping("/staff/transfer")
	public Transaction staffTransfer(@RequestBody Transaction transaction) throws CloneNotSupportedException {
		return transactionManagementService.transfer(transaction);
	}
	
	@GetMapping("/staff/account/{accountNumber}")
	public List<Transaction> staffGetStatement(@PathVariable long accountNumber) {
		return transactionManagementService.getStatement(accountNumber);
	}
	
	@GetMapping("/staff/transactions")
	public List<Transaction> getAllTransactions() {
		return transactionManagementService.getAllTransactions();
	}
}
