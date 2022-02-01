package JavaFXChallenge;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

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


	public void initialize() {
		//		firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
		//		lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
		//		phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
		//		notesCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
		contactsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		contactsTable.getSelectionModel().selectedItemProperty().isNotNull().addListener((newValue) -> {
			Contact item = contactsTable.getSelectionModel().getSelectedItem();
			firstNameCol.setText(item.getFirstName());
			lastNameCol.setText(item.getLastName());
			phoneNumberCol.setText(item.getPhoneNumber());
			notesCol.setText(item.getNotes());
		});


	}

	public void handleClick(MouseEvent mouseEvent) {
		contactsTable.getSelectionModel().selectedItemProperty().isNotNull().addListener((newValue) -> {
			//			Contact contact = contactTableView.getSelectionModel().getSelectedItem();

		});
	}

	@FXML
	public void showAddContactDialog() {

	}
}
