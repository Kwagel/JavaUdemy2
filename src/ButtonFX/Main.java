package ButtonFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
//		Parent is the root class, so you can access and define resources using scenes
//		first you must actually get the objects using FXMLLoader. load and using the parent class to retrieve the fxml file
		
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 300,275));
		primaryStage.show();

	
	}
}
