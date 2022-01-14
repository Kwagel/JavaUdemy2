package JBDCGui;

import JBDCGui.Model.Artist;
import JBDCGui.Model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class Controller {
}
class GetAllArtistsTask extends Task {
	@Override
	public ObservableList<Artist> call() throws Exception {
		return FXCollections.observableArrayList(Datasource.getInstance().queryArtist(Datasource.ORDER_BY_ASC));
	}
}