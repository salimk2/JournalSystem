package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import application.Utilities;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author jdev
 *
 */
public class ResearcherController implements Initializable {

	private String username;
	private int type;

	// Declare Components
	@FXML
	private Label lblSub1, lblSub2, lblSub3, lblSubFinal; // submissions
	@FXML
	private Button btnSub1, btnSub2, btnSub3, btnSubFinal;
	@FXML
	private Label lblRev1, lblRev2, lblRevMinor; // revisions
	@FXML
	private Button btnRev1, btnRev2, btnRevMinor;
	@FXML
	private Label lblComment1, lblComment2, lblCommentMinor; // comments
	@FXML
	private JFXButton btnUpload, btnWithdraw; // upload/download
	@FXML
	private JFXButton btnNominate;
	@FXML
	private Label lblNextSub, lblWithdrawPending;
	@FXML
	private JFXComboBox<String> selectJournal;
	@FXML
	private Label alert, nominateRevLabel;

	private List<String> journals = new ArrayList<>();
	private ObservableList<String> list = FXCollections.observableArrayList();
	@FXML
	private FontAwesomeIcon notification, refreshIcon;

	private Utilities util = new Utilities();

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	public void initUser(String username, int type) {
		setUsername(username);
		setType(type);
	}

	/**
	 * Initialize the controller class
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		readFile();
		for (int i = 0; i < journals.size(); i++) {
			String j = journals.get(i);

			list.add(i, j);
		}
		selectJournal.setItems(list);
		btnRev1.setDisable(true);
		btnRev2.setDisable(true);
		btnRevMinor.setDisable(true);
		btnSub1.setDisable(true);
		btnSub2.setDisable(true);
		btnSub3.setDisable(true);
		btnSubFinal.setDisable(true);
		alert.setVisible(false);
		notification.setVisible(false);
		btnNominate.setDisable(true);
		refreshIcon.setVisible(false);

	}

	@FXML
	public void openNominateWin(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		String journalName = selectJournal.getValue();

		if (event.getSource() == btnNominate) {
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/ResearcherNominateReviewer.fxml"));
			// reference editor page
			root = loader.load();
			ResearcherNominateReviewerController controller = loader.getController();
			controller.setUserInfo(username, journalName);

			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(btnUpload.getScene().getWindow());
			stage.showAndWait();
		}

	}

	@FXML
	// Added the refresh option to clear any error label and enable any available
	// button
	public void refreshPage(MouseEvent click) {
		String journalNameRefresh = selectJournal.getValue();
		journalSelected(journalNameRefresh);
		refreshIcon.setVisible(false);
	}

	/**
	 * 
	 */
	private void readFile() {

		String journalList;
		try {
			journalList = util.readJournalList();
			journals = Arrays.asList(journalList.split(" "));
			for (int i = 0; i < journals.size(); i++) {

				String j = journals.get(i);

				// System.out.println(j + " " + i);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File was not found, Can't read it");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param event
	 */
	@FXML
	public void journalSelected(ActionEvent event) {
		String journalName = selectJournal.getValue();
		boolean userFound = util.checkResearcherFileExists(journalName, username);
		if (!userFound) {
			btnRev1.setDisable(true);
			btnRev2.setDisable(true);
			btnRevMinor.setDisable(true);
			btnSub1.setDisable(true);
			btnSub2.setDisable(true);
			btnSub3.setDisable(true);
			btnSubFinal.setDisable(true);
			btnNominate.setDisable(true);
			alert.setText("Journal is empty. Please submbit files to " + journalName);
			alert.setStyle("-fx-text-fill:#d90024;");
			alert.setVisible(true);
		} else {
			checkJournalUserSubmissionFile(journalName, username);
			alert.setVisible(false);
			btnNominate.setDisable(false);

		}
	}

	/**
	 * Overloaded function to used to refresh page
	 * 
	 * @param journalName
	 */
	public void journalSelected(String journalName) {
		boolean userFound = util.checkResearcherFileExists(journalName, username);
		if (!userFound) {
			btnRev1.setDisable(true);
			btnRev2.setDisable(true);
			btnRevMinor.setDisable(true);
			btnSub1.setDisable(true);
			btnSub2.setDisable(true);
			btnSub3.setDisable(true);
			btnSubFinal.setDisable(true);
			btnNominate.setDisable(true);
			alert.setText("Journal is empty. Please submbit files to " + journalName);
			alert.setStyle("-fx-text-fill:#d90024;");
			alert.setVisible(true);
		} else {
			checkJournalUserSubmissionFile(journalName, username);
			alert.setVisible(false);
			btnNominate.setDisable(false);

		}
	}

	public void checkJournalUserSubmissionFile(String journalName, String username) {

		File baseFilePath = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator);
		File firstFile = new File(baseFilePath + File.separator + "FirstSubmission.pdf");
		File secondFile = new File(baseFilePath + File.separator + "SecondSubmission.pdf");
		File thirdFile = new File(baseFilePath + File.separator + "ThirdSubmission.pdf");
		File finalFile = new File(baseFilePath + File.separator + "FinalSubmission.pdf");

		// ======================Check First
		// submission====================================================
		if (firstFile.exists()) {
			System.out.println(firstFile);
			System.out.println(journalName + " has a directory called " + username + " and a file called "
					+ "FirstSubmission.pdf");

			btnSub1.setDisable(false);
		} else {

			System.out.println(journalName + " does not have a directory called " + username
					+ " and a file called FirstSubmission.pdf");

			btnSub1.setDisable(true);
		}

		// ======================Check Second
		// submission====================================================
		if (secondFile.exists()) {
			System.out.println(firstFile);
			System.out.println(journalName + " has a directory called " + username + " and a file called "
					+ "SecondSubmission.pdf");

			btnSub2.setDisable(false);
		} else {

			System.out.println(journalName + " does not have a directory called " + username
					+ " and a file called SecondSubmission.pdf");

			btnSub2.setDisable(true);
		}

		// ======================Check Third
		// submission====================================================
		if (thirdFile.exists()) {
			System.out.println(firstFile);
			System.out.println(journalName + " has a directory called " + username + " and a file called "
					+ "ThirdSubmission.pdf");

			btnSub3.setDisable(false);
		} else {

			System.out.println(journalName + " does not have a directory called " + username
					+ " and a file called ThirdSubmission.pdf");

			btnSub3.setDisable(true);
		}

		// ======================Check Final
		// submission====================================================
		if (finalFile.exists()) {
			System.out.println(firstFile);
			System.out.println(journalName + " has a directory called " + username + " and a file called "
					+ "FinalSubmission.pdf");

			btnSub1.setDisable(false);
		} else {

			System.out.println(journalName + " does not have a directory called " + username
					+ " and a file called FinalSubmission.pdf");

			btnSubFinal.setDisable(true);
		}

	}

	/**
	 * Logout
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		openNewBorderPaneWindow(event, "/application/Login.fxml");

	}

	/**
	 * btnSub1
	 */
	public void btnSub1Action(ActionEvent event) throws IOException {
		if (event.getSource() == btnSub1) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + "journal1" + File.separator
					+ "researchers" + File.separator + username + File.separator + "FirstSubmission.pdf");
			util.download(PathFile);
		}

	}

