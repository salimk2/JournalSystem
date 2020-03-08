package application;
import java.io.*;
import java.util.HashMap;

public class Authenticator {

	private HashMap<String, Account> accounts = new HashMap<String, Account>();

	public Authenticator() {

	}

	public short register(String username, String password, int type) {
		boolean correctUser = Account.usernameRequirement(username);
		boolean correctPass = Account.passwordRequirement(password);
		boolean notSameUser = !accounts.containsKey(username);
		System.out.println("not same user:" + notSameUser);
		if (correctUser && correctPass && notSameUser) {
			accounts.put(username, new Account(username, password, type));
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+ "//Login.txt",true));
				bw.write(username + " " + password + " " + type + System.getProperty("line.separator"));
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
		else if (!correctPass) {
			return -2;//"Invalid password.";
		}
		else if (!notSameUser) {
			return -3;//"Username taken.";
		}
		
		else return -4;//"unknown issue";
	}

	public void ReadData() throws IOException {
		try {
			//For Windows
			//BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "\\Login.txt"));
			
			//For Unix based systems only
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "//Login.txt"));
			
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
				System.out.println("Invalid Password.");
				return null;
			}
		} else {
			System.out.println("Account not found.");
			return null;
		} 
	}
}
