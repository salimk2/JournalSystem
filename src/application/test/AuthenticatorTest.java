package application.test;

// Imports
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import application.Account;
import application.Authenticator;

/**
 * AuthenticatorTest JUnit testing for the Authenticator class
 */
class AuthenticatorTest {

	/**
	 * Test the register method
	 */
	@Test
	void testRegister() {
		// setup
		Authenticator myAuthenticator;

		// executing
		myAuthenticator = new Authenticator();

		// verification
		short shortNum = 0;

		// this test case will create a new user, username1. If it succeeds, then 0 is
		// returned
		assertSame("The user does not exist.", shortNum, myAuthenticator.register("username1", "password123", 0));

		// this test case will attempt to create an existing user, username1.
		// It should fail and return 0 (shortNum)
		assertNotSame("The user already exists.", shortNum, myAuthenticator.register("username1", "password123", 0));
	}

	/**
	 * Test the login method
	 */
	@Test
	void testLogin() {
		// setup
		Authenticator myAuthenticator;
		Account myAccount;

		// executing
		myAuthenticator = new Authenticator();
		myAuthenticator.register("username1", "password123", 0);

		// verification
		myAccount = myAuthenticator.login("username1", "password123"); // username1 exists
		assertSame("It should be a successful login.", 0, myAccount.getAccountType());

		myAccount = myAuthenticator.login("NotExist", "password123"); // NotExist does not exist
		assertSame("The username doesn't exist, therefore should be unsuccessful.", null, myAccount);
	}

	/**
	 * Test the ReadData method
	 */
	@Test
	void testReadData() {
		// setup
		Authenticator myAuthenticator;

		// executing
		myAuthenticator = new Authenticator();
		myAuthenticator.register("username1", "password123", 0); // ensures login file isn't empty

		// verification
		try {
			myAuthenticator.ReadData();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	/*
	 * @Test void testGetSubmissions() { fail("Not yet implemented"); }
	 * 
	 * @Test void testGetReviews() { fail("Not yet implemented"); }
	 * 
	 * @Test void testGetComments() { fail("Not yet implemented"); }
	 * 
	 * @Test void testGetWithdrawStatus() { fail("Not yet implemented"); }
	 */

}
