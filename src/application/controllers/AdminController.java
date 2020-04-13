package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.EditorRecord;
import application.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminController implements Initializable {

	/* Declare components */
	@FXML
	public ComboBox<String> cbJournals; /* Configure 'ComboBox' */
	@FXML
	private TableView<EditorRecord> tableView; /* Configure 'TableView' */
	@FXML
	private TableColumn<EditorRecord, String> submissionColumn;
	@FXML
	private TableColumn<EditorRecord, String> reviewerColumn;
	@FXML
	private TableColumn<EditorRecord, String> researcherColumn;
	@FXML
	private TableColumn<EditorRecord, LocalDate> minorRevisionColumn;
	@FXML
	private TableColumn<EditorRecord, LocalDate> revision1Column;
	@FXML
	private TableColumn<EditorRecord, LocalDate> revision2Column;
	@FXML
	private Label lblTest1, lblTest2; /* !!! Testing */

	private Utilities util = new Utilities();
	private List<String> journals = new ArrayList<>();
	private File path = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
			+ "editor" + File.separator + "journals");

	/* Define variables */
	ObservableList<String> journalsList = FXCollections.observableArrayList();;

	/**
	 * Initializes the components.
	 * 
	 * @param location: Resolve the relative paths for the root object, or NULL if the location is unknown.
	 * @param resources: Localize the root object, or NULL if the root object wasn't localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

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

		/* Set columns */
		researcherColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("researcher"));
		submissionColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("submission")); /* Instance
																											// variable
																											// to look
																											// for: submission */
		reviewerColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("reviewer"));
		minorRevisionColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, LocalDate>("minorRevision"));
		revision1Column.setCellValueFactory(new PropertyValueFactory<EditorRecord, LocalDate>("revision1"));
		revision2Column.setCellValueFactory(new PropertyValueFactory<EditorRecord, LocalDate>("revision2"));

		/* Set table selection */
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		/* !!! Test - Set labels */
		lblTest1.setText("journal: " + cbJournals.getValue());
		lblTest2.setText("submission: none");

	}

	/**
	 * Returns an 'ObservableList' of 'People' objects.
	 *
	 * @param Journal: The name of the journal being used. 
	 * @return ObservableList<EditorRecord>
	 */
	public ObservableList<EditorRecord> getRecords(String Journal) {
		// ObservaleList behaves like an ArrayList with feature for GUI environment
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
							File deadlineFile = new File(sub + File.separator + "reviewerDeadlines.txt");
							File getNominatedRev = new File(sub + File.separator + "nominatedReviewers.txt");
							String reviewer = "Not Assigned";
							if (deadlineFile.exists()) {
								String[] deadLine = util.readRevDeadlines(res[i], Journal).split(" ");
								LocalDate rev1 = LocalDate.parse(deadLine[0]);
								LocalDate rev2 = LocalDate.parse(deadLine[1]);
								LocalDate rev3 = LocalDate.parse(deadLine[2]);

								if (getNominatedRev.exists()) {
									String[] getReviewerStrings = util.readNomRevFile(res[i], Journal).split(" ");
//									
									if (getReviewerStrings[0].contains("ASSIGNED")) {
										System.out.println("you passed assigned");
										reviewer = getReviewerStrings[1];
									}
								}

								if (!subs[j].contains(".txt")) // only used to ignore any txt files
									records.add(new EditorRecord(res[i], subs[j], reviewer, rev1, rev2, rev3));

							} else {
								if (!subs[j].contains(".txt")) // only used to ignore any txt files
									records.add(new EditorRecord(res[i], subs[j], reviewer, null, null, null));
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
	 * Gets the appropriate Journal name.
	 * 
	 * @param event: Component that does a desired action when pressed.
	 */
	public void journalSelected(ActionEvent event) {

		lblTest1.setText("journal: " + cbJournals.getValue());

		tableView.setItems(getRecords(cbJournals.getValue()));
	}

	/**
	 * Gets the Submission file name.
	 * 
	 * @param event: Component that does a desired action when pressed.
	 */
	public void columnSelected(MouseEvent event) {
		/* Create 'ObservableList' of records type */
		ObservableList<EditorRecord> records;
		records = tableView.getSelectionModel().getSelectedItems(); /* Get row contents */
		if (records.get(0) != null) { /* Ensures the user selected available submission */
			lblTest2.setText("submission: " + records.get(0).getSubmission());
		}
	}

	/**
	 * Permits to log out for the application. 
	 * 
	 * @param event: Component that does a desired action when pressed.
	 */
	public void logout(ActionEvent event) throws IOException {
		openNewWindow(event, "/application/Login.fxml");
	}

	// open new window (BorderPane)

	/**
	 * Permits to open new window (BorderPane).
	 * 
	 * @param event: Component that does a desired action when pressed.
	 * @param pageName: This will be the name of the new window. 
	 */
	public void openNewWindow(ActionEvent event, String pageName) throws IOException {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource(pageName));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}
}
