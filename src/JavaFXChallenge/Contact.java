package JavaFXChallenge;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
	private final SimpleStringProperty firstName = new SimpleStringProperty("");
	private final SimpleStringProperty lastName = new SimpleStringProperty("");
	private final SimpleStringProperty phoneNumber = new SimpleStringProperty("");
	private final SimpleStringProperty notes = new SimpleStringProperty("");

	public Contact(String firstName, String lastName, String phoneNumber, String notes) {
		this.firstName.set(firstName);
		this.lastName.set(lastName);
		this.phoneNumber.set(phoneNumber);
		this.notes.set(notes);
	}

	public Contact() {
	}


	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public SimpleStringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public SimpleStringProperty lastNameProperty() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	public SimpleStringProperty phoneNumberProperty() {
		return phoneNumber;
	}

	public String getNotes() {
		return notes.get();
	}

	public void setNotes(String notes) {
		this.notes.set(notes);
	}

	public SimpleStringProperty notesProperty() {
		return notes;
	}

	@Override
	public String toString() {
		return "Contact{" + "firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", notes=" + notes + '}';
	}
}
