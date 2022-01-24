package JavaFXCSS;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class Controller {
    @FXML
    public Button button4;
    @FXML
    public Label label;
    @FXML
    public GridPane gridPane;


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
        //        FileChooser chooser = new FileChooser();
        //        choose will open a dialog attached to a certain window, so you cannot interact with said window without
        //        completing the chooser or closing it.
        //        by using gridPanes window, it made the popup modal
        DirectoryChooser chooser = new DirectoryChooser();
        File file = chooser.showDialog(gridPane.getScene().getWindow());
        if (file != null) {
            System.out.println(file.getPath());
        } else {
            System.out.println("Chooser was cancelled");
        }
        //        chooser.showOpenDialog(gridPane.getScene().getWindow());
    }
}
