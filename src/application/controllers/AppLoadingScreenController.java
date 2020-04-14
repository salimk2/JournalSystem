package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXProgressBar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * AppLoadingScreenController
 * 
 * Controller class for AppLoadingScreen.FXML
 */
public class AppLoadingScreenController implements Initializable {
	@FXML
	private StackPane rootPane;
	@FXML
	private JFXProgressBar progressBar;

	@FXML
	private Label loadingLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
