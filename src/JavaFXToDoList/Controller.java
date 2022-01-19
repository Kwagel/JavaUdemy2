package JavaFXToDoList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    public TextArea itemDetailsTextArea;
    @FXML
    public Label deadlineLabel;
    private List<ToDoItem> toDoItems;
    @FXML
    private ListView<ToDoItem> todoListView;

    public void initialize() {
        ToDoItem item1 = new ToDoItem("Mail birthday card", "Buy a 30th birthday card for John",
                LocalDate.of(2022, Month.JANUARY, 18));
        ToDoItem item2 = new ToDoItem("Doctor's Appointment", "See Dr.smith at 123 Main Street",
                LocalDate.of(2022, Month.FEBRUARY, 21));
        ToDoItem item3 = new ToDoItem("Finish design proposal for client",
                "I promised Mike I'd finished proposal by November", LocalDate.of(2022, Month.JANUARY, 22));
        ToDoItem item4 = new ToDoItem("Pickup Dog at the train staiton", "Doug's arriving on MNovember 23",
                LocalDate.of(2021, Month.NOVEMBER, 23));
        ToDoItem item5 = new ToDoItem("Pick up dry cleaning", "The clothes should be ready by Wednesday",
                LocalDate.of(2022, Month.JANUARY, 20));

        toDoItems = new ArrayList<>();
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);

        todoListView.getSelectionModel().selectedItemProperty().addListener((newValue) -> {
            if (newValue != null) {
                ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                itemDetailsTextArea.setText(item.getDetails());
                //                You can format date objects using a date time formatter ofPattern.
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");

                deadlineLabel.setText(df.format(item.getDeadline()));

            }
        });
        todoListView.getItems().setAll(toDoItems);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
        //        itemDetailsTextArea.setText(todoListView.getSelectionModel().getSelectedItem().getDetails());
    }

    @FXML
    public void handleClickListView() {
        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());

    }

}

