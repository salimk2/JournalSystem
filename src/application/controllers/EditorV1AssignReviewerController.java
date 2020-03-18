package application.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;


/**
 * Controller class for EditorV1AssignReviewer
 * 
 * implements and interface called Initializable 
 * when code is run, the class runs the initialize() method
 */
public class EditorV1AssignReviewerController implements Initializable {
	
	// define FXML components
	@FXML public ComboBox<String> cbSubmissions, cbReviewers;  // ComboBox<DataType> behaves like a list
	@FXML private DatePicker dpMinorRev, dpRev1, dpRev2;	
	@FXML private Button btnGoBack, btnAddRev;	
	
	// !!! Testing
	@FXML public Label lblTest1, lblTest2, lblTest3, lblTest4, lblTest5;
	
	// define variables
	ObservableList<String> submissionsList = FXCollections.observableArrayList("sub1.pdf", "sub2.pdf");
	ObservableList<String> reviewersList = FXCollections.observableArrayList("Reviewer1", "Reviewer2");
	LocalDate ldMinorRev, ldRev1, ldRev2;
	
	// Initializes components 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// set ChoiceBox items
		cbSubmissions.setItems(submissionsList);
		cbReviewers.setItems(reviewersList);
		
	}
	
	/** // cbSubmissions, get selection
	public void cbSubmissionsChanged(ActionEvent event) {
		//lblTest1.setText(cbSubmissions.getValue());
		
		// dynamically change the values in the combo box
		cbSubmissions.getItems().addAll("sub3.pdf", "sub4.pdf");
		
	} */
	
	/**
	 * btnAddReviewer, get values
	 * @param event
	 */
	public void btnAddRevAction(ActionEvent event) {
		
		// get text
		lblTest1.setText(cbSubmissions.getValue());
		lblTest2.setText(cbReviewers.getValue());
		
		// get date
		ldMinorRev = dpMinorRev.getValue();
		ldRev1 = dpRev1.getValue();
		ldRev2 = dpRev2.getValue();
		
		// set labels
		lblTest1.setText(cbSubmissions.getValue());
		lblTest2.setText(cbReviewers.getValue());
		lblTest3.setText(ldMinorRev.toString());
		lblTest4.setText(ldRev1.toString());
		lblTest5.setText(ldRev2.toString());
		
	}
	
	

}
