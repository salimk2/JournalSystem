package application.controllers;

import application.Authenticator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResearcherController implements Initializable {
	
	private String username;
	private int type;
	
	// Declare Components
	@FXML private Label lblSub1, lblSub2, lblSub3, lblSubFinal;			 // submissions
	@FXML private Button btnSub1, btnSub2, btnSub3, btnSubFinal;	
	@FXML private Label lblRev1, lblRev2, lblRevMinor;					 // revisions
	@FXML private Button btnRev1, btnRev2, btnRevMinor;
	@FXML private Label lblComment1, lblComment2, lblCommentMinor; 	 	// comments
	@FXML private JFXButton btnUpload, btnWithdraw;						// upload/download
	@FXML private Label lblNextSub, lblWithdrawPending;
	
	
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void initUser(String username, int type) {
		setUsername(username);
		setType(type);
	}
	
	

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
			lblSubFinal.setDisable(true);
			btnSubFinal.setDisable(true);
			lblNextSub.setText("Next Submission: Final Submission");
			if (!submissions.contains("sub3")) {
				lblSub3.setDisable(true);
				btnSub3.setDisable(true);
				lblNextSub.setText("Next Submission: Post Revision 1");
				if (!submissions.contains("sub2")) {
					lblSub2.setDisable(true);
					btnSub2.setDisable(true);
					lblNextSub.setText("Next Submission: Post Revision 2");
					if (!submissions.contains("sub1")) {
						lblSub1.setDisable(true);
						btnSub1.setDisable(true);
						lblNextSub.setText("Next Submission: Post Minor Revision (Final Submission)");
					}
				}
			}
		} 
		
		// get reviews & disable view	
		ArrayList<String> reviews = myAuth.getReviews("research_1", "sub_1");	
		if (!reviews.contains("revMinor")) {			
			lblRevMinor.setDisable(true);
			btnRevMinor.setDisable(true);
			if (!reviews.contains("rev2")) {
				lblRev2.setDisable(true);
				btnRev2.setDisable(true);
				if (!reviews.contains("rev1")) {
					lblRev1.setDisable(true);
					btnRev2.setDisable(true);
				}
			}
		}			
		
		// get comments & disable view
		ArrayList<String> comments = myAuth.getComments("research_1", "rev_1");
		if (!comments.contains("commentMinor")) {			
			lblCommentMinor.setDisable(true);
			if (!comments.contains("comment2")) {
				lblComment2.setDisable(true);
				if (!comments.contains("comment1")) {
					lblComment1.setDisable(true);
				}
			}
		} 	
		
		// get withdraw status
		boolean withdrawStatus = myAuth.getWithdrawStatus("research_1", "sub_1");
		if (!withdrawStatus) {								 
			lblWithdrawPending.setDisable(true);
		}	
		
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
	 * btnSub1
	 */
	public void btnSub1Action(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Downlaod Sub1 Clicked");
		System.out.println("Username is :" + username + " Type is :" + type);
	}	

	/**
	 * btnSub2
	 */
	public void btnSub2Action(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Downlaod Sub2 Clicked");
	}	

	/**
	 * btnSUb3
	 */
	public void btnSub3Action(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Download Sub3 Clicked");
	}	
	
	/**
	 * btnSubFinal
	 */
	public void btnSubFinalAction(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Download SubFinal Clicked");
	}
	
	/**
	 * btnRev1
	 */
	public void btnRev1Action(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Download Rev1 Clicked");
	}	
	
	/**
	 * btnRev2
	 */
	public void btnRev2Action(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Download Rev2 Clicked");
	}	
	
	/**
	 * btnRevMinor
	 */
	public void btnRevMinorAction(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Download RevMinor Clicked");
	}	
	
	/**
	 * btnUpload
	 */
	public void btnUploadAction(ActionEvent event) throws IOException{
		// TODO implement the method
		// Creating pop up window
		
			Stage stage;
			Parent root;
			
			
			
		

			if (event.getSource() == btnUpload) {
				stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/ResearcherUploadDocument.fxml"));
				// reference editor page
				root = loader.load();
				ResearcherUploadDocumentController controller =loader.getController();
				controller.setUser(username,type);
				
				
				stage.setScene(new Scene(root));
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(btnUpload.getScene().getWindow());
				stage.showAndWait();
			}

		
		System.out.println("Upload Clicked");
	}	
	
	/**
	 * btnWithdraw
	 */
	public void btnWithdrawAction(ActionEvent event) throws IOException{
		// TODO implement the method
		System.out.println("Withdraw Clicked");
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
