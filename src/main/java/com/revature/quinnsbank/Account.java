package com.revature.quinnsbank;

import java.io.Serializable;
import java.util.Random;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3010928263862704730L;
	private Random r = new Random();
	private final int accountNumber = Math.abs(r.nextInt());
	private double balance;
	private boolean approved = false;
	
	public Account() {
		super();
		r = new Random();
		r = null;
		balance = 0;
		approved = false;
	}

	public Account(double balance) {
		this();
		this.balance = balance;
		this.approved = false;
	}

	public void setBalance(double newBalance) {
		this.balance = newBalance;
	}
	
	public double getBalance() {
		return balance;
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
