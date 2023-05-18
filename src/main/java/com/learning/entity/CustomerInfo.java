package com.learning.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table (name="customerInfo")
public class CustomerInfo implements Serializable {
	private static final long serialVersionUID = 2652327633296064143L;
	
	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "securityQuestion")
	private String securityQuestion;
	
	@Column(name = "securityAnswer")
	private String securityAnswer;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "pan")
	private String pan;
	
	@Column(name = "aadhar")
	private String aadhar;
	
	@Column(name = "fullname")
	private String fullname;
	
	@JsonIgnore
	@OneToMany(mappedBy="customerInfo", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@Column(name = "accountList")
	private Set<Account> accountList;
	
	@JsonIgnore
	@OneToMany(mappedBy="customerInfo", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@Column(name = "beneficiaryList")
	private List<Beneficiary> beneficiaryList;

	public CustomerInfo(String username, String securityQuestion, String securityAnswer, String phone, String pan,
			String aadhar, String fullname, Set<Account> accountList, List<Beneficiary> beneficiaryList) {
		super();
		this.username = username;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
		this.phone = phone;
		this.pan = pan;
		this.aadhar = aadhar;
		this.fullname = fullname;
		this.accountList = accountList;
		this.beneficiaryList = beneficiaryList;
	}

	public CustomerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Set<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(Set<Account> accountList) {
		this.accountList = accountList;
	}

	public List<Beneficiary> getBeneficiaryList() {
		return beneficiaryList;
	}

	public void setBeneficiaryList(List<Beneficiary> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

//	@Override
//	public String toString() {
//		return "CustomerInfo [username=" + username + ", securityQuestion=" + securityQuestion + ", securityAnswer="
//				+ securityAnswer + ", phone=" + phone + ", pan=" + pan + ", aadhar=" + aadhar + ", fullname=" + fullname
//				+ ", accountList=" + accountList + ", beneficiaryList=" + beneficiaryList + "]";
//	}
	
}
