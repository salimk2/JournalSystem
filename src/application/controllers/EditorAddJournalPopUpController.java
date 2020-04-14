package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * EditorAddJournalPopUpController
 * 
 * Controller class for EditorAddJournalPopUp.FXML
 */
public class EditorAddJournalPopUpController implements Initializable {

	@FXML
	private JFXTextField addJournal;
	@FXML
	private JFXButton createJournal;
	@FXML
	private JFXButton cancel;
	@FXML
	private JFXButton goBack;
	@FXML
	private Label confirmation;

	@Override
	/**
	 * Initializes needed components.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		goBack.setVisible(false);
		confirmation.setVisible(false);

	}

	/**
	 * Hides a particular label from being seen. It may be hidden until an action is
	 * taken to render it visible.
	 */
	@FXML
	public void hideLable() {
		confirmation.setVisible(false);
	}

	/**
	 * Goes back to the editor window.
	 * 
	 * @throws IOException
	 */
	@FXML
	public void backToEditor() throws IOException {
		Stage stage = (Stage) goBack.getScene().getWindow();
		stage.close();
	}

	/**
	 * When a button is pressed, it will generate a Journal entry.
	 * 
	 * @param pressed Invoke an action, using space bar or a key.
	 * @throws IOException
	 */
	@FXML
	public void onEnter(ActionEvent pressed) throws IOException {

		createJournal(pressed);
	}

	/**
	 * Creates the Journal page, setting all the necessary labels, style, and texts.
	 * 
	 * @param pressed: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	@FXML
	public void createJournal(ActionEvent pressed) throws IOException {
		boolean error;
		String message;
		Utilities createJournalDir = new Utilities();
		String journalName = addJournal.getText();
		error = createJournalDir.createJournalDir(journalName);
		message = createJournalDir.getMessage();

		goBack.setVisible(true);
		cancel.setVisible(false);

		if (error) {
			confirmation.setVisible(true);
			confirmation.setStyle("-fx-text-fill:#d90024;");
			confirmation.setText(message);
		} else {
			confirmation.setVisible(true);
			confirmation.setStyle("-fx-text-fill:#027d00;");
			confirmation.setText(message);
		}

	}

}
