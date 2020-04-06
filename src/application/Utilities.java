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

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Utilities {

	private FileChooser upload = new FileChooser();
	private DirectoryChooser download = new DirectoryChooser();
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

	/**
	 * Only used to modify the status to the first line of this file
	 */

	public void modifyNominatedRevFileStatus(String status, String username, String journalName) {
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator + "nominatedReviewers.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile, false));
			bw.write(status + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {
			System.out.println("Error! Couldn't write to file");
			e.printStackTrace();
		}

	}

	public void writeNominatedRev(String username, String reviewername, String journalName) {
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator + "nominatedReviewers.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile, true));
			bw.write(reviewername + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {
			System.out.println("Error Saving DataBase.");
			e.printStackTrace();
		}
	}

	private void copyFile(File source, File dest, boolean avoidDuplicate) throws IOException {
		if (avoidDuplicate)
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		else {
			Files.copy(source.toPath(), dest.toPath());

		}
	}

	/**
	 * Lists all directories inside a directory and return a list containing those
	 * elements names
	 * 
	 * @param path
	 * @return
	 */
	public String[] listFilesInDir(File path) {
		String[] list = {};

		if (path.exists()) {
			list = path.list();

		} else {
			System.out.println("Error! path doesnt exists");
		}
		return list;
	}

	/**
	 * read nominatedReviewers file
	 * 
	 * @throws IOException
	 */
	public String readNomRevFile(String researcherUsername, String journalName) throws IOException {
		String reviewer = "";
		File readFromFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + researcherUsername + File.separator + "nominatedReviewers.txt");
		try {

			BufferedReader br = new BufferedReader(new FileReader(readFromFile));

			String line = "";

			// System.out.println("Journals:");
			while ((line = br.readLine()) != null) {
				reviewer += line + " ";

			}
			br.close();
			if (reviewer.isEmpty()) {
				System.out.println("There are no journals yet");
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error Loading DataBase.");
			e.printStackTrace();
		}
		return reviewer;

	}

	/**
	 * Checks if a researcher has a folder for a specified journal and returns the
	 * boolean
	 * 
	 * @param journalName
	 * @param username
	 * @return
	 */
	public boolean checkResearcherFileExists(String journalName, String username) {
		boolean exists = false;
		File journal = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username);
		exists = journal.exists() ? true : false;
		if (exists)
			System.out.println("File exists :" + journalName);
		else
			System.out.println("FIle doesnt exists");

		return exists;
	}

	// creates a user directory
	public boolean createUserDir(String username, int type, String chosenJournal) {

		boolean error;

		switch (type) {

		case 1:
			// Researcher
			if ((new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator + "editor"
					+ File.separator + "journals" + File.separator + chosenJournal + File.separator + "researchers"
					+ File.separator + username)).mkdirs()) {
				message = "Directory for researcher was created succesfully";
				error = false;

			} else {
				message = "Reseacher directory was not created " + System.getProperty("line.separator")
						+ "or a folder with that name already exists";
				error = true;
			}
			break;
		case 3:
			// Reviewer
			if ((new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator + "editor"
					+ File.separator + "journals" + File.separator + chosenJournal + File.separator + "reviewers"
					+ File.separator + username)).mkdirs()) {
				message = "Directory for researcher was created succesfully";
				error = false;

			} else {
				message = "Reviewer directory was not created " + System.getProperty("line.separator")
						+ "or a folder with that name already exists";
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
	public void writeJournalToFile(String journalName) {
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + "journalList.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile, true));
			bw.write(journalName + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {
			System.out.println("Error Saving DataBase.");
			e.printStackTrace();
		}
	}

	// only used to read from journalList.txt
	public String readJournalList() throws IOException {
		String journalList = "";

		File readFromFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + "journalList.txt");
		try {

			BufferedReader br = new BufferedReader(new FileReader(readFromFile));

			String line = "";

			// System.out.println("Journals:");
			while ((line = br.readLine()) != null) {
				journalList += line + " ";

			}
			br.close();
			if (journalList.isEmpty()) {
				System.out.println("There are no journals yet");
			}
			// System.out.println("Inside Utilities the journals are: "+journalList);

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

	public void upload(File fileDestinPath, String subVersion) throws IOException {
		File source;
		// Add filters for files extensions
		upload.getExtensionFilters().addAll(new ExtensionFilter("PDF File (.pdf)", "*.pdf"));
		File selectedFile = upload.showOpenDialog(null);

		if (selectedFile != null) {
			String ext = ".pdf";
			String fileName = subVersion + "Submission" + ext;

			File localDest = new File(fileDestinPath + File.separator + fileName);

			// files.getItems().add(fileName);
			source = selectedFile.getAbsoluteFile();
			System.out.println("Source is : " + source + " dest is  " + localDest);
			copyFile(selectedFile, localDest, true);
			message = fileName + " file uploaded succesfully";

		} else {
			System.out.println("Selectoin got cancelled");

		}

	}

	public void download(File fileOriginPath) throws IOException {

		Stage stage = null;
		File dest = download.showDialog(stage);

		if (dest != null) {
			String fileName = fileOriginPath.getName();
			// hardcode the directory where the uploaded files will be stored
			File fileDest = new File(dest + File.separator + fileName);

			// files.getItems().add(fileName);
			File file = fileOriginPath.getAbsoluteFile();
			System.out.println("Source is : " + file + " dest is  " + fileDest);

			// set the flag to true to allow overriding files with the same name
			copyFile(file, fileDest, true);

			System.out.println("Downloaded!");

		} else {
			System.out.println("Selectoin got cancelled");

		}
	}
}
