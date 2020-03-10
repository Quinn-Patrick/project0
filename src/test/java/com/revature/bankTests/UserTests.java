package com.revature.bankTests;

import com.revature.quinnsbank.models.Account;
import com.revature.quinnsbank.models.User;
import com.revature.quinnsbank.services.UserServices;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;

public class UserTests {
	
	private User u = null;

	/*
	 * This method will be invoked before the test class is instantiated.
	 * This is primarily used in order to set up the global test environment.
	 */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/*
	 * This method will be invoked after the entire test class has finished
	 * running its tests.
	 * This is primarily used to clean up the global test environment.
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	/*
	 * This method will be invoked before each individual test case.
	 * This is primarily used to set up specific test environments, or perhaps to rest
	 * the test environment.
	 */
	@Before
	public void setUp() throws Exception {
		u = new User();
	}

	/*
	 * This method will be invoked after each individual test case.
	 * This is primarily used to reset the test environment.
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testusername() {
		u.setUsername("New User");
		assertEquals("New User", u.getUsername());
	}
	
	@Test
	public void testpassword() {
		UserServices.setPassword(u, "password");
		assertEquals("password", u.getPassword());
		assertEquals(false, u.comparePassword("passworb"));
		assertEquals(true, u.comparePassword("password"));
		UserServices.setPassword(u, "password");
		UserServices.setPassword(u, "passworb");
		assertEquals("passworb", u.getPassword());
		assertEquals(true, u.comparePassword("passworb"));
		assertEquals(false, u.comparePassword("password"));
		
	}
	
	@Test
	public void testPersonalInfo() {
		UserServices.setAge(u, 18);
		assertEquals(18, u.getAge());
		UserServices.setAge(u, -5);
		assertEquals(18, u.getAge());
		UserServices.setAge(u, 15);
		assertEquals(18, u.getAge());
		
		UserServices.setfName(u, "Milsherd");
		assertEquals("Milsherd", u.getfName());
		UserServices.setlName(u, "Facehead");
		assertEquals("Facehead", u.getlName());
	}
	
	@Test
	public void testMiscellaneous() {
		User u2 = new User("User 2", "password2", "Talmidge", "McGooliger", 33);
		assertEquals(false, u.equals(u2));
		User u3 = new User("User 2", "password2", "Talmidge", "McGooliger", 33);
		//assertEquals(true, u3.equals(u2));
		System.out.println(u2);
		System.out.println(u2.hashCode());
		
		u2 = new User("User 3", "password2", "Talmidge", "McGooliger", 33);
		assertEquals(false, u3.equals(u2));
		u2 = new User("User 2", "password3", "Talmidge", "McGooliger", 33);
		assertEquals(false, u3.equals(u2));
		u2 = new User("User 2", "password2", "Amish", "McGooliger", 33);
		assertEquals(false, u3.equals(u2));
		u2 = new User("User 2", "password2", "Talmidge", "Gadfell", 33);
		assertEquals(false, u3.equals(u2));
		u2 = new User("User 2", "password2", "Talmidge", "McGooliger", 115);
		assertEquals(false, u3.equals(u2));
		
		assertEquals(true, u2.equals(u2));
		assertEquals(false, u2.equals(null));
		assertEquals(false, u2.equals(new Account()));
		
		u2 = new User(null, "password2", "Talmidge", "McGooliger", 33);
		assertEquals(false, u2.equals(u3));
		u2 = new User("User 2", null, "Talmidge", "McGooliger", 33);
		assertEquals(false, u2.equals(u3));
		u2 = new User("User 2", "password2", null, "McGooliger", 33);
		assertEquals(false, u2.equals(u3));
		u2 = new User("User 2", "password2", "Talmidge", null, 33);
		assertEquals(false, u2.equals(u3));
		
		u2.setlName("McGooliger");
		
		u3 = new User(null, "password2", "Talmidge", "McGooliger", 33);
		assertEquals(false, u3.equals(u2));
		u3 = new User("User 2", null, "Talmidge", "McGooliger", 33);
		assertEquals(false, u3.equals(u2));
		u3 = new User("User 2", "password2", null, "McGooliger", 33);
		assertEquals(false, u3.equals(u2));
		u3 = new User("User 2", "password2", "Talmidge", null, 33);
		assertEquals(false, u3.equals(u2));
		
		
	}
	
	@Test
	public void testAccountLink() {
		u.createAccount("Checking");
		Account a = u.getAccount(0);
		Account a1 = u.getAccount(1);
		
		assertEquals(u.getAccountNumbers(0), u.getAccount(0).getAccountNumber());
		
		u.getAccountNumbers(1);
	}
	
	/*
	 * This test will be a success if it sees MyException.
	 * It will fail if it sees no exception.
	 */
	/*
	 * @Test(expected = MyException.class) public void expectException() {
	 * 
	 * }
	 */
	
	/*
	 * In test driven development, we come up with crazier and crazier edge cases
	 * and alter the logic of our methods to pass them.
	 * 
	 * Or we can just ignore them.
	 */
	/*
	 * @Ignore
	 * 
	 * @Test public void testAdd2() { }
	 * 
	 * @Test(timeout = 3000) //This test will fail if it does not complete within 3
	 * seconds. public void testTimeOut() { while(true) {
	 * 
	 * } }
	 */
}