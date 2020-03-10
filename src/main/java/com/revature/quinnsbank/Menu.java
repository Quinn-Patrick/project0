package com.revature.quinnsbank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.revature.quinnsbank.data.DataAccessible;
import com.revature.quinnsbank.data.FileAccessor;
import com.revature.quinnsbank.models.Account;
import com.revature.quinnsbank.models.Admin;
import com.revature.quinnsbank.models.Employee;
import com.revature.quinnsbank.models.User;
import com.revature.quinnsbank.services.AccountServices;
import com.revature.quinnsbank.services.UserServices;

public abstract class Menu {
	private static Scanner scan = new Scanner(System.in);
	private static User currentUser = null;
	private static Account currentAccount = null;
	public DataAccessible data = FileAccessor.getFileAccessor();
	private static List<String> reserved = Arrays.asList("New", "new", "-1", "Quit", "quit");
	
	public static User chooseUser(User user) {
		if(user instanceof Employee || user instanceof Admin) {
			return typeUser();
		}
		else return user;
	}
	
	public static User typeUser() {
		System.out.println("Please enter a user name. Enter -1 to go back.");
		
		String userSelect = "-2";
		
		do {
			if(userSelect.equals("-1")) return null;
			
			userSelect = scan.nextLine();
			
		}while(UserServices.retrieveUser(userSelect) == null);
		
		return UserServices.retrieveUser(userSelect);
		
	}
	
	public static Account chooseAccount(User user) {
		
		if(user instanceof Employee) {
			System.out.println("Please enter an account number. Enter -1 to go back.");
			
			int accountSelect = -2;
			
			do {
				if(accountSelect == -1) return null;
				try {
					accountSelect = Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e) {
					accountSelect = -2;
				}
			}while(AccountServices.retrieveAccount(accountSelect) == null);
			
			return AccountServices.retrieveAccount(accountSelect);
		}
		else {
			List<String> options = new ArrayList<>();
			for(Integer i : user.getAccountNumbers()) {
				if(AccountServices.retrieveAccount(i) != null)options.add(i.toString());
			}
			options.add("Back");
			
			String selection = numberedMenu(options, "Which of your accounts would you like to check?");
			
			if(selection.equals("Back")) return null;
			
			Account accountSelection = AccountServices.retrieveAccount(Integer.parseInt(selection));
			
			return accountSelection;
		}
	}
	
	
	
	public static String chooseUserAction(User user) {
		List<String> options = new ArrayList<>();
		if(currentUser.equals(user) || currentUser instanceof Admin) {
			options.add("Update personal information.");
			options.add("Update password.");
			options.add("Delete");
			if(currentUser instanceof Admin) {
				options.add("View and modify accounts.");
			}
		}
		options.add("Back");
		String choice = numberedMenu(options, "");
		return choice;
	}
	
	public static String chooseAccountAction(User user, Account account) {
		List<String> options = new ArrayList<>();
		boolean isAccountOwner = AccountServices.findUsers(account.getAccountNumber()).contains(currentUser.getUsername());
		if(account.isApproved() && (currentUser instanceof Admin || isAccountOwner)) {
			options.add("Withdraw");
			options.add("Deposit");
			options.add("Transfer Funds");
		}
		if(currentUser instanceof Employee) {
			options.add("Approve");
		}
		if(currentUser instanceof Admin || isAccountOwner) options.add("Delete");
		options.add("Back");
		
		String choice = numberedMenu(options, "What would you like to do with this account?");
		
		return choice;
	}
	
	
	private static void withdraw(Account account) {
		System.out.println("Withdraw how much?");
		account.withdraw(getDollarAmount());
	}
	
	private static void deposit(Account account) {
		System.out.println("Deposit how much?");
		account.deposit(getDollarAmount());
	}
	
	
	private static double getDollarAmount() {
		double val = 0.0;
		do {
			try {
				val = Double.parseDouble(scan.nextLine());
			}catch(NumberFormatException e){
				val = 0.0;
			}
		}while(val <= 0.0);
		return val;
	}
	
