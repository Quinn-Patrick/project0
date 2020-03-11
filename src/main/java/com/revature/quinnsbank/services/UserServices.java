package com.revature.quinnsbank.services;

import java.util.List;

import com.revature.quinnsbank.Driver;
import com.revature.quinnsbank.data.DataAccessible;
import com.revature.quinnsbank.data.DatabaseAccessor;
import com.revature.quinnsbank.models.Account;
import com.revature.quinnsbank.models.User;


public class UserServices {
	private static DataAccessible data = new DatabaseAccessor();
	
	public static void setUsername(User user, String username) {
		Driver.logger.info("User set username to " + username + ".");
		user.setUsername(username);
		data.storeUser(user);
	} 
	
	public static void setPassword(User user, String password) {
		Driver.logger.info("User " + user.getUsername() + " attempted to change password.");
		if(user.getPassword().equals(password)) {
			Driver.logger.info("Password change failed. Reason: must pick new password.");
			Driver.logger.info("User " + user.getUsername() + " attempted to change password.");
			System.out.println("Your new password cannot be the same as your old one.");
		}
		else{
			Driver.logger.info("Password successfully changed to " + password + ".");
			user.setPassword(password);
			data.storeUser(user);
		}
	}
	
	public static void setfName(User user, String fName) {
		Driver.logger.info("User " + user.getUsername() + " changed first name to " + fName + ".");
		user.setfName(fName);
		data.storeUser(user);
	}
	
	public static void setlName(User user, String lName) {
		Driver.logger.info("User " + user.getUsername() + " changed last name to " + lName + ".");
		user.setlName(lName);
		data.storeUser(user);
	}
	
	public static void setAge(User user, int age) {
		Driver.logger.info("User " + user.getUsername() + " attempted to change age to " + age + ".");
		if(age < 0) {
			Driver.logger.info("Age change failed. Reason: negative input.");
			System.out.println("Invalid age input.");
			return;
		}else if(age < 16) {
			Driver.logger.info("Age change failed. Reason: too young.");
			System.out.println("You must be sixteen or older to use this bank.");
			return;
		}
		Driver.logger.info("User " + user.getUsername() + " successfully changed age to " + age + ".");
		user.setAge(age);
		data.storeUser(user);
	}
	
	
	public static void createAccount(User user, String checkingOrSavings) {
		Account newAccount = new Account(checkingOrSavings);
		Driver.logger.info("User " + user.getUsername() + " opened a new account with number "
				+ newAccount.getAccountNumber());
		user.linkAccount(newAccount);
		
		data.storeAccount(newAccount);
		data.storeUser(user);
		System.out.println("Your new account number is " + newAccount.getAccountNumber() + ".");
	}
	
	public static User retrieveUser(String username) {
		return data.retrieveUser(username);
	}
	
	public static List<User> retrieveAllUsers(){
		return data.retrieveAllUsers();
	}
	
	public static void deleteUser(String username) {
		Driver.logger.info("User " + username + " user profile deleted.");
		data.deleteUser(username);
	}
	
	public static void storeUser(User user) {
		Driver.logger.info("User " + user.getUsername() + " profile recorded.");
		data.storeUser(user);
	}
	
	public static List<String> retrieveAllUsernames(){
		return data.retrieveAllUsernames();
	}
	
	
}
