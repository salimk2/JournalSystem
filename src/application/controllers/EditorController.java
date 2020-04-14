package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import application.EditorRecord;
import application.Utilities;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for EditorV1.fxml.
 */
public class EditorController implements Initializable {
	private Pane content = new Pane();
	/* Configure table */
	@FXML
	private TableView<EditorRecord> tableView; /* Configure 'TableView' */
	@FXML
	private TableColumn<EditorRecord, String> submissionColumn;
	@FXML
	private TableColumn<EditorRecord, String> reviewerColumn;
	@FXML
	private TableColumn<EditorRecord, String> researcherColumn;
	@FXML
	private TableColumn<EditorRecord, LocalDate> deadlineColumn;
	@FXML
	private Button btnAddJournal, btnReviewSubmissions, btnAssignReviewer;
	@FXML
	public JFXComboBox<String> cbJournals;
	@FXML
	private FontAwesomeIcon refreshIcon;

	private Utilities util = new Utilities();
	private List<String> journals = new ArrayList<>();
	private File path = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
			+ "editor" + File.separator + "journals");

	/* Define variables */
	ObservableList<String> journalsList = FXCollections.observableArrayList();

	/* Initializes components */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		refreshIcon.setVisible(false);
		btnAssignReviewer.setDisable(true);
		btnReviewSubmissions.setDisable(true);

		fillJournalComboBox();
		/* Set columns */
		researcherColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("researcher"));
		submissionColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("submission")); /* instance
																											// variable
																											// to look
																											// for: submission */
		reviewerColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("reviewer"));
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, LocalDate>("deadline"));

		/* set table selection */
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		/* !!! Test - Set labels */
		// lblTest1.setText("journal: " + cbJournals.getValue());
		// lblTest2.setText("submission: none");

	}

	/**
	 * Loads the available 'journalsList' into the Journal combo box.
	 */
	private void fillJournalComboBox() {
		String journalList;
		try {
			journalList = util.readJournalList();
			journals = Arrays.asList(journalList.split(" "));
			for (int i = 0; i < journals.size(); i++) {

				String j = journals.get(i);

				// System.out.println(j + " " + i);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File was not found, Can't read it");
			e.printStackTrace();
		}

		for (int i = 0; i < journals.size(); i++) {
			String j = journals.get(i);
			// System.out.println("Initilaizer j :" + j);
			journalsList.add(i, j);
		}
		// System.out.println(journalsList);
		// availableJournals.getItems().clear();

		cbJournals.setItems(journalsList);
	}

	@FXML
	/* Added the refresh the ComboBox values button */
	
	/* 
	 * Refreshes the page/ 
	 *
	 * @param click: Action taken by user.
	 */
	public void refreshPage(MouseEvent click) {
		journalsList.clear();
		fillJournalComboBox();
		refreshIcon.setVisible(false);

	}

	/**
	 * Returns an 'ObservableList' of 'People' objects.
	 * 
	 * @return ObservableList<EditorRecord>
	 */
	public ObservableList<EditorRecord> getRecords(String Journal) {
		/* ObservableList behaves like an ArrayList with feature for GUI environment */
		ObservableList<EditorRecord> records = FXCollections.observableArrayList();

		try {
			File PathFile = new File(path + File.separator + Journal + File.separator + "researchers" + File.separator);

			if (PathFile != null) {
				String[] res = PathFile.list();

				for (int i = 0; i < res.length; i++) {

					File sub = new File(PathFile + File.separator + res[i]);

					if (sub != null) {

						String[] subs = sub.list();

						for (int j = 0; j < subs.length; j++) {
							/* Set needed variables */
							File deadlineFile = new File(sub + File.separator + "reviewerDeadlines.txt");
							File getNominatedRev = new File(sub + File.separator + "nominatedReviewers.txt");
							String reviewer = "Not Assigned";
							/* check if there is a 'reviewerDeadline.txt' */
							if (deadlineFile.exists()) {
								/* Read the file, and store its contents */
								String[] deadLine = util.readRevDeadlines(res[i], Journal).split(" ");
								LocalDate rev1 = LocalDate.parse(deadLine[0]);
								LocalDate rev2 = LocalDate.parse(deadLine[1]);
								LocalDate rev3 = LocalDate.parse(deadLine[2]);

								/* Check if 'nominatedReviewers.txt' exists */
								if (getNominatedRev.exists()) {
									/* Read the file, and store its contents */
									String[] getReviewerStrings = util.readNomRevFile(res[i], Journal).split(" ");
//									
									/* Check if the reviewer has been assigned */
									if (getReviewerStrings[0].contains("ASSIGNED")) {
										reviewer = getReviewerStrings[1];
									}
								}
								/* If all conditions passe then store relevant values here */
								if (!subs[j].contains(".txt") && !subs[j].contains("Rev")) { /* Only used to ignore any '.txt' files */
									
									switch(subs[j]) {
									case "FirstSubmission.pdf":
										records.add(new EditorRecord(res[i], subs[j], reviewer, rev1));
										break;
									case "SecondSubmission.pdf":
										records.add(new EditorRecord(res[i], subs[j], reviewer, rev2));
										break;
									case "ThirdSubmission.pdf":
										records.add(new EditorRecord(res[i], subs[j], reviewer, rev3));
										break;
									default:
										break;
									}
									
								}
							} else {
								/* If there are no deadlines then most of the data will be generic */
								if (!subs[j].contains(".txt") && !subs[j].contains("Rev")) /* Only used to ignore any '.txt' files */
									records.add(new EditorRecord(res[i], subs[j], reviewer, null));
							}

						}

					}
				}

			}

		} catch (Exception e) {
			records.clear();
		}

		return records;
	}

	/**
	 * Gets the current journal's name.
	 * 
	 * @param event: Event component that does a desired action when pressed.
	 */
	public void journalSelected(ActionEvent event) {

		ObservableList<EditorRecord> checkList = getRecords(cbJournals.getValue());
		tableView.setItems(getRecords(cbJournals.getValue()));
		if (!checkList.isEmpty()) {
			btnAssignReviewer.setDisable(false);
			btnReviewSubmissions.setDisable(false);
		} else {
			btnAssignReviewer.setDisable(true);
			btnReviewSubmissions.setDisable(true);
		}

	}

	/**
	 * Gets the Submission file name.
	 * 
	 * @param event: Event component that does a desired action when pressed.
	 */
	public void columnSelected(MouseEvent event) {
		/* Create 'ObservableList' of records type */
		ObservableList<EditorRecord> records;
		records = tableView.getSelectionModel().getSelectedItems(); /* Gets row contents */
		if (records.get(0) != null) { /* Ensures user selected available submission */
			// lblTest2.setText("submission: " + records.get(0).getSubmission());
		}
	}

	/* Logout */
	@FXML
	public void logout(ActionEvent event) throws IOException {
		openNewBorderPaneWindow(event, "/application/Login.fxml");
	}

	/* Creating pop up window */
	@FXML
	public void addJournal(ActionEvent event) throws IOException {
		refreshIcon.setVisible(true);

		Stage stage;
		Parent root;

		if (event.getSource() == btnAddJournal) {
			stage = new Stage();
			/* Reference Editor page */
			root = FXMLLoader.load(getClass().getResource("/application/EditorAddJournalPopUp.fxml"));
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(btnAddJournal.getScene().getWindow());
			stage.showAndWait();
		}

	}

	/**
	 * Handles and opens a window when the 'Assign Reviewer' button is pressed.
	 * 
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void AssignReviewer(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;

		if (event.getSource() == btnAssignReviewer) {
			String journalSelected = cbJournals.getValue();

			stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/EditorAssignReviewer.fxml"));
			/* Reference Editor page */
			root = loader.load();
			EditorAssignReviewerController controller = loader.getController();
			controller.setJournalSelected(journalSelected);

			;
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(btnAssignReviewer.getScene().getWindow());
			stage.showAndWait();
		}

	}

	/**
	 * Handles and opens a window when the 'Review Submission' button is pressed.
	 * 
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void reviewSubmission(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;

		if (event.getSource() == btnReviewSubmissions) {
			String journalSelected = cbJournals.getValue();

			stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/EditorReviewFinalSubmission.fxml"));
			/* Reference Editor page */
			root = loader.load();
			EditorReviewFinalSubmissionController controller = loader.getController();
			controller.setJournal(journalSelected);

			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(btnReviewSubmissions.getScene().getWindow());
			stage.showAndWait();
		}
	}

	/* Switch Windows (BorderPane) */
	public void openNewBorderPaneWindow(ActionEvent event, String newWindow) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(newWindow));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
