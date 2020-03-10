package com.revature.quinnsbank.models;

import java.io.Serializable;
import java.util.Random;

import com.revature.quinnsbank.services.AccountServices;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3010928263862704730L;
	private int accountNumber;
	private double balance;
	private boolean approved = false;
	
	private String checkingOrSavings = "";
	
	public Account() {
		super();
		Random r = new Random();
		accountNumber = Math.abs(r.nextInt());
		balance = 0;
		approved = false;
	}

	public Account(double balance) {
		this();
		this.balance = balance;
		this.approved = false;
	}
	
	public Account(String checkingOrSavings) {
		this();
		this.balance = 0;
		this.approved = false;
		this.checkingOrSavings = checkingOrSavings;
	}

	public void setBalance(double newBalance) {
		this.balance = newBalance;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}

	public void deposit(double amount) {
		AccountServices.deposit(this, amount);
	}
	
	public void withdraw(double amount) {
		AccountServices.withdraw(this, amount);
	}

	public boolean isApproved() {
		return approved;
	}

	public void approve() {
		this.approved = true;
		AccountServices.record(this);
	}

	public String getCheckingOrSavings() {
		return checkingOrSavings;
	}

	public void setCheckingOrSavings(String checkingOrSavings) {
		this.checkingOrSavings = checkingOrSavings;
	}

	@Override
	public String toString() {
		return "Account [balance=" + balance + ", approved=" + approved + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (approved ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (approved != other.approved)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if(this.accountNumber != ((Account) obj).getAccountNumber()) return false;
		return true;
	}
}