	/**
	 * btnSub2
	 */
	public void btnSub2Action(ActionEvent event) throws IOException {
		if (event.getSource() == btnSub2) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + "journal1" + File.separator
					+ "researchers" + File.separator + username + File.separator + "SecondSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * btnSUb3
	 */
	public void btnSub3Action(ActionEvent event) throws IOException {
		if (event.getSource() == btnSub3) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + "journal1" + File.separator
					+ "researchers" + File.separator + username + File.separator + "ThirdSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * btnSubFinal
	 */
	public void btnSubFinalAction(ActionEvent event) throws IOException {
		if (event.getSource() == btnSubFinal) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + "journal1" + File.separator
					+ "researchers" + File.separator + username + File.separator + "FinalSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * btnRev1
	 */
	public void btnRev1Action(ActionEvent event) throws IOException {
		if (event.getSource() == btnRev1) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + "journal1" + File.separator
					+ "researchers" + File.separator + username + File.separator + "Review1.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * btnRev2
	 */
	public void btnRev2Action(ActionEvent event) throws IOException {
		if (event.getSource() == btnRev2) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + "journal1" + File.separator
					+ "researchers" + File.separator + username + File.separator + "Review2.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * btnRevMinor
	 */
	public void btnRevMinorAction(ActionEvent event) throws IOException {
		if (event.getSource() == btnRevMinor) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + "journal1" + File.separator
					+ "researchers" + File.separator + username + File.separator + "ReviewMinor.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * btnUpload
	 */
	public void btnUploadAction(ActionEvent event) throws IOException {

		// Creating pop up window
		refreshIcon.setVisible(true);

		Stage stage;
		Parent root;

		if (event.getSource() == btnUpload) {
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/ResearcherUploadDocument.fxml"));
			// reference editor page
			root = loader.load();
			ResearcherUploadDocumentController controller = loader.getController();
			controller.setUser(username, type);

			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(btnUpload.getScene().getWindow());
			stage.showAndWait();
		}

		System.out.println("Upload Clicked");
	}

	/**
	 * btnWithdraw
	 */
	public void btnWithdrawAction(ActionEvent event) throws IOException {
		// TODO implement the method
		System.out.println("Withdraw Clicked");
	}

	/**
	 * Switch Windows (BorderPane)
	 * 
	 * @param event
	 * @param newWindow
	 * @throws IOException
	 */
	public void openNewBorderPaneWindow(ActionEvent event, String newWindow) throws IOException {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource(newWindow));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
