package com.learning.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "beneficiary")
public class Beneficiary {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private int beneficiaryID;
	private boolean enabled;
	LocalDate creationDate = LocalDate.now();
	
	private long accNum;
	private AccountType type;
	
	private String beneficiaryName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private CustomerInfo customerInfo;

	public Beneficiary(int beneficiaryID, boolean enabled, LocalDate creationDate, long accNum, AccountType type,
			String beneficiaryName, CustomerInfo customerInfo) {
		super();
		this.beneficiaryID = beneficiaryID;
		this.enabled = enabled;
		this.creationDate = creationDate;
		this.accNum = accNum;
		this.type = type;
		this.beneficiaryName = beneficiaryName;
		this.customerInfo = customerInfo;
	}

	public Beneficiary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBeneficiaryID() {
		return beneficiaryID;
	}

	public void setBeneficiaryID(int beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public long getAccNum() {
		return accNum;
	}

	public void setAccNum(long accNum) {
		this.accNum = accNum;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	@Override
	public String toString() {
		return "Beneficiary [beneficiaryID=" + beneficiaryID + ", enabled=" + enabled + ", creationDate=" + creationDate
				+ ", accNum=" + accNum + ", type=" + type + ", beneficiaryName=" + beneficiaryName + ", customerInfo="
				+ customerInfo + "]";
	}
	
}
