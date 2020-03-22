package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Utilities {

	
	private FileChooser upload = new FileChooser();
	private FileChooser download = new FileChooser();
//	private ArrayList<File> storefiles = new ArrayList<>();
//	private HashMap<String, File> fileStorage = new HashMap<>();

	private String message;



	public String getMessage() {
		return message;
	}

	
	public Utilities() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private void copyFile(File source, File dest, boolean avoidDuplicate) throws IOException {
		if (avoidDuplicate)
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		else {
			Files.copy(source.toPath(), dest.toPath());

		}
	}

	//creates a user directory 
	public boolean createUserDir(String username, int type, String chosenJournal) {

		boolean error;

		switch (type) {

		case 1:
			// Researcher
			if ((new File(System.getProperty("user.dir") + File.separator + "projectDB"
					+ File.separator + "editor" + File.separator + "journals" + File.separator + chosenJournal + File.separator +"researchers" + File.separator + username))
					.mkdirs()) {
				message = "Directory for researcher was created succesfully";
				error = false;

			} else {
				message = "Reseacher directory was not created "+System.getProperty("line.separator")+"or a folder with that name already exists";
				error = true;
			}
			break;
		case 3:
			// Reviewer
			if ((new File(System.getProperty("user.dir") + File.separator + "projectDB"
					+ File.separator + "editor" + File.separator + "journals" + File.separator + chosenJournal + File.separator +"reviewers" + File.separator + username))
					.mkdirs()) {
				message = "Directory for researcher was created succesfully";
				error = false;

			} else {
				message = "Reviewer directory was not created "+System.getProperty("line.separator")+"or a folder with that name already exists";
				error = true;
			}
			break;
		default:
			message = "User doesn't need to create custom folder" + System.getProperty("line.separator")
					+ " or no user with that type exists";
			error = true;
			break;
		}

		return error;

	}
	
	// only used to write to journalList.txt
	public void writeJournalToFile(String journalName){
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB"
				+ File.separator + "editor" + File.separator + "journals" + File.separator + "journalList.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile,true));
			bw.write(journalName +System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {
			System.out.println("Error Saving DataBase.");
			e.printStackTrace();
		}
	}
	
	// only used to read from journalList.txt
	public String readJournalList() throws IOException {
		String journalList="";
		
		File readFromFile = new File(System.getProperty("user.dir") + File.separator + "projectDB"
				+ File.separator + "editor" + File.separator + "journals" + File.separator + "journalList.txt");
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(readFromFile));
			
			String line = "";
			
			System.out.println("Journals:");
			while ((line = br.readLine()) != null) {
				journalList +=line + " ";
				
			}
			br.close();
			if(journalList.isEmpty()) {
				System.out.println("There are no journals yet");
			}
			System.out.println("Inside Utilities the journals are: "+journalList);

			
		} catch (FileNotFoundException e) {
			System.out.println("Error Loading DataBase.");
			e.printStackTrace();
		}
		return journalList;
    }
	
	

	// only used to create journal folders
	public boolean createJournalDir(String journalName) {
		boolean error;
		if (!journalName.isEmpty()) {
			File createJournalFolderDir = new File(System.getProperty("user.dir") + File.separator + "projectDB"
					+ File.separator + "editor" + File.separator + "journals" + File.separator + journalName);
			if (!createJournalFolderDir.exists()) {
				if (createJournalFolderDir.mkdirs()) {
					writeJournalToFile(journalName);
					message = journalName + " journal directory was created succesfully";
					error = false;

				} else {
					message = journalName + " journal directory was not created due to an error";
					error = true;
				}
			} else {
				message = "Journal Already Exists. Choose another name";
				error = true;
			}
		} else {
			message = "Journal name can not be empty!";
			error = true;
		}
		return error;
	}
	
public void upload(File fileDestinPath) throws IOException {
		File source ;
		//Add filters for files extensions
		upload.getExtensionFilters().addAll(new ExtensionFilter("PDF File (.pdf)", "*.pdf"),
				new ExtensionFilter("Word Document (.dox):", "*.docx"));
		File selectedFile = upload.showOpenDialog(null);

		if (selectedFile != null) {
			String fileName = selectedFile.getName();
			//hardcode the directory where the uploaded files will be stored
			File localDest = new File(fileDestinPath + File.separator + fileName);

			//files.getItems().add(fileName);
			source = selectedFile.getAbsoluteFile();
			System.out.println("Source is : " + source + " dest is  " + localDest);
			copyFile(selectedFile, localDest, true);
			message = fileName+" file uploaded succesfully";
 


		} else {
			 System.out.println("Selectoin got cancelled");

		}

	}
	//testing the download function 
	//needs more work, as of now it only downloads the last uploaded file since source is a global variable
	//very easy to make it do more, just didnt have time
//	public void download(ActionEvent event) throws IOException {
//		String fileName = "";
//		File selectedPath;
//		download.setInitialFileName("myFile.pdf");
//		download.getExtensionFilters().addAll(
//				 new FileChooser.ExtensionFilter("All Files", "*"),
//				 new FileChooser.ExtensionFilter("pdf Files", "*.pdf"),
//			     new FileChooser.ExtensionFilter("Text Files", "*.txt")
//			    
//			);
//		File selectedFile = download.showSaveDialog(null);
//
//		if (files != null) {
//			destination = selectedFile.getAbsoluteFile();
//			if(fileStorage!=null) {
//				fileName = files.getSelectionModel().getSelectedItem();
//				selectedPath = fileStorage.get(fileName);
//				System.out.println("the selected path is " + selectedPath);
//				copyFile(selectedPath, destination, false);
//			}
//			else {
//				System.out.println("nothing to download");
//			}
//		} else {
//			System.out.println("Something happened");
//		}
////		if(!storefiles.isEmpty()) {
////			for (File file : storefiles) {
////				System.out.println(file);
////			}
////		}
//	}

}
