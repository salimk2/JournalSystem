package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Account;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
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
	  private void hideErrorMsg(){
	    infoLabel.setVisible(false);
	  }

	
	
	
	public void togglevisiblePassword(ActionEvent event) {
		if (showPassword.isSelected()) {
			passwordText.setText(password.getText());
			passwordText.setVisible(true);
			password.setVisible(false);
			return;
		} else {
			password.setText(passwordText.getText());
			password.setVisible(true);
			passwordText.setVisible(false);
		}
	}

	public void buttonAction(ActionEvent event) throws IOException {

		String user = username.getText();
		String pass = password.getText();
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
				fxmlFileName = applicationAddr + usertype + ".fxml";
				break;
			default:
				fxmlFileName = "/application/controllers/Login.fxml";
				break;
			}

			openNewWindow(event, fxmlFileName);

		} else {
			infoLabel.setVisible(true);
			infoLabel.setText("Username/Password not found!" + System.getProperty("line.separator")
					+ "Please try again or create an account. ");
//			username.clear();
//			password.clear();
		}

	}

	public void openNewWindow(ActionEvent event, String pageName) throws IOException {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource(pageName));
		Scene scene = new Scene(root);

		// This line gets the stage info
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(scene);
		window.show();

	}

}
