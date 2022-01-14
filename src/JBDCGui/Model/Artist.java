package JBDCGui.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Artist {
	public SimpleIntegerProperty id;
	public SimpleStringProperty name;
	

	public SimpleIntegerProperty idProperty() {
		return id;
	}
	
	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}
	
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}
}
