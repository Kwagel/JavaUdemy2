package JavaFXGridPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

 public class Application extends javafx.application.Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("demo.fxml"));
		primaryStage.setScene(new Scene(root,700,275));
		primaryStage.setTitle("Hello Application!");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
