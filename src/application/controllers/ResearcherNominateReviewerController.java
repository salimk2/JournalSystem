package application.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import application.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class ResearcherNominateReviewerController implements Initializable {

	private String researcherUsername;
	private String currentWorkingJournal;
	private Utilities util = new Utilities();

	@FXML
	JFXListView<String> reviewerList;
	@FXML
	JFXButton btnNominate, btnGoBack;
	@FXML
	Label message;

	private ArrayList<String> reviewersRead = new ArrayList<>();
	private ObservableList<String> list = FXCollections.observableArrayList();
	private ObservableList<String> chosenRevs;

	/**
	 * Used to get the username and currenttly selected journal from
	 * ResearcherController
	 * 
	 * @param username
	 * @param journalName
	 */
	public void setUserInfo(String username, String journalName) {
		researcherUsername = username;
		currentWorkingJournal = journalName;
	}

	/**
	 *
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		message.setVisible(false);
		// try to read reviewers from login.txt
		try {
			readReviewers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// add the reviewers found to the arraylist
		for (int i = 0; i < reviewersRead.size(); i++) {
			String j = reviewersRead.get(i);
			list.add(i, j);
		}

		reviewerList.setItems(list);
		reviewerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	/**
	 * 
	 */
	@FXML
	public void hideLabel() {

		message.setVisible(false);

	}

	/**
	 * @param event
	 */
	@FXML
	public void goBack(ActionEvent event) {
		Stage stage = (Stage) btnGoBack.getScene().getWindow();
//		System.out.println("The current user username is : " + researcherUsername);
//		System.out.println("the current journal is " + currentWorkingJournal);

		stage.close();
	}

	/**
	 * 
	 */
	@FXML
	public void nominateReviewer() {
		boolean error = displayNominateConfirmationMsg();

		if (!error) {
			reviewerList.getSelectionModel().clearSelection();
		} else {
//			System.out.println("Success to nominate");
			util.modifyNominatedRevFileStatus("PENDING", researcherUsername, currentWorkingJournal);

			for (String rev : chosenRevs) {
//				System.out.println(rev);
				util.writeNominatedRev(researcherUsername, rev, currentWorkingJournal);
//				System.out.println(rev);

			}

		}
	}

	/**
	 * @return
	 */
	private boolean displayNominateConfirmationMsg() {
		boolean success;
		chosenRevs = reviewerList.getSelectionModel().getSelectedItems();

		if (chosenRevs.size() >= 5) {
			success = false;
			message.setStyle("-fx-text-fill:#d90024");
			message.setText("Please select at most 4 reviewers");
			message.setVisible(true);

		} else if (chosenRevs.size() == 0) {
			success = false;
			message.setStyle("-fx-text-fill:#d90024");
			message.setText("Please select at least 1 reviewer");
			message.setVisible(true);

		} else {
			success = true;
			message.setStyle("-fx-text-fill:#027d00");
			message.setText("Reviewers were nominated succesfully");
			message.setVisible(true);

		}
		return success;

	}

	/**
	 * Reads and stores all the reviewers with an account *
	 * 
	 * @throws IOException
	 */
	private void readReviewers() throws IOException {

		File readFromLoginFile = new File(System.getProperty("user.dir") + File.separator + "Login.txt");
		try {

			BufferedReader read = new BufferedReader(new FileReader(readFromLoginFile));

			String line = "";
			String[] loginInfo = new String[10000];
//			System.out.println("These are the Researcher accounts:");
//			System.out.println();
			while ((line = read.readLine()) != null) {
				loginInfo = line.split(" ");

				if (Integer.parseInt(loginInfo[2]) == 3) {
					reviewersRead.add(loginInfo[0]);
					// System.out.println(loginInfo[0]);
				}

			}
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error reading login info.");
			e.printStackTrace();
		}
	}

}
