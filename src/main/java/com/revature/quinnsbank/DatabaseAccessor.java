package com.revature.quinnsbank;

import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccessor implements DataAccessible {

	@Override
	public User retrieveUser(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			User outUser = null;
			String sql = "SELECT * FROM users WHERE username = ?";
			ResultSet res = cleanAndExecute(conn, sql, username);
			
			if(!res.next()) return null;
			String userLevel = res.getString("userType");
			String newfName = res.getString("firstName");
			String newlName = res.getString("lastName");
			int newAge = res.getInt("age");
			String newPassword = res.getString("password");
			
			switch(userLevel.toLowerCase()) {
			case("customer"):
				outUser = new User(username, newPassword, newfName, newlName, newAge);
				break;
			case("employee"):
				outUser = new Employee(username, newPassword, newfName, newlName, newAge);
				break;
			case("admin"):
				outUser = new Admin(username, newPassword, newfName, newlName, newAge);
				break;
			default:
				outUser = new User(username, newPassword, newfName, newlName, newAge);
				break;
			}
			
			String join = "SELECT accountNumber FROM joinTable WHERE username = ?";
			
			ResultSet joinres = cleanAndExecute(conn, join, username);
			
			
			
			//conn.close();
			
			while(joinres.next()) {
				outUser.linkAccount(retrieveAccount(joinres.getInt("accountNumber")));
			}
			
			return outUser;
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to retrieve user.");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account retrieveAccount(int accountNumber) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE accountNumber = ?";
			ResultSet res = cleanAndExecute(conn, sql, new Integer(accountNumber).toString());
			
			if(!res.next()) return null;
			//System.out.println(accountNumber);
			
			Account outAccount = new Account(res.getString("checkingOrSavings"));
			outAccount.setAccountNumber(res.getInt("AccountNumber"));
			outAccount.setBalance(res.getDouble("balance"));
			if(res.getInt("approved") == 1) outAccount.approve();
			return outAccount;
			
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to retrieve account.");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> retrieveAllUsers() {
		try(Connection conn = ConnectionUtil.getConnection()){
			List<User> allUsers = new ArrayList<>();
			
			String sql = "SELECT username FROM users";
			
			ResultSet res = cleanAndExecute(conn, sql);
			
			while(res.next()) {
				allUsers.add(retrieveUser(res.getString("username")));
			}
			
			return allUsers;
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to retrieve all users.");
			return null;
		}
	}

	@Override
	public List<Account> retrieveAllAccounts() {
		try(Connection conn = ConnectionUtil.getConnection()){
			List<Account> allAccounts = new ArrayList<>();
			
			String sql = "SELECT accountNumber FROM accounts";
			
			ResultSet res = cleanAndExecute(conn, sql);
			
			while(res.next()) {
				allAccounts.add(retrieveAccount(res.getInt("accountNumber")));
			}
			
			return allAccounts;
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to retrieve all accounts.");
			return null;
		}
	}

	@Override
	public void storeUser(User user) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String userType = null;
			if(user instanceof Admin) {
				userType = "Admin";
			}else if(user instanceof Employee) {
				userType = "Employee";
			}else {
				userType = "Customer";
			}
			if(isUserNew(user.getUsername())) {
				String sql = "CALL insert_into_users(?, ?, ?, ?, ?, ?)";
				
				
				cleanAndExecute(conn, sql,
						userType, user.getUsername(), user.getPassword(),
						user.getfName(), user.getlName(), new Integer(user.getAge()).toString());
			}
			else {
				String sql = "CALL update_users(?, ?, ?, ?, ?, ?)";
				cleanAndExecute(conn, sql,
						userType, user.getUsername(), user.getPassword(),
						user.getfName(), user.getlName(), new Integer(user.getAge()).toString());
			}
			
			for(int i : user.getAccountNumbers()) {
				String sql = "SELECT * FROM jointable WHERE username = ? AND accountNumber = ?";
				
				if(!cleanAndExecute(conn, sql, user.getUsername(), new Integer(i).toString()).next()) {
					String join = "CALL insert_account_connection(?, ?)";
					cleanAndExecute(conn, join, user.getUsername(), new Integer(i).toString());
				}
				
			}
			
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to store or update a user.");
		}

	}

	@Override
	public void storeAccount(Account account) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			int approvedInt = 0;
			if(account.isApproved()) approvedInt = 1;
			
			String approved = new Integer(approvedInt).toString();
			
			if(isAccountNew(account.getAccountNumber())) {
				String sql = "CALL insert_into_accounts(?, ?, ?, ?)";
				
				//System.out.println(approved);
				cleanAndExecute(conn, sql,
						new Integer(account.getAccountNumber()).toString(),
						new Double(account.getBalance()).toString(),
						approved,
						account.getCheckingOrSavings());
			}
			else {
				String sql = "CALL update_accounts(?, ?, ?, ?)";
				//System.out.println(approved);
				cleanAndExecute(conn, sql,
						new Integer(account.getAccountNumber()).toString(),
						new Double(account.getBalance()).toString(),
						approved,
						account.getCheckingOrSavings());
			}
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to store or update a user.");
		}

	}

	@Override
	public void deleteUser(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "DELETE FROM users WHERE username = ?";
			cleanAndExecute(conn, sql, username);
			
			
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to store or update a user.");
		}

	}

	@Override
	public void deleteAccount(int accountNumber) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM jointable WHERE accountNumber = ?";
			cleanAndExecute(conn, sql, new Integer(accountNumber).toString());
			
			sql = "DELETE FROM accounts WHERE accountNumber = ?";
			cleanAndExecute(conn, sql, new Integer(accountNumber).toString());
			
			
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting to store or update a user.");
		}

	}
	
	private boolean isUserNew(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE username = ?";
			ResultSet res = cleanAndExecute(conn, sql, username);
			if(res.next()) {
				return false;
			}
			else {
				return true;
			}
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting a miscellaneous task.");
			return false;
		}
	}
	
	private boolean isAccountNew(int accountNumber) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE accountnumber = ?";
			ResultSet res = cleanAndExecute(conn, sql, new Integer(accountNumber).toString());
			if(res.next()) {
				return false;
			}
			else {
				return true;
			}
		}catch(SQLException e) {
			Driver.logger.error("Connection to database failed when attempting a miscellaneous task.");
			return false;
		}
	}

	private ResultSet cleanAndExecute( Connection conn, String ... sql) {
		try{
			
			//System.out.println("Made it here.");
			ResultSet res = null;
			if(sql[0].contains("?") && !sql[0].contains("CALL")){
				PreparedStatement stmt = conn.prepareStatement(sql[0]);
			
				//System.out.println("Made it here.");
				for(int i = 1; i < sql.length; i++) {
					//System.out.println(sql[i]);
					stmt.setString(i, sql[i]);
				}
				//System.out.println("Made it here. " + sql[1]);
				res = stmt.executeQuery();
			}
			else if(!sql[0].contains("CALL")) {
				Statement stmt = conn.createStatement();
				res = stmt.executeQuery(sql[0]);
			}
			else {
				CallableStatement stmt = conn.prepareCall(sql[0]);
				
				//System.out.println("Made it here.");
				for(int i = 1; i < sql.length; i++) {
					//System.out.println(sql[i]);
					stmt.setString(i, sql[i]);
				}
				//System.out.println("Made it here. " + sql[0]);
				res = stmt.executeQuery();
			}
			//System.out.println("Made it here.");
			return res;
		}catch(SQLException e) {
			//if(!sql[0].toLowerCase().contains("connection"))
				Driver.logger.error("Connection to database failed.");
				e.printStackTrace();
			return null;
		}
	}
}
