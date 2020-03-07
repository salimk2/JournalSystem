package application;




import java.io.IOException;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	public TextField Username;
	@FXML
	public TextField Password;
	@FXML
	public ComboBox<String> users;
	ObservableList<String> list = FXCollections.observableArrayList("Admin","Editor","Researcher","Reviewer");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		users.setItems(list);
		
	}
	
	public void userChanged (ActionEvent event) {
		users.getItems().addAll("1","2","3");
		//myLabel.setText(users.getValue());
	}

	public void buttonAction (ActionEvent event) throws IOException  {
		
		String user = Username.getText();
		String pass = Password.getText();
		Account acc = Main.AuthSys.login(user, pass);

		
		if (acc != null) {
			String fxmlFileName="";
			String usertype = acc.accountTypetoString();
			
			switch (usertype) {
			case ADMIN: 
				fxmlFileName = usertype + ".fxml";
				break;
			case RESEARCHER: 
				fxmlFileName = usertype + ".fxml";
				break;
			case REVIEWER: 
				fxmlFileName = usertype + ".fxml";
				break;
			case EDITOR: 
				fxmlFileName = usertype + ".fxml";
				break;
			default:
				fxmlFileName = "Login.fxml";
				break;
			}
			
			
			openNewWindow(event, fxmlFileName);
			
		}
		
		
		
		
	
		
		
	}
	
	public void openNewWindow(ActionEvent event, String pageName) throws IOException  {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource(pageName));
		Scene scene = new Scene(root);
		
		//This line gets the stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	
		
		window.setScene(scene);
		window.show();
	
	}
	
}
