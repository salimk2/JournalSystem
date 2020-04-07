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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EditorReviewFinalSubmissionController implements Initializable {

	private String Journal;

	@FXML
	JFXButton btnDownload, btnAccept, btnReject, btnGoBack, btnGetResearchers;
	@FXML
	JFXComboBox<String> selectResearcher;
	@FXML
	Label alert;

	private List<String> researchersInJournal = new ArrayList<>();
	private ObservableList<String> researcherList = FXCollections.observableArrayList();
	Utilities util = new Utilities();
	File mainPath = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator + "editor"
			+ File.separator + "journals" + File.separator);

	public void setJournal(String journal) {
		Journal = journal;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnDownload.setDisable(true);
		btnAccept.setDisable(true);
		btnReject.setDisable(true);
		btnGoBack.setDisable(true);
		selectResearcher.setDisable(true);
		btnDownload.setVisible(false);
		btnAccept.setVisible(false);
		btnReject.setVisible(false);
		btnGoBack.setVisible(false);
		selectResearcher.setVisible(false);
		alert.setVisible(false);
		btnGetResearchers.setVisible(true);
	}

	public void getResearchers() {
		fillResearcherComboBox();
		btnGoBack.setDisable(false);
		selectResearcher.setDisable(false);
		btnDownload.setVisible(true);
		btnAccept.setVisible(true);
		btnReject.setVisible(true);
		btnGoBack.setVisible(true);
		selectResearcher.setVisible(true);
		btnGetResearchers.setVisible(false);
		btnGetResearchers.setDisable(true);
	}

	private void fillResearcherComboBox() {
		File path = new File(mainPath + File.separator + Journal + File.separator + "researchers" + File.separator);
		researchersInJournal = Arrays.asList(util.listFilesInDir(path));
		researcherList.clear();
		for (int i = 0; i < researchersInJournal.size(); i++) {
			String elem = researchersInJournal.get(i);
			researcherList.add(i, elem);
			System.out.println(elem);
		}

		selectResearcher.setItems(researcherList);
	}

	public void researcherWasSelected() {
		btnDownload.setDisable(false);
		btnAccept.setDisable(false);
		btnReject.setDisable(false);
	}

	/**
	 * btnSubFinal
	 */
	public void downloadFinalSub(ActionEvent event) throws IOException {
		alert.setVisible(false);
		System.out.println("Dowload was pressed");
		String username = selectResearcher.getValue();
		if (event.getSource() == btnDownload) {
			File fileToDownload = new File(
					System.getProperty("user.dir") + File.separator + "projectDB" + File.separator + "editor"
							+ File.separator + "journals" + File.separator + Journal + File.separator + "researchers"
							+ File.separator + username + File.separator + "FinalSubmission.pdf");
			if (fileToDownload.exists()) {
				btnAccept.setDisable(false);
				btnReject.setDisable(false);
				btnDownload.setDisable(true);
				util.download(fileToDownload);
				alert.setStyle("-fx-text-fill:#027d00");
				alert.setText("FinalSubmission.pdf has been succesfully downloaded");
				alert.setVisible(true);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("No Final Submision File to download");
				alert.setHeaderText(username + " has not submitted a Final Submission file");
				alert.setContentText("Please select different Researcher if available or contact " + username
						+ " to submit his Final Submission");

				alert.showAndWait();
				btnAccept.setDisable(true);
				btnReject.setDisable(true);

			}
		}
	}

	/**
	 * @throws IOException
	 */
	public void acceptSubmission() throws IOException {
		String username = selectResearcher.getValue();
		File acceptFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + Journal + File.separator + "researchers"
				+ File.separator + username + File.separator + "FinalSubmissionAccepted.txt");
		File rejectFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + Journal + File.separator + "researchers"
				+ File.separator + username + File.separator + "FinalSubmissionRejected.txt");
		if (!acceptFile.exists() && !rejectFile.exists()) {
			if (acceptFile.createNewFile()) {
				alert.setStyle("-fx-text-fill:#027d00");
				alert.setText("FinalSubmission.pdf has been Accepted");
				alert.setVisible(true);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Final Submision Couldn't be accepted");

				alert.setContentText(
						"Unknown Error,please close window and try again. If issue persists please contact IT.");

				alert.showAndWait();
				btnAccept.setDisable(true);
				btnReject.setDisable(true);
			}
		} else if (rejectFile.exists()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Final Submision Has already been rejected");

			alert.setContentText("This submission can't be aceepted because it was already marked as rejected");

			alert.showAndWait();
			btnAccept.setDisable(true);
			btnReject.setDisable(true);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Final Submision Has already been accepted");

			alert.setContentText("This submission can't be accepted because it was already marked as accepted");

			alert.showAndWait();
			btnAccept.setDisable(true);
			btnReject.setDisable(true);
		}
	}

	/**
	 * @throws IOException
	 */
	public void rejectSubmission() throws IOException {

		String username = selectResearcher.getValue();
		File acceptFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + Journal + File.separator + "researchers"
				+ File.separator + username + File.separator + "FinalSubmissionAccepted.txt");
		File rejectFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + Journal + File.separator + "researchers"
				+ File.separator + username + File.separator + "FinalSubmissionRejected.txt");
		if (!acceptFile.exists() && !rejectFile.exists()) {
			if (rejectFile.createNewFile()) {
				alert.setStyle("-fx-text-fill:#027d00");
				alert.setText("FinalSubmission.pdf has been Rejected");
				alert.setVisible(true);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Final Submision Couldn't be accepted");

				alert.setContentText(
						"Unknown Error,please close window and try again. If issue persists please contact IT.");

				alert.showAndWait();
				btnAccept.setDisable(true);
				btnReject.setDisable(true);
			}
		} else if (acceptFile.exists()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Final Submision Has already been accepted");

			alert.setContentText("This submission can't be rejected because it was already marked as accepted");

			alert.showAndWait();
			btnAccept.setDisable(true);
			btnReject.setDisable(true);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Final Submision Has already been rejected");

			alert.setContentText("This submission can't be rejected because it was already marked as rejected");

			alert.showAndWait();
			btnAccept.setDisable(true);
			btnReject.setDisable(true);
		}
	}

	/**
	 * Go back to main editor page
	 * 
	 * @param event
	 */
	@FXML
	public void goBack(ActionEvent event) {
		Stage stage = (Stage) btnGoBack.getScene().getWindow();

		stage.close();
	}

}
