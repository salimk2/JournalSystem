package application.controllers;

import application.EditorRecord;

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
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller class for EditorV1.fxml
 */
public class EditorController implements Initializable {
	
	// configure table
	@FXML private TableView<EditorRecord> tableView;
	@FXML private TableColumn<EditorRecord, String> submissionColumn;
	@FXML private TableColumn<EditorRecord, String> reviewerColumn;
	@FXML private TableColumn<EditorRecord, LocalDate> minorRevisionColumn;
	@FXML private TableColumn<EditorRecord, LocalDate> revision1Column;
	@FXML private TableColumn<EditorRecord, LocalDate> revision2Column;	
	
	/**
	 * Initialize the controller class
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		// set columns
		submissionColumn.setCellValueFactory(new PropertyValueFactory<EditorRecord, String>("submission"));  // instance variable to look for: submission
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
		records.add(new EditorRecord("Submission1.pdf","Reviewer 1",LocalDate.of(2020, Month.DECEMBER, 12), LocalDate.of(2020, Month.DECEMBER, 20), LocalDate.of(2020, Month.DECEMBER, 28)));
		records.add(new EditorRecord("Submission1.pdf","Reviewer 1",LocalDate.of(2020, Month.DECEMBER, 12), LocalDate.of(2020, Month.DECEMBER, 20), LocalDate.of(2020, Month.DECEMBER, 28)));
		records.add(new EditorRecord("Submission1.pdf","Reviewer 1",LocalDate.of(2020, Month.DECEMBER, 12), LocalDate.of(2020, Month.DECEMBER, 20), LocalDate.of(2020, Month.DECEMBER, 28)));
		
		return records;	
	}

	// Logout
	public void logout (ActionEvent event) throws IOException{
		openNewBorderPaneWindow(event, "/application/Login.fxml");
	}	
	
	// Switch Windows (BorderPane)
	public void openNewBorderPaneWindow(ActionEvent event, String newWindow) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource(newWindow));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	
		window.setScene(scene);
		window.show();
	}	

}
