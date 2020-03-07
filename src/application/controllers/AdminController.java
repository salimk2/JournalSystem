package application.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminController {
	
	public void goBack (ActionEvent event) throws IOException{
		openNewWindow(event, "/application/Login.fxml");
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
