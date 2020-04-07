package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javafx.stage.Stage;

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
	private JFXButton btnGoBack, btnApproveRev, btnCheckJournal, btnGetResearchers;
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

	/**
	 * It fills the researcher combo box
	 * 
	 */
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

	/**
	 * @param event
	 * @throws IOException
	 */
	public void fillReviewerComboBox(ActionEvent event) throws IOException {
		String researcherUsername = cbResearcher.getValue();
		String dataRead = "";
		File pathFile = new File(mainPath + File.separator + File.separator + journalSelected + File.separator
				+ "researchers" + File.separator + researcherUsername + File.separator + "nominatedReviewers.txt");
		if (!pathFile.exists()) {
			System.out.println("File doesnt exists " + pathFile);

			// Added alerts cause they are more informative
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No nominated reviewers");
			alert.setHeaderText(researcherUsername + " hasn't nominated reviewers");
			alert.setContentText("Please select different Researcher if available or contact " + researcherUsername
					+ " to submit his nominee(s)");

			alert.showAndWait();
			cbResearcher.getValue();
		} else {
			System.out.println("File does exixst " + pathFile);

			dataRead = util.readNomRevFile(researcherUsername, journalSelected);

			if (dataRead.contains("ASSIGNED")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Reviewer has been Assigned");
				alert.setHeaderText(researcherUsername + " already has a reviewer previoulsy approved");
				alert.setContentText("Please select different Researcher if available ");
				alert.showAndWait();

			} else {
				reviewersInNomList = Arrays.asList(dataRead.split(" "));
				reviewersList.clear();
				for (int i = 0; i < reviewersInNomList.size(); i++) {
					String elem = reviewersInNomList.get(i);
					reviewersList.add(i, elem);
					System.out.println("Testing " + reviewersList.get(i));

				}
				reviewersList.remove(0);
				cbReviewers.setItems(reviewersList);
				cbReviewers.setDisable(false);
			}

		}
	}

	/**
	 * 
	 */
	public void selectedReviewer() {

		dpMinorRev.setDisable(false);
		dpRev1.setDisable(false);
		dpRev2.setDisable(false);
		btnApproveRev.setDisable(false);

	}

	/**
	 * 
	 */
	public void checkJournal() {

		fillResearcherComboBox();

		cbResearcher.setDisable(false);

		btnGoBack.setDisable(false);

	}

	/**
	 * Handle the file changes that have to happen when a reviewer gets approved
	 * 
	 * @param event
	 */
	/**
	 * @param event
	 */
	public void AproveRev(ActionEvent event) {
		String researcherUsername = cbResearcher.getValue();
		LocalDate rev1, rev2, rev3, timeNow = LocalDate.now();
		ArrayList<String> dateArrayList = new ArrayList<>();
		String reviewername = cbReviewers.getValue();

		// get the deadlines and store them in a file
		rev1 = dpRev1.getValue();
		rev2 = dpRev2.getValue();
		rev3 = dpMinorRev.getValue();

		if (rev1.isBefore(timeNow) || rev1.equals(timeNow)) {
			// first review date is before or equal to current date
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Date");
			alert.setHeaderText(
					rev1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " can't be set as deadline");
			alert.setContentText("First Revision Deadline can't be set equal or before today's date: "
					+ timeNow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
			alert.showAndWait();
			dpRev1.getEditor().clear();

		} else if (rev2.isBefore(rev1) || rev2.equals(timeNow) || rev2.isBefore(timeNow) || rev2.equals(rev1)) {
			// second review date is before or equal First review date or equal to current
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Date");
			alert.setHeaderText(
					rev2.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " can't be set as deadline");
			alert.setContentText("Second Revision Deadline can't be set equal or before today's date: "
					+ timeNow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
					+ System.getProperty("line.separator")
					+ "Also it can't be set equal or before First review's date: "
					+ rev1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
			alert.showAndWait();
			dpRev2.getEditor().clear();

		} else if (rev3.isBefore(rev2) || rev3.equals(rev2) || rev3.isBefore(rev1) || rev3.equals(rev1)
				|| rev3.equals(timeNow) || rev3.isBefore(timeNow)) {
			// second review date is before or equal First review date or equal to current
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Date");
			alert.setHeaderText(
					rev3.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " can't be set as deadline");
			alert.setContentText("Minor/Last Revision Deadline can't be set equal or before today's date: "
					+ timeNow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
					+ System.getProperty("line.separator")
					+ "Also it can't be set equal or before Second review's date:"
					+ rev2.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
					+ System.getProperty("line.separator") + "Also it can't be set equal or before First review's date:"
					+ rev1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
			alert.showAndWait();
			dpMinorRev.getEditor().clear();

		} else if (rev1 == null | rev2 == null | rev3 == null) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Date");
			alert.setHeaderText("Date can't be empty");
			alert.setContentText("Please select a date in order to continue");
			alert.showAndWait();

		}

		else {
			dateArrayList.add(rev1.toString());
			dateArrayList.add(rev2.toString());
			dateArrayList.add(rev3.toString());

			for (int i = 0; i < 3; i++) {
				util.writeRevDeadlines(researcherUsername, journalSelected, dateArrayList.get(i));
			}
			// change the PENDING to ASSIGNED status in nominatedReviewer.txt and add the
			// assigned reviewer
			util.modifyNominatedRevFileStatus("ASSIGNED", researcherUsername, journalSelected);
			util.writeNominatedRev(researcherUsername, reviewername, journalSelected);

			alert.setStyle("-fx-text-fill:#027d00");
			alert.setText(reviewername + " has been approved to review " + researcherUsername
					+ System.getProperty("line.separator") + " submissions for the " + journalSelected + " journal");
			alert.setVisible(true);
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
