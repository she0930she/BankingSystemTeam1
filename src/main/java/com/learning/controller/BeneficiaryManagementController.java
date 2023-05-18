package com.learning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.learning.entity.Beneficiary;
import com.learning.service.BeneficiaryManagementService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BeneficiaryManagementController {
	
	
	BeneficiaryManagementService beneficiaryService;
	
	
	@Autowired
	public BeneficiaryManagementController(BeneficiaryManagementService beneficiaryService) {
		super();
		this.beneficiaryService = beneficiaryService;
	}


	@GetMapping("/customer/{username}/beneficiary")
	public List<Beneficiary> getAllBeneficiaries(@PathVariable("username") String username) {
		return beneficiaryService.getAllBeneficiariesOfCustomer(username);
	}
	
	@GetMapping("/customer/{username}/beneficiary/approved")
	public List<Beneficiary> getAllApprovedBeneficiaries(@PathVariable("username") String username) {
		return beneficiaryService.getAllEnabledBeneficiariesOfCustomer(username);
	}
	
	@PostMapping("/customer/{username}/beneficiary")
	public Beneficiary addBeneficiary(@PathVariable("username") String username, @RequestBody Beneficiary beneficiary) {
		return beneficiaryService.addBeneficiaryToCustomer(beneficiary, username);
	}
	
	@DeleteMapping("/customer/{username}/beneficiary/{beneficiaryID}")
	public ResponseEntity<String> deleteBeneficiaryById(@PathVariable("username") String username, @PathVariable("beneficiaryID") int beneficiaryID) {
		beneficiaryService.removeBeneficiaryById(username, beneficiaryID);
		return new ResponseEntity<String>("Beneficiary(id: "+ beneficiaryID + ") deleted", HttpStatus.OK);
	}
	
	@PutMapping("/staff/beneficiary")
	public Beneficiary enableBeneficiary(@RequestBody Beneficiary beneficiary) {
		return beneficiaryService.approveBeneficiary(beneficiary);
	}
	
	@GetMapping("/staff/beneficiary")
	public List<Beneficiary> getAllBeneficiariesToBeApproved() {
		return beneficiaryService.getAllDisabledBeneficiaries();
	}
}
