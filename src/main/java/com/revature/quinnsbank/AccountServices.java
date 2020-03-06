package com.revature.quinnsbank;

public class AccountServices {
	private static DataAccessible data = new FileAccessor();
	
	public static void deposit(Account account, double amount) {
		Driver.logger.info("Attempted to deposit $" + amount + " into account "
				+ account.getAccountNumber());
		if(account.isApproved()) {
			if(amount >= 0) {
				Driver.logger.info("Deposit successful.");
				account.setBalance(account.getBalance() + amount);
			}
			else {
				Driver.logger.info("Deposit failed. Reason: negative dollar amount.");
				System.out.println("Please only enter positive dollar amounts.");
			}
		}
		else {
			Driver.logger.info("Deposit failed. Reason: account not approved.");
			System.out.println("Please wait for a bank admin to approve your account"
					+ " before making deposits.");
		}
		data.storeAccount(account);
	}
	
	public static void withdraw(Account account, double amount) {
		Driver.logger.info("Attempted to withdraw $" + amount + " from account "
				+ account.getAccountNumber() + ".");
		if(account.isApproved()) {
			if(amount >= 0 && account.getBalance() - amount >= 0) {
				Driver.logger.info("Withdrawal successful.");
				account.setBalance(account.getBalance() - amount);
			}
			else if(amount < 0) {
				Driver.logger.info("Withdrawal failed. Reason: negative dollar amount.");
				System.out.println("Please only enter positive dollar amounts.");
			}
			else {
				Driver.logger.info("Withdrawal failed. Reason: account overdraw.");
				System.out.println("Withrawal denied: Account overdrawn.");
			}
		}
		else {
			Driver.logger.info("Withdrawal failed. Reason: account not approved.");
			System.out.println("Please wait for a bank admin to approve your account"
				+ " before making withrawals.");
		}
		data.storeAccount(account);
	}
	
	public static void record(Account account) {
		Driver.logger.info("Account number " + account.getAccountNumber() + " data updated.");
		data.storeAccount(account);
	}
	
	public static Account retrieveAccount(int accountNumber) {
		return data.retrieveAccount(accountNumber);
	}
	
	public static void deleteAccount(int accountNumber) {
		Driver.logger.info("Account number " + accountNumber + " has been deleted.");
		data.deleteAccount(accountNumber);
	}
}
