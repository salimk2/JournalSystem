package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Authenticator {

	private HashMap<String, Account> accounts = new HashMap<String, Account>();
	
	/**
	 * Constructor
	 */
	public Authenticator() {
	}

	/**
	 * Register
	 * @param username
	 * @param password
	 * @param type
	 * @return
	 */
	public short register(String username, String password, int type) {
		boolean correctUser = Account.usernameRequirement(username);
		boolean correctPass = Account.passwordRequirement(password);
		boolean notSameUser = !accounts.containsKey(username);
		System.out.println("not same user:" + notSameUser);
		if (correctUser && correctPass && notSameUser) {
			
			//hashing function
			String salt = PasswordHashing.getSalt(30);
			String hashedPwd = PasswordHashing.generateSecurePassword(password, salt);
			accounts.put(username, new Account(username, hashedPwd, type,salt));
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+ "//Login.txt",true));
				bw.write(username + " " + hashedPwd + " " + type + " "+salt +System.getProperty("line.separator"));
				bw.close();
			} catch (IOException e) {
				System.out.println("Error Saving DataBase.");
				e.printStackTrace();
			}
			return 0;

		}

		if (!correctUser) {
			return -1;//"Invalid Username.";
		}
		else if (!notSameUser) {
			return -2;//"Invalid password.";
		}
		else if (!correctPass) {
			return -3;//"Username taken.";
		}
		
		else return -4;//"unknown issue";
	}

	/**
	 * ReadData
	 * @throws IOException
	 */
	public void ReadData() throws IOException {
		try {
			//For Windows
			//BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "\\Login.txt"));
			
			//For Unix based systems only
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "//Login.txt"));
			
			String s = "";
			String[] info = new String[10000];
			System.out.println("Current Users Passwords Types:");
			while ((s = br.readLine()) != null) {
				 info = s.split(" ");
				accounts.put(info[0], new Account(info[0], info[1], Integer.parseInt(info[2]),info[3]));
				System.out.println(info[0] + " " + info[1] + " " + info[2]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error Loading DataBase.");
			e.printStackTrace();
		}
	}
	
	/**
	 * login
	 * @param username
	 * @param password
	 * @return
	 */
	public Account login(String username, String password) {
		if (accounts.containsKey(username)) {
			//compare password against hashed password
			boolean pwdHashMatch =PasswordHashing.verifyUserPassword(password,accounts.get(username).getPassword(), accounts.get(username).getSalt());
			System.out.println("this is the hashed pwd: " +accounts.get(username).getPassword() +"math: " + pwdHashMatch);
			if (pwdHashMatch) {
				System.out.printf("Display page for %s%n", accounts.get(username).accountTypetoString());
				return accounts.get(username);
			} else {
				System.out.println("Invalid Password.");
				return null;
			}
		} else {
			System.out.println("Account not found.");
			return null;
		} 
	}

	/**
	 * getSubmissions
	 * @param userId
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getSubmissions(String userId) {
		// create arrayList
		ArrayList<String> submissions = new ArrayList<String>();
		
		// add user submissions		(these are fake submissions)
		submissions.add("sub1");
		submissions.add("sub2");
		submissions.add("sub3");
		submissions.add("subFinal");
		
		return submissions;
	}
	
	/**
	 * getReviews
	 * @param userId, subID
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getReviews(String userId, String subID) {
		// create arrayList
		ArrayList<String> reviews = new ArrayList<String>();
		
		// add user reviews				(fake reviews:)
		reviews.add("rev1");
		reviews.add("rev2");
		reviews.add("revMinor");
		
		return reviews;
	}	
	
	/**
	 * getComments
	 * @param userId, revID
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getComments(String userId, String revID) {
		// create arrayList
		ArrayList<String> comments = new ArrayList<String>();
		
		// add user reviews				
		comments.add("comment1");
		comments.add("comment2");
		//reviews.add("commentMinor");
		
		return comments;
	}	
	
	/**
	 * getWithdrawStatus
	 * returns status of the withdraw, true = Pending
	 * @param userId, subID
	 * @return boolean
	 */
	public boolean getWithdrawStatus(String userId, String subID) {
		return false;
	}
	
}