	public static String getUserInformation(User requester, User target) {
		return "Invalid input.";
	}
	
	public static void loginScreen() {
		do {
			
			System.out.println("Welcome to Quinn's Bank! Please enter your username,"
					+ " or enter \"New\" to sign up.");
			String loginUsername = "";
			do {
				loginUsername = scan.nextLine();
				if(loginUsername.equalsIgnoreCase("New")) {
					createNewUser();
					break;
				}
				else if(loginUsername.equalsIgnoreCase("Quit")) {
					System.exit(0);
				}
				currentUser = UserServices.retrieveUser(loginUsername);
			}while(currentUser == null);
			
			if(currentUser != null) {
				System.out.println("Please enter your password.");
				String pass;
				boolean success = false;
				do {
					pass = scan.nextLine();
					success = currentUser.comparePassword(pass);
					if(!success) {
						Driver.logger.info("Unsuccessful login attempt for username " + 
								currentUser.getUsername() + ".");
						System.out.println("The username and password do not match. "
								+ "Please try again. -1 to log out.");
					}else {
						Driver.logger.info("User " + currentUser.getUsername() 
						+ " successful login attempt.");
						homeMenu();
					}
					if(pass.equals("-1")) {
						currentUser = null;
						break;
					}
				}while(!success);
			}
		}while(true);
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
		
		User newUser = null;
		if(newUsername.contains("*Admin*")) {
			newUser = new Admin(newUsername, newPassword, newfName, newlName, newAge);
		}
		else if(newUsername.contains("*Employee*")) {
			newUser = new Employee(newUsername, newPassword, newfName, newlName, newAge);
		}
		else {
			newUser = new User(newUsername, newPassword, newfName, newlName, newAge);
		}
		
		UserServices.storeUser(newUser);
		
		currentUser = newUser;
	}
	
