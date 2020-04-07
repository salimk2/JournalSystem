package application.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import application.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EditorReviewFinalSubmissionController implements Initializable {

	private String Journal;

	@FXML
	JFXButton btnDownload, btnAccept, btnReject, btnGoBack, btnGetResearchers;
	@FXML
	JFXComboBox<String> selectResearcher;
	@FXML
	Label alert;

	private List<String> researchersInJournal = new ArrayList<>();
	private ObservableList<String> researcherList = FXCollections.observableArrayList();
	Utilities util = new Utilities();
	File mainPath = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator + "editor"
			+ File.separator + "journals" + File.separator);

	public void setJournal(String journal) {
		Journal = journal;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnDownload.setDisable(true);
		btnAccept.setDisable(true);
		btnReject.setDisable(true);
		btnGoBack.setDisable(true);
		selectResearcher.setDisable(true);
		btnDownload.setVisible(false);
		btnAccept.setVisible(false);
		btnReject.setVisible(false);
		btnGoBack.setVisible(false);
		selectResearcher.setVisible(false);
		alert.setVisible(false);
	}

	public void getResearchers() {
		fillResearcherComboBox();
		btnGoBack.setDisable(false);
		selectResearcher.setDisable(false);
		btnDownload.setVisible(true);
		btnAccept.setVisible(true);
		btnReject.setVisible(true);
		btnGoBack.setVisible(true);
		selectResearcher.setVisible(true);
		btnGetResearchers.setVisible(false);
		btnGetResearchers.setDisable(true);
	}

	private void fillResearcherComboBox() {
		File path = new File(mainPath + File.separator + Journal + File.separator + "researchers" + File.separator);
		researchersInJournal = Arrays.asList(util.listFilesInDir(path));
		researcherList.clear();
		for (int i = 0; i < researchersInJournal.size(); i++) {
			String elem = researchersInJournal.get(i);
			researcherList.add(i, elem);
			System.out.println(elem);
		}

		selectResearcher.setItems(researcherList);
	}

	/**
	 * Go back to main editor page
	 * 
	 * @param event
	 */
	@FXML
	public void goBack(ActionEvent event) {
		Stage stage = (Stage) btnGoBack.getScene().getWindow();

		stage.close();
	}

}
