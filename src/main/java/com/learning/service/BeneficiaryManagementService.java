package com.learning.service;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Beneficiary;
import com.learning.entity.CustomerInfo;
import com.learning.exception.AccountNotAuthorizedException;
import com.learning.exception.AccountNotExistException;
import com.learning.exception.BeneficiaryAlreadyExistException;
import com.learning.exception.BeneficiaryNotExistException;
import com.learning.exception.UserNotExistException;
import com.learning.repo.AccountRepository;
import com.learning.repo.BeneficiaryRepository;
import com.learning.repo.CustomerInfoRepository;
import java.util.stream.Collectors;

@Service
public class BeneficiaryManagementService {
	
	BeneficiaryRepository beneficiaryRepository;
	CustomerInfoRepository customerInfoRepo;
	AccountRepository accountRepository;
	
	@Autowired
	public BeneficiaryManagementService(BeneficiaryRepository beneficiaryRepository, CustomerInfoRepository customerInfoRepo,
			AccountRepository accountRepository) {
		super();
		this.beneficiaryRepository = beneficiaryRepository;
		this.customerInfoRepo = customerInfoRepo;
		this.accountRepository = accountRepository;
	}
	
	
	public List<Beneficiary> getAllBeneficiariesOfCustomer(String username) {
		Optional<CustomerInfo> userOptional = customerInfoRepo.findById(username);
		if(!userOptional.isPresent()) {
			throw new UserNotExistException("Customer " + username + " does not exist");
		}
		
		return userOptional.get().getBeneficiaryList();
	}
	
	public List<Beneficiary> getAllEnabledBeneficiariesOfCustomer(String username) {
		Optional<CustomerInfo> userOptional = customerInfoRepo.findById(username);
		if(!userOptional.isPresent()) {
			throw new UserNotExistException("Customer " + username + " does not exist");
		}
		
		return getAllEabledBeneficiaries();
	}
	
	public Beneficiary addBeneficiaryToCustomer(Beneficiary beneficiary, String username) {
		Optional<CustomerInfo> customerOptional = customerInfoRepo.findById(username);
		if(!customerOptional.isPresent()) {
			throw new UserNotExistException("Customer "+ username + " does not exist");
		}
		
		long accNum = beneficiary.getAccNum();
		if(!ifAccExist(accNum)) {
			throw new AccountNotExistException("Account " + accNum + " does not exist.");
		}
		
		if(!ifAccEnabled(accNum)) {
			throw new AccountNotAuthorizedException("Account " + accNum + " has not been authorized.");
		}
		
		if(!verifyBeneficiary(accNum)) {
			throw new BeneficiaryAlreadyExistException("Beneficiary with account number:" + accNum + " already exist.");
		}
		
		CustomerInfo customerInfo = customerOptional.get();
		beneficiary.setCustomerInfo(customerInfo);
		beneficiary.setBeneficiaryName(beneficiary.getBeneficiaryName());
		List<Beneficiary> list = customerInfo.getBeneficiaryList();
		if(list == null) {
			list = new ArrayList<>();
			customerInfo.setBeneficiaryList(list);
		}
		
		list.add(beneficiary);
		
		// save beneficiary in customerInfoRepo??not sure need to?
		customerInfo.setBeneficiaryList(list);
//		customerInfoRepo.save(customerInfo);
		
		return beneficiaryRepository.save(beneficiary);
	}
	
	public void removeBeneficiaryById(String username, int beneficiaryId) {
		Optional<CustomerInfo> customerOptional = customerInfoRepo.findById(username);
		if(!customerOptional.isPresent()) {
			throw new UserNotExistException("Customer "+ username + " does not exist");
		}
		
		Optional<Beneficiary> beneficiaryOptional = beneficiaryRepository.findById(beneficiaryId);
		if(!beneficiaryOptional.isPresent()) {
			throw new BeneficiaryNotExistException("Beneficiary(id:" + beneficiaryId + ") does not exist.");
		}
		
		CustomerInfo customerInfo = customerOptional.get();
		List<Beneficiary> list = customerInfo.getBeneficiaryList();
		Iterator<Beneficiary> iterator = list.iterator();
		while(iterator.hasNext()) {
			Beneficiary next = iterator.next();
			if(next.getBeneficiaryID() == beneficiaryId) {
				System.out.println("Removing beneficiary:" + beneficiaryId);
				iterator.remove();
				break;
			}
		}
		beneficiaryRepository.deleteById(beneficiaryId);
	}
	
	public List<Beneficiary> getAllDisabledBeneficiaries() {
		return beneficiaryRepository.findAll().stream().filter(b -> !b.isEnabled()).collect(Collectors.toList());
	}
	
	public List<Beneficiary> getAllEabledBeneficiaries() {
		return beneficiaryRepository.findAll().stream().filter(b -> b.isEnabled()).collect(Collectors.toList());
	}
	
	public Beneficiary approveBeneficiary(Beneficiary beneficiary) {
		Optional<Beneficiary> beneficiaryOptional = beneficiaryRepository.findById(beneficiary.getBeneficiaryID());
		if(!beneficiaryOptional.isPresent()) {
			throw new BeneficiaryNotExistException("Beneficiary not exist.");
		}
		Beneficiary storedBeneficiary = beneficiaryOptional.get();
		storedBeneficiary.setEnabled(true);
		
		return beneficiaryRepository.save(storedBeneficiary);
	}
	
	// Assuming all the accounts are stored in this table
	private boolean ifAccExist(long accNum) {
		// 
		return accountRepository.findById(accNum).isPresent();
	}
	
	private boolean ifAccEnabled(long accNum) {
		return accountRepository.findById(accNum).get().isEnabled();
	}
	
	private boolean verifyBeneficiary(long accNum) {
		List<Beneficiary> list = beneficiaryRepository.findAll();
		for(Beneficiary beneficiary: list) {
			if(beneficiary.getAccNum() == accNum) {
				return false;
			}
		}
		return true;
	}
	
	
	
}
