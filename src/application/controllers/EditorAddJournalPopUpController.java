package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Utilities;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.stage.Stage;

public class EditorAddJournalPopUpController implements Initializable {

	@FXML
	private JFXTextField addJournal;
	@FXML
	private JFXButton createJournal;
	@FXML
	private JFXButton cancel;
	@FXML
	private JFXButton goBack;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		goBack.setVisible(false);

	}

	@FXML
	public void backToEditor() throws IOException {
		Stage stage = (Stage) goBack.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void createJournal() {
		String message;
		Utilities createJournalDir = new Utilities();
		String journalName = addJournal.getText();
		message =createJournalDir.createJournalDir(journalName);
		goBack.setVisible(true);
		cancel.setVisible(false);
		
		
		System.out.println(message);
		

	}

}
