package JavaFXCSS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class Controller {
	@FXML
	public Button button4;
	@FXML
	public Label label;
	@FXML
	public GridPane gridPane;
	@FXML
	public WebView webview;


	public void initialize() {
		button4.setEffect(new DropShadow());
	}

	@FXML
	public void handleMouseEnter() {
		label.setScaleX(2.0);
		label.setScaleY(2.0);
	}

	@FXML
	public void handleMouseExit() {
		label.setScaleX(1.0);
		label.setScaleY(1.0);
	}

	@FXML
	public void handleClick() {
		//        FileChooser forces you to choose a file, so you cannot choose an entire directory.
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Application File");
		//        You can choose which file types something can be saved down , by adding extension filters to the
		//        list of
		//        extension filters using get extension filters and creating new extension-filters which are
		//        inherited inside the FileChooser class.
		// Using a catch app *>* allows you to view, open and save any file type
		//        you can have multiple extensions listed into one description, e.g all image file types
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
		                                     new FileChooser.ExtensionFilter("PDF", "*.pdf"),
		                                     new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"),
		                                     new FileChooser.ExtensionFilter("All Files", "*.*"));

		//        choose will open a dialog attached to a certain window, so you cannot interact with said window
		//        without completing the chooser or closing it.
		//        by using gridPanes window, it made the popup modal
		//        DirectoryChooser chooser = new DirectoryChooser();
		//        File file = chooser.showDialog(gridPane.getScene().getWindow());
		//        To be able to select multiple files with shift click, use showOpenMultipleDialog
		List<File> file = chooser.showOpenMultipleDialog(gridPane.getScene().getWindow());
		if (file != null) {
			for (int i = 0; i < file.size(); i++) {
				System.out.println(file.get(i).getPath());
			}
		} else {
			System.out.println("Chooser was cancelled");
		}
	}

	@FXML
	public void handleLinkClick(ActionEvent actionEvent) {
		//		System.out.println("Link Clicked");
		//		try {
		//			Desktop.getDesktop().browse(new URI("http://www.javafx.com"));
		//		} catch (IOException | URISyntaxException e) {
		//			e.printStackTrace();
		//		}
		//		WebEngine allows you to load web views, using the default engine
		WebEngine engine = webview.getEngine();
		engine.load("http://www.javafx.com");

	}
}
