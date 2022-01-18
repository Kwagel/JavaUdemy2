package JavaFXEventHandling;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class Controller {
	
	@FXML
	private TextField nameField;
	
//	not need but is good for debugging as it shows that a method is used on the FMXL fil
	@FXML
	public void onButtonClicked() {
		System.out.println("Hello " + nameField.getText());
	}
}
