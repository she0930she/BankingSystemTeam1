package com.learning.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.learning.entity.Account;
import com.learning.entity.CustomerInfo;
import com.learning.exception.UserAlreadyExistException;
import com.learning.exception.UserNotExistException;
import com.learning.repo.AccountRepository;
import com.learning.repo.CustomerInfoRepository;
import com.learning.repo.UserRepository;

@Service
public class AccountManagementService {

	UserRepository userRepo;
	CustomerInfoRepository customerInfoRepo;
	AccountRepository accountRepository;

	@Autowired
	public AccountManagementService(UserRepository userRepo, CustomerInfoRepository customerInfoRepo,
			AccountRepository accountRepository) {
		super();
		this.userRepo = userRepo;
		this.customerInfoRepo = customerInfoRepo;
		this.accountRepository = accountRepository;
	}

	public Account createAccount(@PathVariable("username") String username, @RequestBody Account newAccount) {

		if (!customerInfoRepo.existsById(username)) {
			throw new UserNotExistException("Username: " + username + " does not exist.");
		}

		long leftLimit = 10000000L;
		long rightLimit = 999999999999L;
		long accountNo = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

		if (accountRepository.existsById(accountNo)) {
			throw new UserAlreadyExistException("Account number: " + accountNo + " already exist.");

		}

		Optional<CustomerInfo> optional = customerInfoRepo.findById(username);

		CustomerInfo customer = optional.get();

		newAccount.setAccountNumber(accountNo);
		newAccount.setEnabled(false);
		newAccount.setCreationDate(LocalDate.now());
		newAccount.setCustomerInfo(customer);

//		customer.setAccountList(newAccount.getAccountNumber());
		System.out.println(customer.getAccountList());

		return accountRepository.save(newAccount);

	}

	public List<Object[]> getCustomerInfoAccounts(@PathVariable("username") String username) {

		List<Object[]> optional = customerInfoRepo.fetchCustomerAccounts(username);
		if (optional.isEmpty()) {
			throw new UserNotExistException("User does not exist");
		}

		return optional;

	}

	public Account getCustomerInfoAccountDetails(@PathVariable("username") String username,
			@PathVariable("accountNumber") Long accountNumber) {

		Optional<CustomerInfo> customer = customerInfoRepo.findById(username);
		Optional<Account> account = accountRepository.findById(accountNumber);
		if (!customer.isPresent()) {
			throw new UserNotExistException("User does not exist");
		}
		if (!account.isPresent()) {
			throw new UserNotExistException("Account does not exist");
		}

		return account.get();

	}

	public List<Object[]> listOfAccountsToBeApproved() {

		List<Object[]> accountToBeApproved = accountRepository.fetchAccountsToBeApproved();

		return accountToBeApproved;
	}

	public ResponseEntity<Account> enableAccount(@PathVariable("username") String username,
			@PathVariable("accountNumber") Long accountNumber) {
		Optional<Account> existingAccount = accountRepository.findById(accountNumber);
		Optional<CustomerInfo> existingCust = customerInfoRepo.findById(username);

		if (!existingCust.isPresent()) {
			throw new UserNotExistException("Customer not exist");
			
		}
		
		if (existingAccount.isPresent()) {

			Account tempAccount = existingAccount.get();

			tempAccount.setEnabled(true);

			return new ResponseEntity<>(accountRepository.save(tempAccount), HttpStatus.OK);

		} else {
			throw new UserNotExistException("Account with account number " + accountNumber + 
					" does not exist");

		}

	}

	public void approveCustomerAccounts(@RequestBody CustomerInfo newCustomerInfo) {
		Optional<CustomerInfo> existingCust = customerInfoRepo.findById(newCustomerInfo.getUsername());

		if (existingCust.isPresent()) {

			CustomerInfo tempCust = existingCust.get();

			Set<Account> accountSet = tempCust.getAccountList();
			
			List<Account> accountList = new ArrayList<>(accountSet);

			for (int i = 0; i<accountList.size(); i++) {
				
				Account account = accountList.get(i);
				
				Optional<Account> existingAccount = accountRepository.findById(account.getAccountNumber());

				Account tempAccount = existingAccount.get();

				tempAccount.setEnabled(true);

				accountRepository.save(tempAccount);

			}

		} else {
			throw new UserNotExistException("Customer does not exist");

		}

	}

}
