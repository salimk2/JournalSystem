package application.controllers;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class ResearcherNominateReviewerController implements Initializable {

	@FXML
	JFXListView<String> reviewerList;
	@FXML
	JFXButton btnNominate, btnGoBack;
	@FXML
	Label message;
	

	private ArrayList<String> reviewersRead = new ArrayList<>();
	private ObservableList<String> list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		message.setVisible(false);
		try {
			reviewersReviewers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < reviewersRead.size(); i++) {
			String j = reviewersRead.get(i);
			list.add(i, j);
		}
		
		reviewerList.setItems(list);
		reviewerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		

	}
	
	
	@FXML
	public void hideLabel() {
		
		message.setVisible(false);

	}
	
	@FXML
	public void goBack(ActionEvent event) {
		Stage stage = (Stage) btnGoBack.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void nominateReviewer() {
		boolean error = displayNominateConfirmationMsg();
		
		if(!error) {
			reviewerList.getSelectionModel().clearSelection();		
			}
		else {
			System.out.println("Success to nominate");
		}
	}
	
	private boolean displayNominateConfirmationMsg() {
		boolean success ;
		ObservableList<String> chosenRevs = reviewerList.getSelectionModel().getSelectedItems();
		
		if(chosenRevs.size() >=5) {
			success = false;
			message.setStyle("-fx-text-fill:#d90024");
			message.setText("Please select at most 4 reviewers");
			message.setVisible(true);
			
		}else if (chosenRevs.size() == 0){
			success = false;
			message.setStyle("-fx-text-fill:#d90024");
			message.setText("Please select at least 1 reviewer");
			message.setVisible(true);

		}
		else {
			success = true;
			message.setStyle("-fx-text-fill:#027d00");
			message.setText("Reviewers were nominated succesfully");
			message.setVisible(true);
			
		}
		return success;
		

	}

	private void reviewersReviewers() throws IOException {

		File readFromLoginFile = new File(System.getProperty("user.dir") + File.separator + "Login.txt");
		try {

			BufferedReader read = new BufferedReader(new FileReader(readFromLoginFile));

			String line = "";
			String[] loginInfo = new String[10000];
			System.out.println("These are the Researcher accounts:");
			System.out.println();
			while ((line = read.readLine()) != null) {
				loginInfo = line.split(" ");

				if (Integer.parseInt(loginInfo[2]) == 3) {
					reviewersRead.add(loginInfo[0]);
					System.out.println(loginInfo[0]);
				}

			}
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error reading login info.");
			e.printStackTrace();
		}
	}

}
