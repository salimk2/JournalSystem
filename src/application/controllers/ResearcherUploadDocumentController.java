package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import application.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResearcherUploadDocumentController implements Initializable {

	private String username;
	private int type;

	private Utilities util = new Utilities();
	private List<String> journals = new ArrayList<>();
	ObservableList<String> list = FXCollections.observableArrayList();
	ObservableList<String> subNum = FXCollections.observableArrayList("First", "Second", "Third", "Final");

	@FXML
	private JFXComboBox<String> availableJournals;
	@FXML
	private JFXComboBox<String> submissionNumber;

	@FXML
	private JFXButton btnCreateFolder;
	@FXML
	private JFXButton btnUploadToJournal;
	@FXML
	private JFXButton btnGoBack;
	@FXML
	private Label messageLabel;
	@FXML
	JFXButton btnUpload;

	public void setUser(String username, int type) {
		this.username = username;
		this.type = type;

	}

	/**
	 * Initializes needed components.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		readFile();
		for (int i = 0; i < journals.size(); i++) {
			String j = journals.get(i);
			// System.out.println("Initilaizer j :" + j);
			list.add(i, j);
		}
		System.out.println(list);
		// availableJournals.getItems().clear();

		availableJournals.setItems(list);
		submissionNumber.setItems(subNum);
		messageLabel.setVisible(false);
		// btnUploadToJournal.setDisable(true);

	}

	/**
	 * Goes back to the original window.
	 * 
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	@FXML
	public void goBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnGoBack.getScene().getWindow();
		stage.close();

	}

	/**
	 * Creates a personal folder for the current user.
	 */
	public void createUserFolder() {
		// btnUploadToJournal.setDisable(true);
		boolean dirError = false;
		if (!availableJournals.getSelectionModel().isEmpty()) {
			String chosenJournal = availableJournals.getValue();
			/* Create user personal folder */
			dirError = util.createUserDir(username, type, chosenJournal);
			if (!dirError) {

				messageLabel.setStyle("-fx-text-fill:#027d00;");

				messageLabel.setText(util.getMessage());
				messageLabel.setVisible(true);
				// btnUploadToJournal.setDisable(false);

			} else {
				messageLabel.setStyle("-fx-text-fill:#d90024;");

				messageLabel.setText(util.getMessage());
				messageLabel.setVisible(true);
			}
		} else {
			messageLabel.setStyle("-fx-text-fill:#d90024;");

			messageLabel.setText("Please select a journal first." + System.getProperty("line.separator")
					+ "If no Journal available please contact the Editor");
			messageLabel.setVisible(true);

		}
	}

	/** 
	 * 'Researcher' will upload a file to a specific folder located in
	 *  projectDb -> editor -> journals -> "Journal Name" -> "Researcher Username" 
	 *  -> "File resides in here".
	 *
	 * @throws IOException
	 */
	public void uploadDoc() throws IOException {

		/* Check for ComboBox element to be selected */
		if (!availableJournals.getSelectionModel().isEmpty() && !submissionNumber.getSelectionModel().isEmpty()) {
			String chosenJournal = availableJournals.getValue();
			String subVersionString = submissionNumber.getValue();
			boolean journalExists = util.checkResearcherFileExists(chosenJournal, username);

			if (journalExists) {
				/* Upload file */
				System.out.println("Thing created");
				File researcherPathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB"
						+ File.separator + "editor" + File.separator + "journals" + File.separator + chosenJournal
						+ File.separator + "researchers" + File.separator + username + File.separator);

				util.upload(researcherPathFile, subVersionString);

				messageLabel.setStyle("-fx-text-fill:#027d00;");

				messageLabel.setText(util.getMessage());
				messageLabel.setVisible(true);
			} else {
				messageLabel.setStyle("-fx-text-fill:#d90024;");

				messageLabel.setText("journal is Empty. Please create a user folder first");
				messageLabel.setVisible(true);
			}

		} else if (submissionNumber.getSelectionModel().isEmpty()) {
			messageLabel.setStyle("-fx-text-fill:#d90024;");

			messageLabel.setText("Please select a submission number.");
			messageLabel.setVisible(true);

		} else {
			messageLabel.setStyle("-fx-text-fill:#d90024;");

			messageLabel.setText("Please select a journal first." + System.getProperty("line.separator")
					+ "If no Journal available please contact the Editor");
			messageLabel.setVisible(true);

		}
	}

	/**
	 * Reads and stores the names of all created journals inside the 'Journals' file.
	 */
	private void readFile() {

		String journalList;
		try {
			journalList = util.readJournalList();
			journals = Arrays.asList(journalList.split(" "));
			for (int i = 0; i < journals.size(); i++) {

				String j = journals.get(i);

				System.out.println(j + " " + i);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File was not found, Can't read it");
			e.printStackTrace();
		}
	}

}
