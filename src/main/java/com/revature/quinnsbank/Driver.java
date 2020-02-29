package com.revature.quinnsbank;

public class Driver {
	public static DataAccessible data = FileAccessor.getFileAccessor();
	
	public static void main(String[] args) {
		Menu.loginScreen();
	}

}
