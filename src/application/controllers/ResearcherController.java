package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringJoiner;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	/* Declare Components */
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
	private String notificationList;
	private boolean reviewerWasAssigned = false;
	private String reviewerNominateStatus = "";
	private ObservableList<String> list = FXCollections.observableArrayList();
	@FXML
	private FontAwesomeIcon notification, refreshIcon;

	private Utilities util = new Utilities();
	File mainPath = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator + "editor"
			+ File.separator + "journals" + File.separator);

	/**
	 * @param username: Username of the user.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return username
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

	/**
	 * Initialize user.
	 *
	 * @param username: Username of the user.
	 * @param type:     Type of user.
	 */
	public void initUser(String username, int type) {
		setUsername(username);
		setType(type);
	}

	/**
	 * Initialize the 'Controller' class.
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
		lblWithdrawPending.setVisible(false);
		btnWithdraw.setDisable(true);

	}

	/**
	 * Opens the Nomination window.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
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
	/**
	 * Added the refresh option to clear any error label and enable any available
	 * button
	 *
	 * @param click: Actin taken by user.
	 */

	public void refreshPage(MouseEvent click) {
		String journalNameRefresh = selectJournal.getValue();
		journalSelected(journalNameRefresh);
		refreshIcon.setVisible(false);
	}

	/**
	 * Read file.
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
	 * Processes the selected Journal.
	 * 
	 * @param event: Event component that does a desired action when pressed.
	 */
	@FXML
	public void journalSelected(ActionEvent event) {

		String journalName = selectJournal.getValue();
		reviewerWasAssigned = false;
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
			btnWithdraw.setDisable(true);
			alert.setText("Journal is empty. Please submbit files to " + journalName);
			alert.setStyle("-fx-text-fill:#d90024;");
			alert.setVisible(true);
		} else {
			checkJournalUserSubmissionFile(journalName, username);
			alert.setVisible(false);
			btnNominate.setDisable(false);
			File withdrawFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "WithdrawSubmitted.txt");
			if (!withdrawFile.exists()) {
				btnWithdraw.setDisable(false);
				lblWithdrawPending.setVisible(false);

			} else {
				btnWithdraw.setDisable(true);
			}

		}

		if (showNotifications(journalName)) {

			notification.setVisible(true);
		} else
			notification.setVisible(false);

	}

	/**
	 * Overloaded function: Used to refresh page.
	 * 
	 * @param journalName: Name of the journal.
	 */
	public void journalSelected(String journalName) {
		reviewerWasAssigned = false;
		notification.setVisible(false);
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
			btnWithdraw.setDisable(true);
			alert.setText("Journal is empty. Please submbit files to " + journalName);
			alert.setStyle("-fx-text-fill:#d90024;");
			alert.setVisible(true);
		} else {
			checkJournalUserSubmissionFile(journalName, username);
			alert.setVisible(false);
			btnNominate.setDisable(false);
			File withdrawFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "WithdrawSubmitted.txt");
			if (!withdrawFile.exists()) {
				btnWithdraw.setDisable(false);
				lblWithdrawPending.setVisible(false);
			} else {
				btnWithdraw.setDisable(true);
			}

		}
		if (showNotifications(journalName))
			notification.setVisible(true);
		else
			notification.setVisible(false);

	}

	/**
	 * Checks if the submitted files by the 'Researcher' exist.
	 *
	 * @param journalName: Name of the journal submitted.
	 * @param username:    Username of the 'Researcher'.
	 */
	public void checkJournalUserSubmissionFile(String journalName, String username) {

		File baseFilePath = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator);

		File firstFile = new File(baseFilePath + File.separator + "FirstSubmission.pdf");
		File secondFile = new File(baseFilePath + File.separator + "SecondSubmission.pdf");
		File thirdFile = new File(baseFilePath + File.separator + "ThirdSubmission.pdf");
		File finalFile = new File(baseFilePath + File.separator + "FinalSubmission.pdf");

		File firstRev = new File(baseFilePath + File.separator + "RevFirstSubmission.pdf");
		File secondRev = new File(baseFilePath + File.separator + "RevSecondSubmission.pdf");
		File thirdRev = new File(baseFilePath + File.separator + "RevThirdSubmission.pdf");

		/* *** Check First Submission *** */
		if (firstFile.exists()) {
			// System.out.println(journalName + " has a directory called " + username + "
			// and a file called "
			// + "FirstSubmission.pdf");

			btnSub1.setDisable(false);
		} else {

			// System.out.println(journalName + " does not have a directory called " +
			// username
			// + " and a file called FirstSubmission.pdf");

			btnSub1.setDisable(true);
		}

		/* *** Check Second Submission *** */
		if (secondFile.exists()) {
			// System.out.println(journalName + " has a directory called " + username + "
			// and a file called "
			// + "SecondSubmission.pdf");

			btnSub2.setDisable(false);
		} else {

			// System.out.println(journalName + " does not have a directory called " +
			// username
			// + " and a file called SecondSubmission.pdf");

			btnSub2.setDisable(true);
		}

		/* *** Check Third Submisiion *** */
		if (thirdFile.exists()) {
			// System.out.println(journalName + " has a directory called " + username + "
			// and a file called "
			// + "ThirdSubmission.pdf");

			btnSub3.setDisable(false);
		} else {

			// System.out.println(journalName + " does not have a directory called " +
			// username
			// + " and a file called ThirdSubmission.pdf");

			btnSub3.setDisable(true);
		}

		/* *** Check Final Submission *** */
		if (finalFile.exists()) {
			// System.out.println(journalName + " has a directory called " + username + "
			// and a file called "
			// + "FinalSubmission.pdf");

			btnSubFinal.setDisable(false);
		} else {

			// System.out.println(journalName + " does not have a directory called " +
			// username
			// + " and a file called FinalSubmission.pdf");

			btnSubFinal.setDisable(true);
		}

		File reviewFile = new File(baseFilePath + File.separator + "reviewerReviews.txt");
		if (reviewFile.exists()) {
			try {
				String[] review = util.readRevReviews(username, journalName).split(" ");
				if (!review[0].equals("null")) {
					lblComment1.setText(review[0]);
				}

				if (!review[1].equals("null")) {
					lblComment2.setText(review[1]);
				}

				if (!review[2].equals("null")) {
					lblCommentMinor.setText(review[2]);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			lblComment1.setText("");
			lblComment2.setText("");
			lblCommentMinor.setText("");
		}

		// ======================Check First Rev
		// submission====================================================
		if (firstRev.exists()) {
			// System.out.println(journalName + " has a directory called " + username + "
			// and a file called "
			// + "FirstSubmission.pdf");
			btnRev1.setDisable(false);
		} else {

			// System.out.println(journalName + " does not have a directory called " +
			// username
			// + " and a file called FirstSubmission.pdf");

			btnRev1.setDisable(true);
		}

		// ======================Check Second Rev
		// submission====================================================
		if (secondRev.exists()) {
			// System.out.println(journalName + " has a directory called " + username + "
			// and a file called "
			// + "SecondSubmission.pdf");
			btnRev2.setDisable(false);
		} else {

			// System.out.println(journalName + " does not have a directory called " +
			// username
			// + " and a file called SecondSubmission.pdf");

			btnRev2.setDisable(true);
		}

		// ======================Check Third Rev
		// submission====================================================
		if (thirdRev.exists()) {
			// System.out.println(journalName + " has a directory called " + username + "
			// and a file called "
			// + "ThirdSubmission.pdf");
			btnRevMinor.setDisable(false);
		} else {

			// System.out.println(journalName + " does not have a directory called " +
			// username
			// + " and a file called ThirdSubmission.pdf");

			btnRevMinor.setDisable(true);
		}

	}

	/**
	 * Users logouts.
	 * 
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		openNewBorderPaneWindow(event, "/application/Login.fxml");

	}

	/**
	 * Button to view the first submitted file.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnSub1Action(ActionEvent event) throws IOException {
		String JournalName = selectJournal.getValue();
		if (event.getSource() == btnSub1) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + JournalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "FirstSubmission.pdf");
			util.download(PathFile);
		}

	}

	/**
	 * Button to view the second submitted file.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnSub2Action(ActionEvent event) throws IOException {
		String JournalName = selectJournal.getValue();
		if (event.getSource() == btnSub2) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + JournalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "SecondSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * Button to view the third submitted file.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnSub3Action(ActionEvent event) throws IOException {
		String JournalName = selectJournal.getValue();
		if (event.getSource() == btnSub3) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + JournalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "ThirdSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * Button to view the final submission file.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnSubFinalAction(ActionEvent event) throws IOException {
		String JournalName = selectJournal.getValue();
		if (event.getSource() == btnSubFinal) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + JournalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "FinalSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * Button to download the first submission review.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnRev1Action(ActionEvent event) throws IOException {
		String JournalName = selectJournal.getValue();
		if (event.getSource() == btnRev1) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + JournalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "RevFirstSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * Button to download the second submission review.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnRev2Action(ActionEvent event) throws IOException {
		String JournalName = selectJournal.getValue();
		if (event.getSource() == btnRev2) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + JournalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "RevSecondSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * Button to download the minor revision.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnRevMinorAction(ActionEvent event) throws IOException {
		String JournalName = selectJournal.getValue();
		if (event.getSource() == btnRevMinor) {
			File PathFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
					+ "editor" + File.separator + "journals" + File.separator + JournalName + File.separator
					+ "researchers" + File.separator + username + File.separator + "RevThirdSubmission.pdf");
			util.download(PathFile);
		}
	}

	/**
	 * Button to perform the Upload action.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnUploadAction(ActionEvent event) throws IOException {

		/* Creating pop up window */
		refreshIcon.setVisible(true);

		Stage stage;
		Parent root;

		if (event.getSource() == btnUpload) {
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/ResearcherUploadDocument.fxml"));
			/* Reference Editor page */
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
	 * Checks for certain files and words inside files to determined if the
	 * notification should be shown.
	 * 
	 * @param journalName: Name of the journal.
	 * @return notifications
	 */
	private boolean showNotifications(String journalName) {
		reviewerNominateStatus = "";
		boolean notifications;
		File path = new File(mainPath + File.separator + journalName + File.separator + "researchers" + File.separator
				+ username + File.separator);
		File pathToRevNomFile = new File(mainPath + File.separator + journalName + File.separator + "researchers"
				+ File.separator + username + File.separator + File.separator + "nominatedReviewers.txt");
//		if (path.exists())
//			System.out.println("Path Exists " + path);
//		else {
//			System.out.println("Path doesnt exist " + path);
//		}
		StringJoiner joiner = new StringJoiner(" ");
		String[] temp = util.listFilesInDir(path);
		for (String string : temp) {
			joiner.add(string);
		}
		notificationList = joiner.toString();

		try {
			if (pathToRevNomFile.exists())
				reviewerNominateStatus = util.readNomRevFile(username, journalName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (reviewerNominateStatus.contains("ASSIGNED"))
			reviewerWasAssigned = true;
		System.out.println("was rev assigned = " + reviewerWasAssigned);

		// System.out.println(notificationList);
		if (notificationList.contains("FinalSubmissionRejected.txt")
				|| notificationList.contains("FinalSubmissionAccepted.txt") || reviewerWasAssigned)
			notifications = true;
		else
			notifications = false;

		return notifications;
	}

	/**
	 * When clicked, it displays an informative alert.
	 * 
	 * @param click: Action taken by user.
	 */
	public void desplayNotificationAlert(MouseEvent click) {

		if (notificationList.contains("FinalSubmissionRejected.txt")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Final Submission Update");
			alert.setContentText(
					"The editor has rejected your final submission. For more information please contact the Editor");
			alert.showAndWait();

		} else if (notificationList.contains("FinalSubmissionAccepted.txt")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Final Submission Update");
			alert.setContentText(
					"Congratulations! The editor has Accepted your final submission. For more information please contact the Editor");
			alert.showAndWait();
		} else if (reviewerNominateStatus.contains("ASSIGNED")) {
			String asgRevName = reviewerNominateStatus.substring(8);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Assigned Reviewer Update");
			alert.setContentText(
					"Congratulations! The editor has assigned you a reviewer. Your reviewer name is: " + asgRevName);
			alert.showAndWait();

		}
		reviewerWasAssigned = false;
		notification.setVisible(false);
	}

	/**
	 * Button to perform the Withdraw action.
	 *
	 * @param event: Event component that does a desired action when pressed.
	 * @throws IOException
	 */
	public void btnWithdrawAction(ActionEvent event) throws IOException {
		String Journal = selectJournal.getValue();
		File withdrawFile = new File(System.getProperty("user.dir") + File.separator + "projectDB" + File.separator
				+ "editor" + File.separator + "journals" + File.separator + Journal + File.separator + "researchers"
				+ File.separator + username + File.separator + "WithdrawSubmitted.txt");
		if (!withdrawFile.exists()) {
			if (withdrawFile.createNewFile()) {
				lblWithdrawPending.setStyle("-fx-text-fill:#027d00");
				lblWithdrawPending.setText("Withdrawal has been submitted");
				lblWithdrawPending.setVisible(true);
				btnWithdraw.setDisable(true);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error submitting withdrawal");
				alert.setContentText(
						"Unknown Error,please close window and try again. If issue persists please contact IT.");
				alert.showAndWait();
				btnWithdraw.setDisable(true);
				lblWithdrawPending.setVisible(false);
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Withdrawal has already been submitted");

			alert.setContentText("Editor is reviewing your withdrawal submission");

			alert.showAndWait();
			btnWithdraw.setDisable(true);
			lblWithdrawPending.setVisible(false);
		}

	}

	/**
	 * Switch Windows (BorderPane)
	 * 
	 * @param event:     Event component that does a desired action when pressed.
	 * @param newWindow: New window to be created.
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
