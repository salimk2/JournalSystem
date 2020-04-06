package application.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXProgressBar;

import application.Main;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
		progressBar.setProgress(0.0);
		doSomething();
		
	}
	
	class bg_Thread implements Runnable{
		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				progressBar.setProgress(1/100.00);
			}
		}
	}
	
	private void doSomething () {
		Thread thread = new Thread(new bg_Thread());
		thread.start();
	}
	
	

	
}
