package ButtonFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
	
	@FXML
	private Button clickMeButton;
	
	//	public void initialize(){
//		clickMeButton.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("You clicked me!");
//			}
//		});
//	}
	public void initialize() {
//		when there is exactly 1 parameter, you do not need to wrap the parameter in parentheses
		clickMeButton.setOnAction(event -> System.out.println("You clicked me!"));
	}
}

