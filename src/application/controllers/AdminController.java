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

import application.AdminRecord;
import application.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller class for reviewer.fxml
 */
public class AdminController implements Initializable {

	private String username;
	private int type;
	private File active;

	public static final String FIRST = "First";
	public static final String SECOND = "Second";
	public static final String THIRD = "Third";

	private Pane content = new Pane();
	// configure table
	@FXML
	private TableView<AdminRecord> tableView; // configure TableView
	@FXML
	private TableColumn<AdminRecord, String> submissionColumn;
	@FXML
	private TableColumn<AdminRecord, String> researcherColumn;
	@FXML
	private TableColumn<AdminRecord, LocalDate> reviewerColumn;
	@FXML
	private TableColumn<AdminRecord, LocalDate> reviewColumn;
	@FXML
	private JFXButton btnDownloadSubmission, btnDownloadReview;
	@FXML
	public JFXComboBox<String> cbJournals;
	@FXML
	private Label selected;
	// @FXML
	// private FontAwesomeIcon //refreshIcon;

	private Utilities util = new Utilities();
	private List<String> journals = new ArrayList<>();
	private File path = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
			+ "editor" + File.separator + "journals");

	// define variables
	ObservableList<String> journalsList = FXCollections.observableArrayList();

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	public void initUser(String username, int type) {
		setUsername(username);
		setType(type);
	}

	/// Initializes components
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//// refreshIcon.setVisible(false);
		btnDownloadSubmission.setDisable(true);
		btnDownloadReview.setDisable(true);
		btnDownloadReview.setVisible(false);

		// set columns
		researcherColumn.setCellValueFactory(new PropertyValueFactory<AdminRecord, String>("researcher"));
		submissionColumn.setCellValueFactory(new PropertyValueFactory<AdminRecord, String>("submission")); // instance
																											// to look
																											// for:
																											// submission
		reviewerColumn.setCellValueFactory(new PropertyValueFactory<AdminRecord, LocalDate>("reviewer"));
		reviewColumn.setCellValueFactory(new PropertyValueFactory<AdminRecord, LocalDate>("review"));

		// set table selection
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		selected.setText("Submission: ");

	}

	/**
	 * 
	 */
	private void fillJournalComboBox() {

		String journalList;
		try {
			journalList = util.readJournalList();
			journals = Arrays.asList(journalList.split(" "));

		} catch (IOException e) {
			System.out.println("File was not found, Can't read it");
			e.printStackTrace();
		}

		for (int i = 0; i < journals.size(); i++) {
			String j = journals.get(i);
			journalsList.add(j);
		}
		// System.out.println(journalsList);
		// availableJournals.getItems().clear();
		cbJournals.setItems(journalsList);
	}

	@FXML
	// Added the refresh the combobox values
	// button
	public void update(MouseEvent event) {
		if (cbJournals.getItems().isEmpty()) {
			fillJournalComboBox();
		}

	}

	/**
	 * getRecords this method will return an ObersableList of People objects
	 * 
	 * @return ObservableList<AdminRecord>
	 */
	public ObservableList<AdminRecord> getRecords(String Journal) {
		// ObservaleList behaves like an ArrayList with feature for GUI environment
		ObservableList<AdminRecord> records = FXCollections.observableArrayList();

		try {
			File PathFile = new File(path + File.separator + Journal + File.separator + "researchers" + File.separator);

			if (PathFile != null) {
				String[] res = PathFile.list();

				for (int i = 0; i < res.length; i++) {

					File sub = new File(PathFile + File.separator + res[i]);

					if (sub != null) {

						String[] subs = sub.list();

						for (int j = 0; j < subs.length; j++) {

							File nomFile = new File(sub + File.separator + "nominatedReviewers.txt");

							if (nomFile.exists()) {

								String[] nom = util.readNomRevFile(res[i], Journal).split(" ");

								File reviewFile = new File(sub + File.separator + "reviewerReviews.txt");

								LocalDate[] rev = { null, null, null };

								if (reviewFile.exists()) {
									String[] review = util.readRevReviews(res[i], Journal).split(" ");
									try {
										rev[0] = LocalDate.parse(review[0]);
									} catch (Exception e) {
										;
									}
									try {
										rev[1] = LocalDate.parse(review[1]);
									} catch (Exception e) {
										;
									}
									try {
										rev[2] = LocalDate.parse(review[2]);
									} catch (Exception e) {
										;
									}
								}

								if (subs[j].matches("FinalSubmission.pdf")) {
									records.add(new AdminRecord(res[i], subs[j], nom[1], rev[0]));
								}
//								if(subs[j].matches("SecondSubmission.pdf")) {
//									records.add(new AdminRecord(res[i], subs[j], nom[1], rev[1]));
//								}
//								if(subs[j].matches("ThirdSubmission.pdf")) {
//									records.add(new AdminRecord(res[i], subs[j], nom[1], rev[2]));
//								}

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
	 * journal selected method gets the journal name
	 * 
	 * @param event
	 */
	public void journalSelected(ActionEvent event) {
		tableView.setItems(getRecords(cbJournals.getValue()));
		active = null;
		selected.setText("Submission: ");
		btnDownloadSubmission.setDisable(true);

	}

	/**
	 * columnSelected method gets the submission file name
	 * 
	 * @param event
	 */
	public void columnSelected(MouseEvent event) {
		// create observable list of records type
		ObservableList<AdminRecord> records;
		records = tableView.getSelectionModel().getSelectedItems(); // gets row contents
		if (records.get(0) != null && cbJournals.getValue() != null) { // ensures user selected available submission
			selected.setText("Submission: " + records.get(0).getSubmission());
			active = new File(
					path + File.separator + cbJournals.getValue() + File.separator + "researchers" + File.separator
							+ records.get(0).getResearcher() + File.separator + records.get(0).getSubmission());
			btnDownloadSubmission.setDisable(false);

		}
	}

	// Logout
	@FXML
	public void logout(ActionEvent event) throws IOException {
		openNewBorderPaneWindow(event, "/application/Login.fxml");
	}

	public void downloadSubmission(ActionEvent event) throws IOException {
		// System.out.println("Download Clicked");
		if (active != null) {
			util.download(active);
		}
	}

	public void downloadReview(ActionEvent event) throws IOException {
		// System.out.println("Upload Clicked");
		util.download(new File(active.getParentFile() + File.separator + "Rev" + active.getName()));
	}

	// Switch Windows (BorderPane)
	public void openNewBorderPaneWindow(ActionEvent event, String newWindow) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(newWindow));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
