package com.revature.quinnsbank;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String username;
	private String password;
	private String fName;
	private String lName;
	private int age;
	
	private List<Account> linkedAccounts = new ArrayList<>();
	
	public User() {
		this.username = "None";
		this.password = "";
		this.fName = "John";
		this.lName = "Doe";
		this.age = 16;
	}
	
	public User(String username, String password, String fName, String lName, int age) {
		super();
		this.username = username;
		this.password = password;
		this.fName = fName;
		this.lName = lName;
		this.age = age;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(this.password.equals(password)) {
			System.out.println("Your new password cannot be the same as your old one.");
		}
		this.password = password;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		if(age < 0) {
			System.out.println("Invalid age input.");
			return;
		}else if(age < 16) {
			System.out.println("You must be sixteen or older to use this bank.");
			return;
		}
		this.age = age;
	}
	
	public void createAccount() {
		linkedAccounts.add(new Account());
	}
	
	public Account getAccount(int index) {
		if(index < linkedAccounts.size()) {
			return(linkedAccounts.get(index));
		}else {
			System.out.println("List index out of bounds.");
			return null;
		}
	}
	
	public boolean comparePassword(String attempt) {
		if(attempt.equals(password))return true;
		else return false;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", fName=" + fName + ", lName=" + lName
				+ ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (age != other.age)
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	

}
