package application.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReviewerController {

	private String username;
	private int type;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @param username
	 * @param type
	 */
	public void initUser(String username, int type) {
		setUsername(username);
		setType(type);
	}

	/**
	 * @param event
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		openNewWindow(event, "/application/Login.fxml");
		System.out.println("Username is :" + username + "Type is :" + type);
	}

	/**
	 * @param event
	 * @param pageName
	 * @throws IOException
	 */
	public void openNewWindow(ActionEvent event, String pageName) throws IOException {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource(pageName));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
