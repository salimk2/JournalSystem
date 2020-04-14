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

/**
 * Utilities
 */
public class Utilities {

	private FileChooser upload = new FileChooser();
	private DirectoryChooser download = new DirectoryChooser();
	private String message;

	public String getMessage() {
		return message;
	}

	public Utilities() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Only used to modify the status to the first line of this file.
	 * 
	 * @param researcherUsername: The name of the current researcher.
	 * @param journalName:        The name of the journal.
	 *
	 * @return deadline:          The journal with the modified line.
	 */
	public String readRevDeadlines(String researcherUsername, String journalName) throws IOException {
		String deadline = "";
		File readFromFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + researcherUsername + File.separator + "reviewerDeadlines.txt");
		try {

			BufferedReader br = new BufferedReader(new FileReader(readFromFile));

			String line = "";

			while ((line = br.readLine()) != null) {
				deadline += line + " ";

			}
			br.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return deadline;
	}

	/**
	 * Writing to the file with the deadline (Reviewer).
	 * 
	 * @param username:    The username of the current user.
	 * @param journalName: The name of the journal.
	 * @param date:        The current date of the deadline.
	 */
	public void writeRevDeadlines(String username, String journalName, String date) {
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator + "reviewerDeadlines.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile, true));
			bw.write(date + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * This method makes it possible to read what the reviewer written reviews.
	 * 
	 * @param researcherUsername: The username of the researcher.
	 * @param journalName:        The journal name.
	 *
	 * @return review:			  Review to be written.
	 */
	public String readRevReviews(String researcherUsername, String journalName) throws IOException {
		String review = "";
		File readFromFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + researcherUsername + File.separator + "reviewerReviews.txt");
		try {

			BufferedReader br = new BufferedReader(new FileReader(readFromFile));

			String line = "";

			while ((line = br.readLine()) != null) {
				review += line + " ";

			}
			br.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return review;
	}

	/**
	 * This method will make the reviewer able to write his/her reviews.
	 * 
	 * @param date:        this will be the date on which it was written.
	 * @param username:    The username of the current user.
	 * @param journalName: The journal name.
	 * @param rev:         Will be update as the application runs to determine
	 *                     reviewer dates.
	 */
	public void writeRevReviews(String username, String journalName, String date, int rev) {
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator + "reviewerReviews.txt");

		String[] revDates = { null, null, null };

		if (writeToFile.exists()) {
			try {
				revDates = readRevReviews(username, journalName).split(" ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		revDates[rev] = date;

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile, false));
			for (int i = 0; i < 3; i++) {
				bw.write(revDates[i] + System.getProperty("line.separator"));
			}

			bw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Modifying the reviewer file status.
	 * 
	 * @param status:      The file status (Reviewer).
	 * @param username:    The username of the current user.
	 * @param journalName: The journal name.
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

			e.printStackTrace();
		}

	}

	/**
	 * Write the reviewer nominations.
	 * 
	 * @param username:     The username of the current user.
	 * @param reviewername: The name for the reviewer.
	 * @param journalName:  The journal name.
	 */
	public void writeNominatedRev(String username, String reviewername, String journalName) {
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator + "nominatedReviewers.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile, true));
			bw.write(reviewername + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * This methods copies a selected file to a new destination.
	 * 
	 * @param source:         The file that needs to be copied.
	 * @param dest:           The new destination of the copied file.
	 * @param avoidDuplicate: To determine if the file already exists.
	 *
	 * @throws IOException
	 */
	private void copyFile(File source, File dest, boolean avoidDuplicate) throws IOException {
		if (avoidDuplicate)
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		else {
			Files.copy(source.toPath(), dest.toPath());

		}
	}

	/**
	 * Lists all directories inside a directory and return a list containing those
	 * elements names.
	 * 
	 * @param path:  The path to the file.
	 *
	 * @return list: List of all the directories.
	 */
	public String[] listFilesInDir(File path) {
		String[] list = {};

		if (path.exists()) {
			list = path.list();

		}
		return list;
	}

	/**
	 * Read 'nominatedReviewers' file.
	 * 
	 * @param researcherUsername: This will be the username for the researcher.
	 * @param journalName:        This will be the name of the journal.
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

			while ((line = br.readLine()) != null) {
				reviewer += line + " ";

			}
			br.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return reviewer;

	}

	/**
	 * Checks if a 'Researcher' has a folder for a specified journal and returns the
	 * boolean.
	 * 
	 * @param journalName The journal name.
	 * @param username    The username of the current user.
	 * @return exists:    If file exist else False if file does not exist.
	 */
	public boolean checkResearcherFileExists(String journalName, String username) {
		boolean exists = false;
		File journal = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username);
		exists = journal.exists() ? true : false;

		return exists;
	}

	/**
	 * This method creates a researcher directory.
	 * 
	 * @param username:      The username of the current user.
	 * @param type:          The account type.
	 * @param chosenJournal: The name of the selected journal.
	 * @return error:        True if an error was encountered and False if the researcher was
	 *                       created successfully.
	 */
	public boolean createUserDir(String username, int type, String chosenJournal) {

		boolean error;

		switch (type) {

		case 1:
			/* Researcher */
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
			/* Reviewer */
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

	/**
	 * This method is only used to write to journalList.txt.
	 * 
	 * @param journalName: The name of the journal being used.
	 */
	public void writeJournalToFile(String journalName) {
		File writeToFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + "journalList.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile, true));
			bw.write(journalName + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * This method is only used to read from journalList.txt.
	 * 
	 * @return journalList: The data read from the journal.
	 *
	 * @throws IOException
	 */
	public String readJournalList() throws IOException {
		String journalList = "";

		File readFromFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + "journalList.txt");
		try {

			BufferedReader br = new BufferedReader(new FileReader(readFromFile));

			String line = "";

			while ((line = br.readLine()) != null) {
				journalList += line + " ";

			}
			br.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return journalList;
	}

	/**
	 * This method is used to create journal folders.
	 * 
	 * @param journalName: The name of the journal being used.
	 *
	 * @return error:      True if an error occurred creating the journal directory and false
	 *                     for no errors.
	 */
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

	/**
	 * This methods uploads a file to a selected destination.
	 * 
	 * @param fileDestinPath: The destination of the file that will be uploaded.
	 * @param subVersion:     The submission version.
	 *
	 * @throws IOException
	 */
	public void upload(File fileDestinPath, String subVersion) throws IOException {
		File source;
		// Add filters for files extensions
		upload.getExtensionFilters().addAll(new ExtensionFilter("PDF File (.pdf)", "*.pdf"));
		File selectedFile = upload.showOpenDialog(null);

		if (selectedFile != null) {
			String ext = ".pdf";
			String fileName = subVersion + "Submission" + ext;

			File localDest = new File(fileDestinPath + File.separator + fileName);

			source = selectedFile.getAbsoluteFile();
			copyFile(selectedFile, localDest, true);
			message = fileName + " file uploaded succesfully";

		}

	}

	/**
	 * Downloads a corresponding file.
	 * 
	 * @param fileOriginPath: This is the path that the original file is located at.
	 *
	 * @throws IOException
	 */
	public void download(File fileOriginPath) throws IOException {

		Stage stage = null;
		File dest = download.showDialog(stage);

		if (dest != null) {
			String fileName = fileOriginPath.getName();
			/* Hardcode the directory where the uploaded files will be stored */
			File fileDest = new File(dest + File.separator + fileName);

			// files.getItems().add(fileName);
			File file = fileOriginPath.getAbsoluteFile();

			/* Set the flag to true to allow overriding files with the same name */
			copyFile(file, fileDest, true);

		}
	}
}