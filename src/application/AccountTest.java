package application;

// Imports
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * AccountTest
 * JUnit testing for the Account class 
 */
public class AccountTest {
	
	/**
	 * Test the constructor correctly initializes the variables
	 */
	@Test
	void testAccount() {
		// setup
		Account myAccount;
		
		// executing
		myAccount = new Account("username1", "password123", 0, "salt");
		
		// verification
		assertEquals("The username is incorrect.", "username1", myAccount.getUsername());
		assertEquals("The password is incorrect.", "password123", myAccount.getPassword());
		assertSame("The type is incorrect.", 0, myAccount.getAccountType());
		assertEquals("The salt is incorrect.", "salt", myAccount.getSalt());
	}
	
	/**
	 * Test the setUsername method works correctly
	 */
	@Test
	void testSetUsername() {
		// setup
		Account myAccount;
		
		// executing 
		myAccount = new Account("username1", "password123", 0, "salt");
		myAccount.setUsername("username2");
		
		// verification
		assertEquals("The username is incorrect.", "username2", myAccount.getUsername());
	}
	
	/**
	 * Test the setPassword method works correctly
	 */
	@Test
	void testSetPassword() {
		// setup
		Account myAccount;
		
		// executing 
		myAccount = new Account("username1", "password123", 0, "salt");
		myAccount.setPassword("password456");
		
		// verification
		assertEquals("The password is incorrect.", "password456", myAccount.getPassword());
	}	
	
	/**
	 * Test the setAccountType method works correctly
	 */
	@Test
	void testSetAccountType() {
		// setup
		Account myAccount;
		
		// executing 
		myAccount = new Account("username1", "password123", 0, "salt");
		myAccount.setAccountType(1);
		
		// verification
		assertSame("The username is incorrect.", 1, myAccount.getAccountType());
	}	
	
	/**
	 * Test the passwordRequirement method works correctly
	 * 
	 * this test case is using the Boundary-Based selection,
	 * it is checking the size boundary
	 */
	@Test
	void testPasswordRequirement() {
		// verification
		assertFalse("The password must be at least 8 characters.", Account.passwordRequirement("abcdfgh"));		// 7
		assertTrue("The password must be at least 8 characters.", Account.passwordRequirement("abcdfgh1"));		// 8
		assertFalse("The password must contain at least 1 digit.", Account.passwordRequirement("abcdfghj"));
	}	
	
	/**
	 * Test the usernameRequirement method works correctly
	 * 
	 * this test case is using the Boundary-Based selection,
	 * it is checking the size boundary
	 */
	@Test
	void testUsernameRequirement() {
		// verification
		assertFalse("The username must be at least 6 characters.", Account.usernameRequirement("abcdf"));	// 5
		assertTrue("The username must be at least 6 characters.", Account.usernameRequirement("abcdfg"));	// 6
		assertFalse("The username must not contain spaces.", Account.usernameRequirement("abcd fghj"));
	}		
	
	/**
	 * Test the accountTypetoString method works correctly
	 */
	@Test
	void testAccountTypetoString() {
		// setup
		Account myAccount;
		
		// executing 
		myAccount = new Account("username1", "password123", 0, "salt");
		
		// verification
		assertEquals("Admin is incorrect.", "Admin", myAccount.accountTypetoString());
		myAccount.setAccountType(1);
		assertEquals("Researcher is incorrect.", "Researcher", myAccount.accountTypetoString());
		myAccount.setAccountType(2);
		assertEquals("Editor is incorrect.", "Editor", myAccount.accountTypetoString());
		myAccount.setAccountType(3);
		assertEquals("Reviewer is incorrect.", "Reviewer", myAccount.accountTypetoString());
	}	

}