	public static boolean originalUsername(String username) {
		List<User> users = UserServices.retrieveAllUsers();
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				System.out.println("The username you entered is already in use.");
				return false;
			}
		}
		if(reserved.contains(username)) {
			System.out.println("The string you have entered cannot be used as a username.");
		}
		return true;
	}
	
	public static String numberedMenu(List<String> options, String header) {
		int optionNum = 0;
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
		}while(val == -1 || val >= options.size());
		
		return options.get(val);
	}
	
	public static void homeMenu() {
		boolean loggingOut = false;
		do {
			if(currentUser != null) {
				List<String> options = new ArrayList<>();
				options.add("Open Account");
				if(currentUser.hasAccount() || currentUser instanceof Employee)options.add("View Account");
				options.add("Link Existing Account");
				options.add("View User");
				options.add("Log Out");
				
				switch(numberedMenu(options, "Make a selection.")) {
				case("Open Account"):
					openAccount();
					break;
				case("View Account"):
					viewAccounts(currentUser);
					break;
				case("View User"):
					viewUsers();
					break;
				case("Link Existing Account"):
					linkAccount(currentUser);
					break;
				case("Log Out"):
					loggingOut = true;
					currentUser = null;
					break;
				}
			}
		}while(!loggingOut && currentUser != null);
	}
	
	public static void viewAccounts(User user) {
		boolean back = false;
		do {
			Account checkedAccount = chooseAccount(user);
			if(checkedAccount == null) break;
			
			System.out.println("Account Number: " + checkedAccount.getAccountNumber());
			System.out.println("Balance: $" + checkedAccount.getBalance());
			switch(chooseAccountAction(user, checkedAccount)) {
			case("Withdraw"):
				withdraw(checkedAccount);
				break;
			case("Deposit"):
				deposit(checkedAccount);
				break;
			case("Transfer Funds"):
				transferFunds(checkedAccount, user);
				break;
			case("Delete"):
				AccountServices.deleteAccount(checkedAccount.getAccountNumber());
				break;
			case("Approve"):
				checkedAccount.approve();
				break;
			case("Back"):
			default:
				break;
			}
		}while(!back);
	}
	
	public static void viewUsers() {
		boolean back = false;
		do {
			User checkedUser = chooseUser(currentUser);
			if(checkedUser == null) break;
			System.out.println("Username: " + checkedUser.getUsername());
			System.out.println("Name: " + checkedUser.getfName() + " " + checkedUser.getlName());
			System.out.println("Age: " + checkedUser.getAge());
			switch(chooseUserAction(checkedUser)) {
				case("Update personal information."):
					updateUserInfo(checkedUser);
					break;
				case("Update password."):
					updatePassword(checkedUser);
					break;
				case("Delete"):
					if(currentUser.equals(checkedUser)) {
						System.out.println("This action will PERMANENTLY delete your user profile and close the application. Proceed (y/n)?");
						if(!scan.nextLine().equals("y")) {
							break;
						}
					}
					UserServices.deleteUser(checkedUser.getUsername());
					if(currentUser.equals(checkedUser)) System.exit(0);
					break;
				case("View and modify accounts."):
					viewAccounts(checkedUser);
					break;
				case("Back"):
					back = true;
					break;
				default:
					break;
			}
		}while(!back);
	}
	
	public static void updateUserInfo(User user) {
		System.out.println("Enter your new first name:");
		UserServices.setfName(user, scan.nextLine());
		
		System.out.println("Enter your new last name.");
		UserServices.setlName(user, scan.nextLine());
		
		System.out.println("Enter your new age.");
		int newAge = -1;
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
				System.out.println("You must be sixteen or older to use this bank.");
			}
		}while(newAge < 16);
		
		UserServices.setAge(user, newAge);
	}
	
	public static void updatePassword(User user) {
		System.out.println("Please enter your new password.");
		UserServices.setPassword(user, scan.nextLine());
	}
	
	public static void transferFunds(Account account1, User user) {
		
		System.out.println("Which user would you like to transfer funds to?");
		User targetUser = typeUser();
		System.out.println("Which of their accounts would you like to transfer funds to?");
		Account targetAccount = chooseAccount(targetUser);
		System.out.println("How much money would you like to transfer?");
		double transferAmount = getDollarAmount();
		if(transferAmount > account1.getBalance()) {
			System.out.println("Transfer failed. This transfer would overdraw your account.");
			return;
		}
		if(account1.isApproved() && targetAccount.isApproved()) {
			account1.withdraw(transferAmount);
			targetAccount.deposit(transferAmount);
		}else {
			System.out.println("One or both of these accounts has not been approved.");
		}
	}
	
	public static void linkAccount(User user) {
		System.out.println("Link an account belonging to which user?");
		User targetUser = typeUser();
		System.out.println("Which of their accounts would you like to link to?");
		Account targetAccount = chooseAccount(targetUser);
		System.out.println(targetUser.getUsername() + ", please enter your password.");
		String pass = null;
		boolean success = false;
		do {
			pass = scan.nextLine();
			success = targetUser.comparePassword(pass);
			if(!success) {
				Driver.logger.info("Unsuccessful password attempt for username " + 
						targetUser.getUsername() + ".");
				System.out.println("The username and password do not match. "
						+ "Please try again. -1 to go back.");
			}else {
				Driver.logger.info("User " + currentUser.getUsername() 
				+ " successful password attempt.");
				user.linkAccount(targetAccount);
			}
			if(pass.equals("-1")) {
				currentUser = null;
				return;
			}
		}while(!success);
	}
	
	public static void openAccount() {
		List<String> options = new ArrayList<>();
		options.add("Checking");
		options.add("Savings");
		options.add("Back");
		String checkingOrSavings = numberedMenu(options, "Would you like to open a checking or savings account?");
		switch(checkingOrSavings) {
		case("Checking"):
			UserServices.createAccount(currentUser, "Checking");
		break;
		case("Savings"):
			UserServices.createAccount(currentUser, "Savings");
		break;
		default:
			return;

		}
		System.out.println("A new account has been opened, pending approval by"
				+ " a bank administrator.");
		
		
	}
}
