package com.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Account;
import com.learning.entity.Transaction;
import com.learning.entity.TransactionType;
import com.learning.exception.AccountNotAuthorizedException;
import com.learning.exception.AccountNotExistException;
import com.learning.exception.SenderLowBalanceException;
import com.learning.exception.UserNotAuthorizedException;
import com.learning.repo.AccountRepository;
import com.learning.repo.TransactionRepository;
import com.learning.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionManagementService {
	
	TransactionRepository transactionRepo;
	AccountRepository accountRepo;
	UserRepository userRepo;
	
	@Autowired
	public TransactionManagementService(TransactionRepository transactionRepo, AccountRepository accountRepo,
			UserRepository userRepo) {
		super();
		this.transactionRepo = transactionRepo;
		this.accountRepo = accountRepo;
		this.userRepo = userRepo;
	}
	
	
	public Transaction transfer(Transaction transaction) throws CloneNotSupportedException {
		
		// Check if sender account exists
		long senderAccNum = transaction.getSenderAccountNumber();
		Optional<Account> senderAccountOptional = accountRepo.findById(senderAccNum);
		if(!senderAccountOptional.isPresent()) {
			throw new AccountNotExistException("Sender account (" + senderAccNum + ") doesn't exist.");
		}
		
		// Check if the sender hasn't been disabled
		Account senderAccount = senderAccountOptional.get();
		String senderUsername = senderAccount.getCustomerInfo().getUsername();
		if(!userRepo.findById(senderUsername).get().isEnabled()) {
			throw new UserNotAuthorizedException("Sender has been disabled");
		}
		
		//Check if the sender account hasn't been disabled
		if(!senderAccount.isEnabled()) {
			throw new AccountNotAuthorizedException("Sender account (" + senderAccNum + ") is disabled.");
		}
		
		// Check if the receiver Beneficiary exists
		long receiverAccNum = transaction.getReceiverAccountNumber();
		Optional<Account> receiverOptional = accountRepo.findById(receiverAccNum);
		if(!receiverOptional.isPresent()) {
			throw new AccountNotExistException("Receiver account (" + receiverAccNum + ") doesn't exist.");
		}
		
		// Check if the receiver hasn't been disabled.
		Account receiverAccount = receiverOptional.get();
		String receiverUsername = receiverAccount.getCustomerInfo().getUsername();
		if(!userRepo.findById(receiverUsername).get().isEnabled()) {
			throw new UserNotAuthorizedException("Receiver has been disabled");
		}
		
		// Check if the receiver account hasn't been disabled
		if(!receiverAccount.isEnabled()) {
			throw new AccountNotAuthorizedException("Receiver account (" + receiverAccount + ") is disabled.");
		}
		
		// Check if the transfer amount exceeds sender's balance
		float senderbalance = senderAccount.getBalance();
		float receiverBalance = receiverAccount.getBalance();
		float transferAmount = transaction.getAmount(); 
		if(senderbalance < transferAmount) {
			throw new SenderLowBalanceException("Sender doesn't have enough balance to make this transaction.");
		}
		
		// Changing balance for both accounts.
		senderAccount.setBalance(senderbalance - transferAmount);
		receiverAccount.setBalance(receiverBalance + transferAmount);
		
		// Save a record to both sender and receiver's transaction list
		List<Transaction> senderTransactionList = senderAccount.getTransactionList();
		if(senderTransactionList == null) {
			senderTransactionList = new ArrayList<>();
		}
		
		List<Transaction> receiverTransactionList = receiverAccount.getTransactionList();
		if(receiverTransactionList == null) {
			receiverTransactionList = new ArrayList<>();
		}
		
		transaction.setTransactionType(TransactionType.RECEIVE);
		transaction.setAccount(receiverAccount);
		receiverTransactionList.add(transaction);
		receiverAccount.setTransactionList(receiverTransactionList);
//		accountRepo.save(receiverAccount);
		
		Transaction copy = (Transaction) transaction.clone();
		copy.setAccount(senderAccount);
		copy.setTransactionType(TransactionType.SEND);
		senderTransactionList.add(copy);
		senderAccount.setTransactionList(senderTransactionList);
//		accountRepo.save(senderAccount);
		
		
		return transactionRepo.save(transaction);
	}

	public List<Transaction> getStatement(long accNum) {
		Optional<Account> accOptional = accountRepo.findById(accNum);
		if(!accOptional.isPresent()) {
			throw new AccountNotExistException("Account (account number:" + accNum + ") doesn't exist.");
		}
		
		Account account = accOptional.get();
		List<Transaction> list = account.getTransactionList();
		Collections.reverse(list);
		return list;
	}
	
	public List<Transaction> getAllTransactions() {
		return transactionRepo.findAll();
	}
}
