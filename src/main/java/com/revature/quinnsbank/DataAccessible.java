package com.revature.quinnsbank;

import java.util.List;

public interface DataAccessible {
	public User retrieveUser(String username);
	
	public Account retrieveAccount(int accountNumber);
	
	public List<User> retrieveAllUsers();
	
	public List<Account> retrieveAllAccounts();
	
	public void storeUser(User user);
	
	public void storeAccount(Account account);
	
	public void deleteUser(String username);
	
	public void deleteAccount(int accountNumber);

}