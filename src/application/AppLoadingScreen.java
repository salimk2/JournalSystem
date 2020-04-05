package application;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppLoadingScreen extends Preloader{
	
	private Stage preloaderStage;
    private Scene scene;
    
    public AppLoadingScreen() {
        
    }
    
    @Override
    public void init() throws Exception {               
                                         
    Parent root1 = FXMLLoader.load(getClass().getResource("AppLoadingScreen.fxml"));               
    scene = new Scene(root1);                       
                
    }
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.preloaderStage = primaryStage;
		
		//Set the Application loading window and display it
		preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
	}

}
