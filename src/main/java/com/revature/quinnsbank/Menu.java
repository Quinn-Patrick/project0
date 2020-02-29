package com.revature.quinnsbank;

import java.util.Scanner;

public abstract class Menu {
	private Scanner scan = new Scanner(System.in);
	
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
	
	public static boolean loginScreen() {
		return false;
	}
}
