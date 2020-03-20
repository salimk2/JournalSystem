package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Account;
import application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	public static final String ADMIN = "Admin";
	public static final String EDITOR = "Editor";
	public static final String RESEARCHER = "Researcher";
	public static final String REVIEWER = "Reviewer";

	@FXML
	public Label myLabel;
	@FXML
	public TextField username;
	public TextField passwordText;
	public JFXTextField username2 = new JFXTextField();
	public JFXPasswordField password2 = new JFXPasswordField();
	public JFXTextField passwordText2 = new JFXTextField();
	public Pane content = new Pane();
	@FXML
	public PasswordField password;
	@FXML
	public CheckBox showPassword;
	@FXML
	public Label infoLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		infoLabel.setVisible(false);
		this.togglevisiblePassword(null);
	}
	
	@FXML
	public void onEnter(ActionEvent pressed) throws IOException{
	   buttonAction(pressed);
	}
	
	@FXML
	private void hideErrorMsg(){
		infoLabel.setVisible(false);
	}

	public void togglevisiblePassword(ActionEvent event) {
		if (showPassword.isSelected()) {
			passwordText2.setText(password2.getText());
			passwordText2.setVisible(true);
			password2.setVisible(false);
			return;
		} else {
			password2.setText(passwordText2.getText());
			password2.setVisible(true);
			passwordText2.setVisible(false);
		}
	}
	
	public void openRegistration(MouseEvent event) throws IOException {
		Parent fxmlRegParentParent = FXMLLoader.load(getClass().getResource("/application/Registration.fxml"));
		content.getChildren().removeAll();
		content.getChildren().setAll(fxmlRegParentParent);
		
	}
	
	public void buttonAction(ActionEvent event) throws IOException {
		
		String user = username2.getText();
		String pass;
		if(showPassword.isSelected()) {
			pass = passwordText2.getText();
		}
		else {
			pass = password2.getText();
		}
		Account acc = Main.AuthSys.login(user, pass);

		if (acc != null) {
			String fxmlFileName = "";
			String usertype = acc.accountTypetoString();
			String applicationAddr = "/application/";

			switch (usertype) {
			case ADMIN:
				fxmlFileName = applicationAddr + usertype + ".fxml";
				break;
			case RESEARCHER:
				fxmlFileName = applicationAddr + usertype + ".fxml";
				break;
			case REVIEWER:
				fxmlFileName = applicationAddr + usertype + ".fxml";
				break;
			case EDITOR:
				fxmlFileName = applicationAddr + usertype + ".fxml";  //usertype + ".fxml";
				break;
			default:
				fxmlFileName = "/application/controllers/Login.fxml";
				break;
			}

			//openNewWindow(event, fxmlFileName);
			openNewAnchorPaneWindow(event, fxmlFileName);

		} else {
			infoLabel.setVisible(true);
			infoLabel.setText("Username/Password not found!" + System.getProperty("line.separator")
					+ "Please try again or create an account. ");
//			username.clear();
//			password.clear();
		}

	}

	// Switch Window (BorderPane)
	public void openNewWindow(ActionEvent event, String pageName) throws IOException {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource(pageName));
		Scene scene = new Scene(root);

		// This line gets the stage info
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(scene);
		window.show();

	}
	
	// Switch Window (AnchorPane)
	public void openNewAnchorPaneWindow(ActionEvent event, String newWindow) throws IOException {
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(newWindow));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
