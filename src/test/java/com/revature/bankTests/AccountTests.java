package com.revature.bankTests;

import com.revature.quinnsbank.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class AccountTests {
	
	private Account a = null;

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
		a = new Account();
	}

	/*
	 * This method will be invoked after each individual test case.
	 * This is primarily used to reset the test environment.
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeposit() {
		a.deposit(5);
		a.approve();
		a.deposit(5);
		assertEquals(5, a.getBalance(), 0.01);
		a.deposit(-5);
		assertEquals(5, a.getBalance(), 0.01);
	}
	
	@Test
	public void testWithdraw() {
		a.withdraw(3);
		a.approve();
		a.deposit(5);
		a.withdraw(3);
		assertEquals(2, a.getBalance(), 0.01);
		a.withdraw(-3);
		assertEquals(2, a.getBalance(), 0.01);
		a.withdraw(100);
		assertEquals(2, a.getBalance(), 0.01);
		
	}
	
	@Test
	public void testApproval() {
		assertEquals(false, a.isApproved());
		a.approve();
		assertEquals(true, a.isApproved());
	}
	
	@Test
	public void testMiscellaneous() {
		Account a2 = new Account(0);
		
		assertEquals(true, a.equals(a2));
		a.approve();
		assertEquals(false, a.equals(a2));
		a.deposit(200);
		a.withdraw(100);
		assertEquals(false, a.equals(a2));
		a2.approve();
		a2.deposit(100);
		assertEquals(true, a.equals(a2));
		a.toString();
		System.out.println(a.getAccountNumber());
		assertEquals(true, a.equals(a));
		a2.withdraw(50);
		assertEquals(false, a.equals(a2));
		a = null;
		assertEquals(false, a2.equals(a));
		System.out.println(a2.hashCode());
		User u1 = new User();
		assertEquals(false, a2.equals(u1));
	}
	
	/*
	 * This test will be a success if it sees MyException.
	 * It will fail if it sees no exception.
	 */
	/*
	 * @Test(expected = MyException.class) public void expectException() {
	 * a.divide(5, 0);
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
	 * @Test public void testAdd2() { assertNotEquals(1, Integer.MAX_VALUE +
	 * Integer.MAX_VALUE + 2); }
	 * 
	 * @Test(timeout = 3000) //This test will fail if it does not complete within 3
	 * seconds. public void testTimeOut() { while(true) {
	 * 
	 * } }
	 */
}
