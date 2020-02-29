package com.revature.quinnsbank;

import java.util.Random;

public class Account {
	private Random r;
	private int accountNumber;
	private double balance;
	private boolean approved = false;
	
	public Account() {
		super();
		r = new Random();
		accountNumber = Math.abs(r.nextInt());
		r = null;
		balance = 0;
		approved = false;
	}

	public Account(double balance) {
		this();
		this.balance = balance;
		this.approved = false;
	}

	public double getBalance() {
		return balance;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}

	public void deposit(double amount) {
		if(approved) {
			if(amount >= 0) {
				this.balance += amount;
			}
			else System.out.println("Please only enter positive dollar amounts.");
		}
		else System.out.println("Please wait for a bank admin to approve your account"
				+ " before making deposits.");
	}
	
	public void withdraw(double amount) {
		if(approved) {
			if(amount >= 0 && this.balance - amount > 0) {
				this.balance -= amount;
			}
			else if(amount < 0) System.out.println("Please only enter positive dollar amounts.");
			else System.out.println("Withrawal denied: Account overdrawn.");
		}
		else System.out.println("Please wait for a bank admin to approve your account"
				+ " before making withrawals.");
	}

	public boolean isApproved() {
		return approved;
	}

	public void approve() {
		this.approved = true;
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
		return true;
	}

	
	
	
	
	
	
	
}
