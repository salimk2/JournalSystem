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
 * ReviewerController
 * 
 * Controller class for reviewer.fxml
 */
public class ReviewerController implements Initializable {
	
	private String username;
	private int type;
	private File active;
	
	public static final String FIRST = "First";
	public static final String SECOND = "Second";
	public static final String THIRD = "Third";
	
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
	private Label selected;

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
								LocalDate[] dead = {LocalDate.parse(deadLine[0]),LocalDate.parse(deadLine[1]),LocalDate.parse(deadLine[2])};
								
								File reviewFile = new File(sub + File.separator + "reviewerReviews.txt");
								
								LocalDate[] rev = {null,null,null};
								
								if (reviewFile.exists()) {
									String[] review = util.readRevReviews(res[i], Journal).split(" ");
									try {
										rev[0] = LocalDate.parse(review[0]);
									} catch (Exception e) {;}
									try {
										rev[1] = LocalDate.parse(review[1]);
									} catch (Exception e) {;}
									try {
										rev[2] = LocalDate.parse(review[2]);
									} catch (Exception e) {;}
								}
								
								
								if(subs[j].matches("FirstSubmission.pdf")) {
									records.add(new ReviewerRecord(res[i], subs[j], dead[0], rev[0]));
								}
								if(subs[j].matches("SecondSubmission.pdf")) {
									records.add(new ReviewerRecord(res[i], subs[j], dead[1], rev[1]));
								}
								if(subs[j].matches("ThirdSubmission.pdf")) {
									records.add(new ReviewerRecord(res[i], subs[j], dead[2], rev[2]));
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
		tableView.setItems(getRecords(cbJournals.getValue()));
		active = null;
		selected.setText("Submission: ");
		btnDownloadSubmission.setDisable(true);
		btnUploadReview.setDisable(true);
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
		if (records.get(0) != null && cbJournals.getValue() != null) { // ensures user selected available submission
			 selected.setText("Submission: " + records.get(0).getSubmission());
			 active = new File(path + File.separator + cbJournals.getValue() + File.separator + "researchers" + File.separator + records.get(0).getResearcher() + File.separator + records.get(0).getSubmission());
			 btnDownloadSubmission.setDisable(false);
			 
			 //Prevents re-upload of a review
			 if(records.get(0).getReview() == null) {
				btnUploadReview.setDisable(false); 
			 } else {
				btnUploadReview.setDisable(true);
			 }
			 
			
		}
	}

	// Logout
	@FXML
	public void logout(ActionEvent event) throws IOException {
		openNewBorderPaneWindow(event, "/application/Login.fxml");
	}


	
	public void downloadSubmission(ActionEvent event) throws IOException {
		if(active != null) {
			util.download(active);
		}
	}

	public void uploadReview(ActionEvent event) throws IOException {
		String filename;
		String suffix = "Submission.pdf";
		String num = active.getName().substring(0, active.getName().length() - suffix.length()); //removes "Submission.pdf"
		filename = "Rev" + num; //Adds "Rev"
		util.upload(new File(active.getParent()), filename);
		
		switch(num) {
		case FIRST:
			util.writeRevReviews(active.getParentFile().getName(), cbJournals.getValue(), LocalDate.now().toString(), 0);
			break;
		case SECOND:
			util.writeRevReviews(active.getParentFile().getName(), cbJournals.getValue(), LocalDate.now().toString(), 1);
			break;
		case THIRD:
			util.writeRevReviews(active.getParentFile().getName(), cbJournals.getValue(), LocalDate.now().toString(), 2);
			break;
		default:
			System.out.println("Error uploading " + filename);
			break;
		}
		btnUploadReview.setDisable(true);
		tableView.setItems(getRecords(cbJournals.getValue()));
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
