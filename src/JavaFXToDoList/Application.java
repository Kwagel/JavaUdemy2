package JavaFXToDoList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("demo.fxml"));
		primaryStage.setScene(new Scene(root,900,500));
		primaryStage.setTitle("To Do List");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	 @Override
	 public void stop() throws Exception {
		try{
			TodoData.getInstance().storeTodoItems();
		}catch (IOException e){
			System.out.println(e.getMessage());
		}
	 }

	@Override
	public void init() throws Exception {
		try{
			TodoData.getInstance().loadTodoItems();
		}catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
}
