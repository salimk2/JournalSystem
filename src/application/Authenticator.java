package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Authenticator
 * 
 * Authenticator.java registers new users and authenticates user data.
 */
public class Authenticator {

	private HashMap<String, Account> accounts = new HashMap<String, Account>();

	/**
	 * Constructor
	 */
	public Authenticator() {
	}

	/**
	 * Registers the correct username, password and account type for the application
	 * It will then start the hashing process if username, password and account type
	 * are correct.
	 * 
	 * @param username The correct username(String) that was entered
	 * @param password The correct password(String) that was entered
	 * @param type     The correct account type(int) that was selected based
	 * @return
	 */
	public short register(String username, String password, int type) {
		boolean correctUser = Account.usernameRequirement(username);
		boolean correctPass = Account.passwordRequirement(password);
		boolean notSameUser = !accounts.containsKey(username);

		if (correctUser && correctPass && notSameUser) {

			// hashing function
			String salt = PasswordHashing.getSalt(30);
			String hashedPwd = PasswordHashing.generateSecurePassword(password, salt);
			accounts.put(username, new Account(username, hashedPwd, type, salt));
			try {
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(System.getProperty("user.dir") + "//Login.txt", true));
				bw.write(username + " " + hashedPwd + " " + type + " " + salt + System.getProperty("line.separator"));
				bw.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
			return 0;

		}

		if (!correctUser) {
			return -1;// "Invalid Username.";
		} else if (!notSameUser) {
			return -2;// "Invalid password.";
		} else if (!correctPass) {
			return -3;// "Username taken.";
		}

		else
			return -4;// "unknown issue";
	}

	/**
	 * Reads the data from a selected character-input stream
	 * 
	 * @throws IOException
	 */
	public void ReadData() throws IOException {
		try {

			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "//Login.txt"));

			String s = "";
			String[] info = new String[10000];

			while ((s = br.readLine()) != null) {
				info = s.split(" ");
				accounts.put(info[0], new Account(info[0], info[1], Integer.parseInt(info[2]), info[3]));

			}
			br.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Checks for a valid log-in username, and checks for valid account with the
	 * password and hashed password
	 * 
	 * @param username The username(String) entered
	 * @param password The password(String) entered
	 * @return Will return nothing if the account is not found, else it will return
	 *         the account with the username
	 */
	public Account login(String username, String password) {
		if (accounts.containsKey(username)) {

			// compare password against hashed password
			boolean pwdHashMatch = PasswordHashing.verifyUserPassword(password, accounts.get(username).getPassword(),
					accounts.get(username).getSalt());

			if (pwdHashMatch) {
				System.out.printf("Display page for %s%n", accounts.get(username).accountTypetoString());
				return accounts.get(username);
			} else {

				return null;
			}
		} else {

			return null;
		}
	}

	/**
	 * Gets all the submissions tha were added
	 * 
	 * @param userId The userID for the submitted submissions added
	 * @return ArrayList<String> of all the submissions
	 */
	public ArrayList<String> getSubmissions(String userId) {
		// create arrayList
		ArrayList<String> submissions = new ArrayList<String>();

		// add user submissions (these are fake submissions)
		submissions.add("sub1");
		submissions.add("sub2");
		submissions.add("sub3");
		submissions.add("subFinal");

		return submissions;
	}

	/**
	 * Gets all the reviews that were added
	 * 
	 * @param userId
	 * @param subID
	 * @return ArrayList<String> of all the reviews
	 */
	public ArrayList<String> getReviews(String userId, String subID) {
		// create arrayList
		ArrayList<String> reviews = new ArrayList<String>();

		// add user reviews (fake reviews:)
		reviews.add("rev1");
		reviews.add("rev2");
		reviews.add("revMinor");

		return reviews;
	}

	/**
	 * Gets all the comments that are left on the assigned file
	 * 
	 * @param userId, revID
	 * @return ArrayList<String> of all the comments
	 */
	public ArrayList<String> getComments(String userId, String revID) {
		// create arrayList
		ArrayList<String> comments = new ArrayList<String>();

		// add user reviews
		comments.add("comment1");
		comments.add("comment2");
		// reviews.add("commentMinor");

		return comments;
	}

	/**
	 * getWithdrawStatus returns status of the withdraw, true = Pending
	 * 
	 * @param userId, subID
	 * @return boolean
	 */
	public boolean getWithdrawStatus(String userId, String subID) {
		return false;
	}

}
