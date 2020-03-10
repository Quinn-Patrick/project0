package com.revature.quinnsbank;

import org.apache.log4j.Logger;


public class Driver {
	//public static DataAccessible data = FileAccessor.getFileAccessor();
	public static Logger logger = Logger.getLogger(Driver.class);
	
	public static void main(String[] args) {
		logger.info("Application start.");
		Menu.loginScreen();
	}

}
