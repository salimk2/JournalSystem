package application.controllers;

import application.EditorRecord;
import application.Utilities;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for EditorV1.fxml
 */
public class EditorController implements Initializable {
	private Pane content = new Pane();
	// configure table
	@FXML
	private TableView<EditorRecord> tableView;
	@FXML
	private TableColumn<EditorRecord, String> submissionColumn;
	@FXML
	private TableColumn<EditorRecord, String> reviewerColumn;
	@FXML
	private TableColumn<EditorRecord, LocalDate> minorRevisionColumn;
	@FXML
	private TableColumn<EditorRecord, LocalDate> revision1Column;
	@FXML
	private TableColumn<EditorRecord, LocalDate> revision2Column;
	@FXML
	private Button btnAddJournal;
	

	/**
	 * Initialize the controller class
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// set columns
		submissionColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("submission")); // instance
																											// variable
																											// to look
																											// for:
																											// submission
		reviewerColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("reviewer"));
		minorRevisionColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, LocalDate>("minorRevision"));
		revision1Column.setCellValueFactory(new PropertyValueFactory<EditorRecord, LocalDate>("revision1"));
		revision2Column.setCellValueFactory(new PropertyValueFactory<EditorRecord, LocalDate>("revision2"));

		// load dummy data
		tableView.setItems(getRecords());
	}

	/**
	 * this method will return an ObersableList of People objects
	 */
	public ObservableList<EditorRecord> getRecords() {

		// ObservaleList behaves like an ArrayList with feature for GUI environment
		ObservableList<EditorRecord> records = FXCollections.observableArrayList();
		records.add(new EditorRecord("Submission1.pdf", "Reviewer 1", LocalDate.of(2020, Month.DECEMBER, 12),
				LocalDate.of(2020, Month.DECEMBER, 20), LocalDate.of(2020, Month.DECEMBER, 28)));
		records.add(new EditorRecord("Submission1.pdf", "Reviewer 1", LocalDate.of(2020, Month.DECEMBER, 12),
				LocalDate.of(2020, Month.DECEMBER, 20), LocalDate.of(2020, Month.DECEMBER, 28)));
		records.add(new EditorRecord("Submission1.pdf", "Reviewer 1", LocalDate.of(2020, Month.DECEMBER, 12),
				LocalDate.of(2020, Month.DECEMBER, 20), LocalDate.of(2020, Month.DECEMBER, 28)));

		return records;
	}

	// Logout
	@FXML
	public void logout(ActionEvent event) throws IOException {
		openNewBorderPaneWindow(event, "/application/Login.fxml");
	}

//	public void openSomething(MouseEvent event) throws IOException {
//		Parent fxmlRegParentParent = FXMLLoader.load(getClass().getResource("/application/Registration.fxml"));
//		content.getChildren().removeAll();
//		content.getChildren().setAll(fxmlRegParentParent);
//		
//	}

	

	// Creating pop up window
	@FXML
	public void addJournal(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;

		if (event.getSource() == btnAddJournal) {
			stage = new Stage();
			// reference editor page
			root = FXMLLoader.load(getClass().getResource("/application/EditorAddJournalPopUp.fxml"));
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(btnAddJournal.getScene().getWindow());
			stage.showAndWait();
		}

	}

	// Switch Windows (BorderPane)
	public void openNewBorderPaneWindow(ActionEvent event, String newWindow) throws IOException {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource(newWindow));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
