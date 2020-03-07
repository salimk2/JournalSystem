package application;
import java.io.*;
import java.util.HashMap;

public class Authenticator {

	private HashMap<String, Account> accounts = new HashMap<String, Account>();

	public Authenticator() {

	}

	public String register(String username, String password, int type) {
		boolean correctUser = Account.usernameRequirement(username);
		boolean correctPass = Account.passwordRequirement(password);
		boolean notSameUser = !accounts.containsKey(username);

		if (correctUser && correctPass && notSameUser) {
			accounts.put(username, new Account(username, password, type));
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+ "Login.txt"));
				bw.write(username + " " + password + " " + type + "%n");
				bw.close();
			} catch (IOException e) {
				System.out.println("Error Saving DataBase.");
				e.printStackTrace();
			}
			return "works";

		}

		if (!correctUser) {
			return "Invalid Username.";
		}
		if (!correctPass) {
			return "Invalid password.";
		}
		if (!notSameUser) {
			return "Username taken.";
		}
		return "unknown issue";
	}

	public void ReadData() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "\\Login.txt"));
			String s = "";
			System.out.println("Current Users Passwords Types:");
			while ((s = br.readLine()) != null) {
				String[] info = s.split(" ");
				accounts.put(info[0], new Account(info[0], info[1], Integer.parseInt(info[2])));
				System.out.println(info[0] + " " + info[1] + " " + info[2]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error Loading DataBase.");
			e.printStackTrace();
		}
	}
	
	public Account login(String username, String password) {
		if (accounts.containsKey(username)) {
			if (accounts.get(username).getPassword().equals(password)) {
				System.out.printf("Display page for %s%n", accounts.get(username).accountTypetoString());
				return accounts.get(username);
			} else {
				System.out.println("Invalid Passowrd.");
				return null;
			}
		} else {
			System.out.println("Account not found.");
			return null;
		} 
	}
}
