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

import application.ReviewerRecord;
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
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for reviewer.fxml
 */
public class ReviewerController implements Initializable {
	
	private String username;
	private int type;
	
	private Pane content = new Pane();
	// configure table
	@FXML
	private TableView<ReviewerRecord> tableView; // configure TableView
	@FXML
	private TableColumn<ReviewerRecord, String> submissionColumn;
	@FXML
	private TableColumn<ReviewerRecord, String> researcherColumn;
	@FXML
	private TableColumn<ReviewerRecord, LocalDate> deadlineColumn;
	@FXML
	private TableColumn<ReviewerRecord, LocalDate> reviewColumn;
	@FXML
	private JFXButton btnDownloadSubmission, btnUploadReview;
	@FXML
	public JFXComboBox<String> cbJournals;
	@FXML
	private Label lblTest1, lblTest2;
	//@FXML
	//private FontAwesomeIcon //refreshIcon;

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

		////refreshIcon.setVisible(false);
		btnDownloadSubmission.setDisable(true);
		btnUploadReview.setDisable(true);

		// set columns
		researcherColumn.setCellValueFactory(new PropertyValueFactory<ReviewerRecord, String>("researcher"));
		submissionColumn.setCellValueFactory(new PropertyValueFactory<ReviewerRecord, String>("submission")); // instance
																																		// to look
																											// for:
																											// submission
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<ReviewerRecord, LocalDate>("deadline"));
		reviewColumn.setCellValueFactory(new PropertyValueFactory<ReviewerRecord, LocalDate>("Review"));

		// set table selection
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// !!! test - set labels
		// lblTest1.setText("journal: " + cbJournals.getValue());
		// lblTest2.setText("submission: none");

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


			try {
				File PathFile = new File(path + File.separator + j + File.separator + "researchers" + File.separator);

				if (PathFile != null) {
					String[] res = PathFile.list();

					for (int k = 0; k < res.length; k++) {

						File sub = new File(PathFile + File.separator + res[k]);

						if (sub != null) {

							String[] subs = sub.list();

							for (int l = 0; l < subs.length; l++) {
								// Set needed variables
								File getNominatedRev = new File(sub + File.separator + "nominatedReviewers.txt");
								// check if there is a reviewerDeadline.txt

								if (getNominatedRev.exists()) {
									// read the file and store its contents
									
									String[] getReviewerStrings = util.readNomRevFile(res[k], j).split(" ");
//									
									// check if the reviewer has been assigned and
									if (getReviewerStrings[0].contains("ASSIGNED")) {
										for (int rev = 1; rev < getReviewerStrings.length; rev++) {
											System.out.println("Nom " + rev + " is " + getReviewerStrings[rev]);
											if (getReviewerStrings[rev].matches(username)){
												journalsList.add(j);
												getReviewerStrings = null;
											}
										}
									}
								}

							}

						}
					}

				}

			} catch (Exception e) {
			}
			
		}
		// System.out.println(journalsList);
		// availableJournals.getItems().clear();

		
		
		cbJournals.setItems(journalsList);
		
		
	}

	@FXML
	// Added the refresh the combobox values
	// button
	public void update(MouseEvent event) {
		if(cbJournals.getItems().isEmpty()) {
			fillJournalComboBox();
		}
		
	}

	/**
	 * getRecords this method will return an ObersableList of People objects
	 * 
	 * @return ObservableList<ReviewerRecord>
	 */
	public ObservableList<ReviewerRecord> getRecords(String Journal) {
		// ObservaleList behaves like an ArrayList with feature for GUI environment
		ObservableList<ReviewerRecord> records = FXCollections.observableArrayList();

		try {
			File PathFile = new File(path + File.separator + Journal + File.separator + "researchers" + File.separator);

			if (PathFile != null) {
				String[] res = PathFile.list();

				for (int i = 0; i < res.length; i++) {

					File sub = new File(PathFile + File.separator + res[i]);

					if (sub != null) {

						String[] subs = sub.list();

						for (int j = 0; j < subs.length; j++) {
							// Set needed variables
							File deadlineFile = new File(sub + File.separator + "reviewerDeadlines.txt");
							// check if there is a reviewerDeadline.txt
							if (deadlineFile.exists()) {
								// read the file, and store its contents
								String[] deadLine = util.readRevDeadlines(res[i], Journal).split(" ");
								LocalDate rev1 = LocalDate.parse(deadLine[0]);
								LocalDate rev2 = LocalDate.parse(deadLine[1]);
								LocalDate rev3 = LocalDate.parse(deadLine[2]);
								
								if(subs[j].matches("FirstSubmission.pdf")) {
									records.add(new ReviewerRecord(res[i], subs[j], rev1, null));
								}
								if(subs[j].matches("SecondSubmission.pdf")) {
									records.add(new ReviewerRecord(res[i], subs[j], rev2, null));
								}
								if(subs[j].matches("ThirdSubmission.pdf")) {
									records.add(new ReviewerRecord(res[i], subs[j], rev3, null));
								}
								
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

		
		
		ObservableList<ReviewerRecord> checkList = getRecords(cbJournals.getValue());
		tableView.setItems(getRecords(cbJournals.getValue()));
		if (!checkList.isEmpty()) {
			btnUploadReview.setDisable(false);
			btnDownloadSubmission.setDisable(false);
		} else {
			btnUploadReview.setDisable(true);
			btnDownloadSubmission.setDisable(true);
		}

	}

	/**
	 * columnSelected method gets the submission file name
	 * 
	 * @param event
	 */
	public void columnSelected(MouseEvent event) {
		// create observable list of records type
		ObservableList<ReviewerRecord> records;
		records = tableView.getSelectionModel().getSelectedItems(); // gets row contents
		if (records.get(0) != null) { // ensures user selected available submission
			// lblTest2.setText("submission: " + records.get(0).getSubmission());
		}
	}

	// Logout
	@FXML
	public void logout(ActionEvent event) throws IOException {
		openNewBorderPaneWindow(event, "/application/Login.fxml");
	}


	
	public void downloadSubmission(ActionEvent event) throws IOException {
		if (event.getSource() == btnDownloadSubmission) {
			System.out.println("Download Clicked");
		}

	}



	
	public void uploadReview(ActionEvent event) throws IOException {
		if (event.getSource() == btnUploadReview) {
			System.out.println("Upload Clicked");
		}

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
