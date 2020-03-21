package application.controllers;

import application.Authenticator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ResearcherController implements Initializable {
	
	// Declare Components
	@FXML private Label lblSub1, lblSub2, lblSub3, lblSubFinal;			 // submissions
	@FXML private Button btnSub1, btnSub2, btnSub3, btnSubFinal;	
	@FXML private Label lblRev1, lblRev2, lblRevMinor;					 // revisions
	@FXML private Button btnRev1, btnRev2, btnRevMinor;
	@FXML private Label lblComment1, lblComment2, lblCommentMinor; 	 	// comments
	@FXML private Button btnUpload, btnDownload;						// upload/download
	@FXML private Label lblNextSub, lblWithdrawPending;
	
	/**
	 * Initialize the controller class
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// create new authenticator
		Authenticator myAuth = new Authenticator();
		
		// get submissions
		ArrayList<String> submissions = myAuth.getSubmissions("research_1");
		
		// disable view
		if (!submissions.contains("subFinal")) {
			lblSubFinal.setVisible(false);
			btnSubFinal.setVisible(false);
			lblNextSub.setText("Next Submission: Final Submission");
			if (!submissions.contains("sub3")) {
				lblSub3.setVisible(false);
				btnSub3.setVisible(false);
				lblNextSub.setText("Next Submission: Post Revision 1");
				if (!submissions.contains("sub2")) {
					lblSub2.setVisible(false);
					btnSub2.setVisible(false);
					lblNextSub.setText("Next Submission: Post Revision 2");
					if (!submissions.contains("sub1")) {
						lblSub1.setVisible(false);
						btnSub1.setVisible(false);
						lblNextSub.setText("Next Submission: Post Minor Revision (Final Submission)");
					}
				}
			}
		} 
		
		// get reviews
		// disable view
		
		// get comments
		// disable view	
		
		// get withdraw status
		lblWithdrawPending.setVisible(false);
		
	}
	
	/**
	 * Logout
	 * @param event
	 * @throws IOException
	 */
	public void logout (ActionEvent event) throws IOException{
		openNewBorderPaneWindow(event, "/application/Login.fxml");
	}	
	
	/**
	 * Switch Windows (BorderPane)
	 * @param event
	 * @param newWindow
	 * @throws IOException
	 */
	public void openNewBorderPaneWindow(ActionEvent event, String newWindow) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource(newWindow));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	
		window.setScene(scene);
		window.show();
	}	

}
