package application;

/**
 * Account
 * 
 * Account.java creates user accounts
 */
public class Account {

	// Declare user variables
	private String username;
	private String password;
	private int accountType;
	private String salt;

	/**
	 * Account Constructor
	 * @param username
	 * @param password
	 * @param type
	 * @param salt
	 */
	public Account(String username, String password, int type, String salt) {
		super();
		this.username = username;
		this.password = password;
		this.accountType = type;
		this.salt = salt;
	}

	/**
	 * This method displays the username that was verified
	 * 
	 * 
	 * @returns a string which is the username
	 */
	protected String getUsername() {
		return this.username;
	}

	/**
	 * This method displays the password that was verified
	 * 
	 * @returns a string which is the password
	 */
	protected String getPassword() {
		return this.password;
	}

	/**
	 * Generates a random salt number to be used to hash with the password
	 * 
	 * @returns a random salt number
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Calls the function usernameRequirement(string), to verify if the username is correct
	 * The username will now be used by the application by equaling it to this.username
	 * 
	 * @param user Username(String) being tested
	 */
	public void setUsername(String user) {
		if (usernameRequirement(user)) {
			this.username = user;
		}

	}

	/**
	 * Calls the function passwordRequirement(string), to verify if the password is correct
	 * The password will be used in the application to log-in
	 * 
	 * @param pass The password(String) being tested
	 */
	public void setPassword(String pass) {
		if (passwordRequirement(pass)) {
			this.password = pass;
		}
	}

	/**
	 * Depending on the username it will pass the specific account type index
	 * The account type will be equal to the specific index for the application to identify the type
	 * 
	 * @param type An interger that will be used to identify the account type, betweem 0 and 3
	 */
	public void setAccountType(int type) {
		if (0 <= type && type <= 3) {
			this.accountType = type;
		}

	}

	/**
	 * This method will get the account type that was previously selected to use in the application
	 * 
	 * @return the correct account type
	 */
	public int getAccountType() {
		return this.accountType;
	}

	/**
	 * Checks if the password matches the specific requirements 
	 * 
	 * @param password The password(String) entered to be verified 
	 * @return True if the password matches and False if the password does not match
	 */
	public static Boolean passwordRequirement(String password) {
		if (password.matches("^(?=.*\\d).{8,60}$")) {				// Password expression. Password must be between 4 and 8 digits
																	// long and include at least one numeric digit.
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the username meets the requirements 
	 * 
	 * @param username The username(String) entered to be verified 
	 * @return True if the username matches and False if the username does not match
	 */
	public static Boolean usernameRequirement(String username) {
		if (username.length() >= 6 && !username.contains(" ")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method associates each different account types with an int ranging from
	 * 0-3 0 = Admin 1 = Researcher 2 = Editor 3 = Reviewer
	 * 
	 * @return a string that is the account type name
	 */
	public String accountTypetoString() {
		if (this.getAccountType() == 0) {
			return "Admin";
		} else if (this.getAccountType() == 1) {
			return "Researcher";
		} else if (this.getAccountType() == 2) {
			return "Editor";
		} else if (this.getAccountType() == 3) {
			return "Reviewer";
		}
		return null;
	}

	public static void main(String[] args) {

	}

}
