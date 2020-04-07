package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * Controller class for EditorV1AssignReviewer
 * 
 * implements and interface called Initializable when code is run, the class
 * runs the initialize() method
 */
public class EditorAssignReviewerController implements Initializable {

	// define FXML components
	@FXML
	public JFXComboBox<String> cbResearcher, cbReviewers; // ComboBox<DataType> behaves like a list
	@FXML
	private DatePicker dpMinorRev, dpRev1, dpRev2;
	@FXML
	private JFXButton btnGoBack, btnApproveRev, btnCheckJournal;
	@FXML
	private Label alert;

	// !!! Testing
	@FXML

	// define variables
	private List<String> researchersInJournal = new ArrayList<>();
	private List<String> reviewersInNomList = new ArrayList<>();
	private ObservableList<String> researcherList = FXCollections.observableArrayList();
	private ObservableList<String> reviewersList = FXCollections.observableArrayList();
	private String journalSelected;
	Utilities util = new Utilities();
	File mainPath = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator + "editor"
			+ File.separator + "journals" + File.separator);

	/**
	 * @param journalSelected
	 */
	public void setJournalSelected(String journalSelected) {
		this.journalSelected = journalSelected;
	}

	/**
	 * @return
	 */

	LocalDate ldMinorRev, ldRev1, ldRev2;

	// Initializes components
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbResearcher.setDisable(true);
		cbReviewers.setDisable(true);
		dpMinorRev.setDisable(true);
		dpRev1.setDisable(true);
		dpRev2.setDisable(true);
		btnGoBack.setDisable(true);
		btnApproveRev.setDisable(true);
		alert.setVisible(false);
	}

	private void fillResearcherComboBox() {
		File path = new File(
				mainPath + File.separator + journalSelected + File.separator + "researchers" + File.separator);
		researchersInJournal = Arrays.asList(util.listFilesInDir(path));
		researcherList.clear();
		for (int i = 0; i < researchersInJournal.size(); i++) {
			String elem = researchersInJournal.get(i);
			researcherList.add(i, elem);
			System.out.println(elem);
		}

		cbResearcher.setItems(researcherList);
	}

	public void fillReviewerComboBox(ActionEvent event) throws IOException {
		String researcherUsername = cbResearcher.getValue();
		String dataRead = "";
		File pathFile = new File(mainPath + File.separator + File.separator + journalSelected + File.separator
				+ "researchers" + File.separator + researcherUsername + File.separator + "nominatedReviewers.txt");
		if (!pathFile.exists()) {
			System.out.println("File doesnt exists " + pathFile);
//			alert.setStyle("-fx-text-fill:#d90024;");
//			alert.setText("Selected researcher hasn't Nominated reviewers" + System.getProperty("line.separator")
//					+ "Please select different Researcher if available ");
//			alert.setVisible(true);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No nominated reviewers");
			alert.setHeaderText(researcherUsername + " hasn't nominated reviewers");
			alert.setContentText("Please select different Researcher if available or contact " + researcherUsername
					+ " to submit his nominee(s)");

			alert.showAndWait();
		} else {
			System.out.println("File does exixst " + pathFile);
//			util.modifyNominatedRevFileStatus("ASSIGNED", researcherUsername, journalSelected);
			dataRead = util.readNomRevFile(researcherUsername, journalSelected);

//			System.out.println(dataRead);
			reviewersInNomList = Arrays.asList(dataRead.split(" "));
			reviewersList.clear();
			for (int i = 0; i < reviewersInNomList.size(); i++) {
//				if (i != 0) {
				String elem = reviewersInNomList.get(i);
				reviewersList.add(i, elem);
				System.out.println("Testing " + reviewersList.get(i));
//				}

			}
			reviewersList.remove(0);
			cbReviewers.setItems(reviewersList);
			cbReviewers.setDisable(false);

		}
	}

	public void checkJournal() {

		fillResearcherComboBox();

		cbResearcher.setDisable(false);
		// cbReviewers.setDisable(false);
//		dpMinorRev.setDisable(false);
//		dpRev1.setDisable(false);
//		dpRev2.setDisable(false);
		btnGoBack.setDisable(false);
//		btnApproveRev.setDisable(false);
	}

	/**
	 * 
	 * @param event
	 */
	public void AproveRev(ActionEvent event) {

	}

}
