package JavaFXEventHandling;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Controller {
	
	
	@FXML
	public Button byeButton;
	@FXML
	public Button helloButton;
	@FXML
	private TextField nameField;
	@FXML
	private CheckBox ourCheckBox;
	@FXML
	private Label ourLabel;
	
	
	@FXML
	public void initialize() {
		helloButton.setDisable(true);
		byeButton.setDisable(true);
	}
	
	//	The UI thread sits and waits for user input, checks if application is waiting for that event, and if so,
//	dispatches the event to the event handler
//
	@FXML
	public void onButtonClicked(ActionEvent e) {
		if (e.getSource().equals(helloButton)) {
			
			System.out.println("Hello " + nameField.getText());
		} else if (e.getSource().equals(byeButton)) {
			System.out.println("Bye " + nameField.getText());
		}
		System.out.println("The following button was pressed: " + e.getSource());
		
//		All events must be run on the application UI thread, otherwise the information could be compromised. To run a
//		command on the UI thread, you would call it from Platform.runlater()
		Runnable task = () -> {
			try {
				String s = Platform.isFxApplicationThread() ? "UI Thread": "Background Thread";
				System.out.println("I'm going to sleep on the: " + s);
				Thread.sleep(10000);
				Platform.runLater(() -> {
					String s2 = Platform.isFxApplicationThread() ? "UI Thread": "Background Thread";
					System.out.println("I'm updating the label on the: " + s2);
					ourLabel.setText("We did something");
				});
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		};
		new Thread(task).start();
		
		if (ourCheckBox.isSelected()) {
			nameField.clear();
			helloButton.setDisable(true);
			byeButton.setDisable(true);
		}
	}
	
	@FXML
	public void handleKeyReleased() {
		String text = nameField.getText();
		boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
		helloButton.setDisable(disableButtons);
		
		byeButton.setDisable(disableButtons);
		
	}
	
	public void handleChange() {
		System.out.println("THe check is " + (ourCheckBox.isSelected() ? "checked" : "not checked"));
	}
}
