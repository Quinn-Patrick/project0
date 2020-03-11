package com.revature.quinnsbank.data;

import java.util.List;

import com.revature.quinnsbank.models.Account;
import com.revature.quinnsbank.models.User;

public interface DataAccessible {
	public User retrieveUser(String username);
	
	public Account retrieveAccount(int accountNumber);
	
	public List<User> retrieveAllUsers();
	
	public List<Account> retrieveAllAccounts();
	
	public void storeUser(User user);
	
	public void storeAccount(Account account);
	
	public void deleteUser(String username);
	
	public void deleteAccount(int accountNumber);
	
	public List<String> retrieveAllLinkedUsers(int accountNumber);
	
	public List<String> retrieveAllUsernames();

}