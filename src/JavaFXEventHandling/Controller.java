package JavaFXEventHandling;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Controller {
	
	
	@FXML
	public Button byeButton;
	@FXML
	public Button helloButton;
	@FXML
	private TextField nameField;
	
	@FXML
	public void initialize(){
		helloButton.setDisable(true);
		byeButton.setDisable(true);
	}
	
	@FXML
	public void onButtonClicked(ActionEvent e) {
		if (e.getSource().equals(helloButton)) {
			
			System.out.println("Hello " + nameField.getText());
		} else if (e.getSource().equals(byeButton)) {
			System.out.println("Bye " + nameField.getText());
		}
		System.out.println("The following button was pressed: " + e.getSource());
		
	}
	
	@FXML
	public void handleKeyReleased(){
		String text = nameField.getText();
		boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
		helloButton.setDisable(disableButtons);;
		byeButton.setDisable(disableButtons);
		
	}
	
	
}
