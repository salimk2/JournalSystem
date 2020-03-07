package application;
public class Account {

	  private String username;
	  private String password;
	  private int accountType;

	  public Account(String username, String password, int type) {
		super();
		this.username = username;
		this.password = password;
		this.accountType = type;
	}

	protected String getUsername(){
	    return this.username;
	  }

	  protected String getPassword(){
	    return this.password;
	  }


	  public void setUsername(String user){
	    if(usernameRequirement(user)){
	      this.username = user;
	    }

	  }

	  public void setPassword(String pass){
			if(passwordRequirement(pass)){
				  this.password = pass;
			}
	  }

	  public void setAccountType(int type){
	    if(0 <= type && type <= 3){
	      this.accountType = type;
	    }

	  }

	  public int getAccountType(){
	    return this.accountType;
	  }

	  public static Boolean passwordRequirement(String password){
		    if(password.length() >= 6 && !password.contains(" ")){
		      return true;
		    } else{
		      return false;
		    }
		  }

		  public static Boolean usernameRequirement(String username){
		    if (username.length() >= 6 && !username.contains(" ")){
		      return true;
		    } else{
		      return false;
		    }
		  }

	  public String accountTypetoString(){
	    if(this.getAccountType() == 0){
	      return "Admin";
	    } else if (this.getAccountType() == 1){
	      return "Researcher";
	    } else if (this.getAccountType() == 2){
	      return "Editor";
	    } else if (this.getAccountType() == 3){
	      return "Reviewer";
	    }
	    return null;
	  }

	  public static void main(String[] args) {

	  }

}
