package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static Stage stage = null;
	public static Authenticator AuthSys = new Authenticator();
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));//
			//AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Researcher.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());//
			primaryStage.setScene(scene);
			Main.stage = primaryStage;
			
			// Add icon to Main Page
			Image icon =new Image("/images/Icons/icon1.png");
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("Journal Submission System");
			
			AuthSys.ReadData();
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launches the application
	 * 
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
