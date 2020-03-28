package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;


import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;

import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RegistrationController implements Initializable {

	private static short UsertypeCode = -1;
	private static String UserTypeName = null;

	public static final String ADMIN = "Admin";
	public static final String EDITOR = "Editor";
	public static final String RESEARCHER = "Researcher";
	public static final String REVIEWER = "Reviewer";

	@FXML

	public TextField passwordText;
	public TextField confirmPasswordText;
	public JFXTextField username ;//= new JFXTextField();
	public JFXPasswordField password ;//= new JFXPasswordField();
	public JFXPasswordField confirmPassword;// = new JFXPasswordField();
	public Pane content = new Pane();

	public CheckBox showPassword;

	public Label accountError;
	public Label pwdMatchError;
	public Label userNameError;
	public Label userTypeError;

	public JFXComboBox<String> users;
	ObservableList<String> list = FXCollections.observableArrayList("Admin", "Researcher", "Editor", "Reviewer");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		accountError.setVisible(false);
		pwdMatchError.setVisible(false);
		userNameError.setVisible(false);
		userTypeError.setVisible(false);
		this.togglevisiblePassword(null);
		users.setItems(list);

	}

	@FXML
	public void backToLogin(MouseEvent click) throws IOException {
		Parent fxmlLoginParent = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		Main.stage.getScene().setRoot(fxmlLoginParent);
	}

	public void onEnter(ActionEvent pressed) throws IOException {

		register(pressed);
	}

	@FXML
	public void hideErrorMsg() {
		accountError.setVisible(false);
		pwdMatchError.setVisible(false);
		userNameError.setVisible(false);
		userTypeError.setVisible(false);

	}

	public void getUserType(ActionEvent event) {
		UserTypeName = users.getValue();

		switch (UserTypeName) {
		case ADMIN:
			UsertypeCode = 0;
			break;
		case RESEARCHER:
			UsertypeCode = 1;
			break;
		case EDITOR:
			UsertypeCode = 2;
			break;
		case REVIEWER:
			UsertypeCode = 3;
			break;
		default:
			UsertypeCode = -1;
			break;
		}

	}

	public void togglevisiblePassword(ActionEvent event) {
		if (showPassword.isSelected()) {
			passwordText.setText(password.getText());
			confirmPasswordText.setText(confirmPassword.getText());
			passwordText.setVisible(true);
			confirmPasswordText.setVisible(true);
			password.setVisible(false);
			confirmPassword.setVisible(false);
			return;
		} else {
			password.setText(passwordText.getText());
			confirmPassword.setText(confirmPasswordText.getText());
			password.setVisible(true);
			confirmPassword.setVisible(true);
			passwordText.setVisible(false);
			confirmPasswordText.setVisible(false);
		}
	}

	public void register(ActionEvent event) {
		
			
		String userName = null;
		String pwd = null;
		short numErrors = 0;
		String tempPwdString = password.getText();
		String tempConfPwdString = confirmPassword.getText();
		String tempTextPwdString = passwordText.getText();
		System.out.println(tempTextPwdString);
		String tempTextConfPwdString = confirmPasswordText.getText();
		System.out.println(tempTextConfPwdString);
		
		
		//check for valid userType
		if (UsertypeCode == -1) {
			userTypeError.setVisible(true);
			userTypeError.setText("Please select a valid user");
			numErrors++;
		}

		//check for valid username
		if (username.getText() == null || username.getText().trim().isEmpty()) {
			userNameError.setVisible(true);
			userNameError.setText("Username can't be blank! Please enter a username");
			numErrors++;
		} else {
			userName = username.getText();
		}
		
		//check for matching passwords
		if ((!tempPwdString.equals(tempConfPwdString)) || (!tempTextPwdString.equals(tempTextConfPwdString))) {
			pwdMatchError.setVisible(true);
			pwdMatchError.setText("'Password' and 'Confirm Pasword' dont match!");
			numErrors++;
		} else {
			if(showPassword.isSelected())
				pwd=passwordText.getText();
			else {
				pwd=password.getText();
			}
					
		}
		
		if(numErrors == 0){
			//System.out.println(UserTypeName + " " + UsertypeCode + " "+ pwd +" "+ userName);
			
			short creatAccount = Main.AuthSys.register(userName, pwd, UsertypeCode);
			
			switch (creatAccount) {
			case 0:
				accountError.setVisible(true);
				accountError.setStyle("-fx-text-fill:green;");
				accountError.setText("Account created succesfullt!"+ System.getProperty("line.separator") + "Please use arrow on top left corner "
						+ System.getProperty("line.separator") + "to go back to login screen.");
				break;
				
			case -1:
				accountError.setVisible(true);
				accountError.setText("Invalid Username!"+ System.getProperty("line.separator") + "Please make sure username is at least"
						+ System.getProperty("line.separator") + " 6 characters long and has no spaces.");
				break;
				
			case -2:
				accountError.setVisible(true);
				accountError.setText("Username already exists!" + System.getProperty("line.separator")+ "Please try another username");
				break;
				
			case -3:
				accountError.setVisible(true);
				accountError.setText("Invalid Password!"+ System.getProperty("line.separator") + "Please make sure password follows the following requirements:" + System.getProperty("line.separator")
										+"-Minimum eight characters."+ System.getProperty("line.separator") +
										"-Contain at least one digit . "+ System.getProperty("line.separator") );
				break;
				
			default:
				accountError.setVisible(true);
				accountError.setText("Unknown error occured!" + System.getProperty("line.separator") + "Please try again or contact an administrator.");
				break;
			}
			
			System.out.println(creatAccount);
		}
		else {
			accountError.setText("Please fix the indicated errors and try again");
		}
		
		

	}

}
