package com.learning.entity;

import java.io.Serializable;
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
@Table(name="transaction")
public class Transaction implements Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionID;
	
	private long senderAccountNumber;
	
	private long receiverAccountNumber;
	private float amount;
	private String comments;
	private TransactionType transactionType;
	private CrDbType crdbType;
	LocalDate creationDate = LocalDate.now();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountNumber")
	private Account account;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(int transactionID, long senderAccountNumber, long receiverAccountNumber, float amount,
			String comments, TransactionType transactionType, CrDbType crdbType, LocalDate creationDate,
			Account account) {
		super();
		this.transactionID = transactionID;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
		this.amount = amount;
		this.comments = comments;
		this.transactionType = transactionType;
		this.crdbType = crdbType;
		this.creationDate = creationDate;
		this.account = account;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public long getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(long senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CrDbType getCrdbType() {
		return crdbType;
	}

	public long getReceiverAccountNumber() {
		return receiverAccountNumber;
	}


	public void setReceiverAccountNumber(long receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}


	public void setCrdbType(CrDbType crdbType) {
		this.crdbType = crdbType;
	}


	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", senderAccountNumber=" + senderAccountNumber
				+ ", receiverAccountNumber=" + receiverAccountNumber + ", amount=" + amount + ", comments=" + comments
				+ ", transactionType=" + transactionType + ", crdbType=" + crdbType + ", creationDate=" + creationDate
				+ ", account=" + account + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}	
	
	
}
