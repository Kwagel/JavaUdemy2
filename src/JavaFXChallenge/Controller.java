package JavaFXChallenge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {
	@FXML
	public TableView<Contact> contactsTable;
	@FXML
	public TableColumn<Contact, String> firstNameCol;
	@FXML
	public TableColumn<Contact, String> lastNameCol;
	@FXML
	public TableColumn<Contact, String> phoneNumberCol;
	@FXML
	public TableColumn<Contact, String> notesCol;
	@FXML
	public BorderPane mainPanel;

	private ContactData data;

	public void initialize() {
		data = new ContactData();
		data.loadContacts();
		contactsTable.setItems(data.getContacts());
		contactsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


	}

	public void showAddContactDialog() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainPanel.getScene().getWindow());
		dialog.setTitle("Add new contact");
		dialog.setHeaderText("Use this dialog to create a new contact");
		//		create a FXMLLoader with the correct fxml set into it but setting location
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
		//		then try to actually load the FXML file into the content of the Dialog Pane
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load Dialog");
			e.printStackTrace();
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

		//		An optional is a container case that can contain a null result, but it has two methods, isPresent and get to
		//		check and retrieve the values; when set to button type, this can be used as a placeholder to check for
		//		button types
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			ContactController contactController = fxmlLoader.getController();
			Contact newContact = contactController.getNewContact();
			data.addContact(newContact);
			data.saveContacts();
		}


	}


	public void handleClick(MouseEvent mouseEvent) {
	}

	public void showEditContactDialog() {
		Contact selected = contactsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No Contact Selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select the contact you want to edit.");
			alert.showAndWait();
			return;
		}
		Dialog<ButtonType> dialog = new Dialog<>();
		//		Must initiate panel
		dialog.initOwner(mainPanel.getScene().getWindow());
		dialog.setTitle("Edit Contact");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		}catch (IOException e){
			System.out.println("couldn't load dialog");
			e.printStackTrace();
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		ContactController contactController = fxmlLoader.getController();
		contactController.editContact(selected);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK){
			contactController.updateContact(selected);
			data.saveContacts();
		}
	}

	public void deleteContact() {
		Contact selected = contactsTable.getSelectionModel().getSelectedItem();
		if (selected == null){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No contact Selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select the contact you want to delete");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Contact");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete the selected contact :" + selected.getFirstName() + " " + selected.getLastName());
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK){
			data.deleteContact(selected);
			data.saveContacts();
		}
	}
}
