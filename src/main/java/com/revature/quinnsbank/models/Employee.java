package com.revature.quinnsbank.models;

public class Employee extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5518075848783091056L;
	
	public Employee(String username, String password, String fName, String lName, int age) {
		super(username, password, fName, lName, age);
	}

}
