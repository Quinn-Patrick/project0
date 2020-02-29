package com.revature.quinnsbank;

import java.util.List;
import java.util.Scanner;

public abstract class Menu {
	private static Scanner scan = new Scanner(System.in);
	private static User currentUser = null;
	public DataAccessible data = FileAccessor.getFileAccessor();
	public static User chooseUser(User user) {
		return new User();
	}
	
	public static Account chooseAccount(User user) {
		return new Account();
	}
	
	public static int chooseUserAction(User user) {
		return 0;
	}
	
	public static int chooseAccountAction(User user, Account account) {
		return 0;
	}
	
	public static String getUserInformation(User requester, User target) {
		return "Invalid input.";
	}
	
	public static void loginScreen() {
		if(Driver.data.retrieveAllUsers().isEmpty()) {
			createNewUser();
		}
	}
	
	public static void createNewUser() {
		String newUsername;
		String newPassword;
		String newfName;
		String newlName;
		int newAge;
		System.out.println("Welcome to Quinn's Bank!");
		do {
			System.out.println("Please enter your username.");
			newUsername = scan.nextLine();
		}while(!originalUsername(newUsername));
		
		System.out.println("Please enter your password.");
		newPassword = scan.nextLine();
		
		System.out.println("Please enter your first name.");
		newfName = scan.nextLine();
		
		System.out.println("Please enter your last name.");
		newlName = scan.nextLine();
		
		System.out.println("Please enter your age.");
		do {
			int val = -1;
			
			do {
				try {
					val = Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e){
					val = -1;
				}
			}while(val == -1);
			
			newAge = val;
			if(newAge < 0) {
				System.out.println("Please enter a valid age.");
			}
			else if(newAge < 16){
				System.out.println("You must be sixteen to use this bank.");
			}
		}while(newAge < 16);
		
		User newUser = new User(newUsername, newPassword, newfName, newlName, newAge);
		
		Driver.data.storeUser(newUser);
		
		currentUser = newUser;
	}
	
	public static boolean originalUsername(String username) {
		List<User> users = Driver.data.retrieveAllUsers();
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				System.out.println("The username you entered is already in use.");
				return false;
			}
		}
		return true;
	}
	
	public static int numberedMenu(List<String> options, String header) {
		int optionNum = 1;
		System.out.println(header);
		for(String i : options) {
			System.out.println(optionNum + ": " + i);
			optionNum++;
		}
		
		int val = -1;
		
		do {
			try {
				val = Integer.parseInt(scan.nextLine());
			}catch(NumberFormatException e){
				val = -1;
			}
		}while(val == -1);
		
		return val;
	}
}
