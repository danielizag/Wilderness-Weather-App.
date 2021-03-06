package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Stage primaryStage;
	private Scene scene;
	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Wilderness Weather Station");
		loadFXMLView();
	}

	private void loadFXMLView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/Weather View.fxml"));
		
		try{
			root = (AnchorPane)loader.load();
			
			scene = new Scene(root);
			
			primaryStage.setScene(scene);
			
			scene.getStylesheets().add("application/application.css");
					
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
