package application;

import java.io.File;


public class Utilities {
	
//	private String username;
//	private String journalName;
//	private String message;
//	private int type;
//	
//	//to create userDir
//	public Utilities(String username,int type) {
//		super();
//		this.username = username;
//		this.type = type;
//	}
//	//to create Journal dir
//	public Utilities(String journalName) {
//		super();
//		this.journalName = journalName;
//	
//	}

//	public String getMessage() {
//		return message;
//	}
	
	/**
	 * 
	 */
	public Utilities() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String createUserDir(String username,int type) {
		
		String message;

		switch (type) {
		
		case 1:
			//Researcher
			if((new File(System.getProperty("user.dir") + File.separator + "researcher" + File.separator + username)).mkdirs()) {
				message = "Directory for researcher was created succesfully";

			}else {
				message = "Reseacher directory was not created";
			}
			break;
		case 3:
			//Reviewer
			if((new File(System.getProperty("user.dir") + File.separator + "reviewer" + File.separator + username)).mkdirs()) {
				message = "Directory for researcher was created succesfully";

			}else {
				message = "Reviewer directory was not created";
			}
			break;
		default:
			message = "User doesn't need to create custom folder"+ System.getProperty("line.separator")+ " or no user with that type exists";
			break;
		}
		
		return message;

	}
	
	

	


	//will be used when creating journals inside the editor page
	public String createJournalDir(String journalName) {
		String message;
		File createFolderDirFile = new File(System.getProperty("user.dir") + File.separator + "projectDB"+ File.separator + "editor" + File.separator + "journals" + File.separator +  journalName);
		if(!createFolderDirFile.exists()) {
		if(createFolderDirFile.mkdirs()) {
			message = journalName + " journal directory was created succesfully";

		}else {
			message = journalName + " journal directory was not created due to an error";
		}}
		else {
			message = "Journal Already Exists. Choose another name";
		}
		return message;
	}

	
}
