package com.revature.quinnsbank.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.quinnsbank.models.Account;
import com.revature.quinnsbank.models.User;




public class FileAccessor implements DataAccessible{
	
	private Set<User> userList = new HashSet<>();
	private Set<Account> accountList = new HashSet<>();
	private static FileAccessor instance = null;
	
	public FileAccessor() {
		super();
		loadUsers();
		loadAccounts();
	}
	
	public static FileAccessor getFileAccessor(){
		if(instance != null) {
			return instance;
		}
		
		instance  = new FileAccessor();
		return instance;
	}
	
	private void loadUsers() {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/users.db"))){
			userList = ((HashSet<User>) ois.readObject());
		}catch(IOException e) {
			System.out.println("No user data found.");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void loadAccounts() {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/accounts.db"))){
			accountList = ((HashSet<Account>) ois.readObject());
		}catch(IOException e) {
			System.out.println("No account data found.");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public User retrieveUser(String username) {
		loadUsers();
		
		for(User u : userList) {
			if(u.getUsername().equals(username)) {
				return u;
			}
		}
		System.out.println("User " + username + " not found.");
		return null;
	}

	@Override
	public Account retrieveAccount(int accountNumber) {
		loadAccounts();
		for(Account a : accountList) {
			if(a.getAccountNumber() == accountNumber) {
				return a;
			}
		}
		//System.out.println("Account number " + accountNumber + " not found.");
		return null;
	}

	@Override
	public List<User> retrieveAllUsers() {
		loadUsers();
		List<User> retList = new ArrayList<>();
		for(User u : userList) {
			retList.add(u);
		}
		
		return retList;
	}

	@Override
	public List<Account> retrieveAllAccounts() {
		loadAccounts();
		List<Account> retList = new ArrayList<>();
		for(Account a : accountList) {
			retList.add(a);
		}
		
		return retList;
	}

	@Override
	public void storeUser(User user) {
		userList.add(user);
		recordUsers();
		
	}

	@Override
	public void storeAccount(Account account) {
		accountList.add(account);
		recordAccounts();
		
	}

	private void recordUsers() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"files/users.db"))){
			oos.writeObject(userList);
		}catch(IOException e) { 
			e.printStackTrace();
		}
	}
	
	private void recordAccounts() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"files/accounts.db"))){
			oos.writeObject(accountList);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteUser(String username) {
		User u = null;
		for(User u1 : userList) {
			if(u1.getUsername().equals(username)){
				u = u1;
				break;
			}
		}
		if(u != null) {
			System.out.println("Deleted user " + username + ".");
			userList.remove(u);
		}
		//else System.out.println("User " + username + " not found.");
		recordUsers();
		
	}

	@Override
	public void deleteAccount(int accountNumber) {
		Account a = null;
		for(Account a1 : accountList) {
			if(a1.getAccountNumber() == accountNumber){
				a = a1;
				break;
			}
		}
		if(a != null) {
			System.out.println("Deleted account number " + accountNumber + ".");
			accountList.remove(a);
		}
		//else System.out.println("Account number " + accountNumber + " not found.");
		recordAccounts();
		
	}
	
	@Override
	public List<String> retrieveAllLinkedUsers(int accountNumber){
		List<User> allUsers = retrieveAllUsers();
		List<String> linkedUsers = new ArrayList<>();
		for(User u : allUsers) {
			if(u.getAccountNumbers().contains(accountNumber)) {
				linkedUsers.add(u.getUsername());
			}
		}
		return linkedUsers;
	}

}
