package JavaFXToDoList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    @FXML
    public TextArea itemDetailsTextArea;
    @FXML
    public Label deadlineLabel;
    @FXML
    public ToggleButton filterToggleButton;
    private ObservableList<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;

    private Predicate<TodoItem> wantAllItems;
    private Predicate<TodoItem> wantTodaysItems;

    private FilteredList<TodoItem> filteredList;

    public void initialize() {
        //        when the selected property, a listener is added then reads and sets the information as required
        //        This is databinding example

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            deleteItem(item);
        });
        listContextMenu.getItems().addAll(deleteMenuItem);


        todoListView.getSelectionModel().selectedItemProperty().isNotNull().addListener((newValue) -> {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            itemDetailsTextArea.setText(item.getDetails());
            //                You can format date objects using a date time formatter ofPattern.
            DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            deadlineLabel.setText(df.format(item.getDeadline()));
        });


        wantAllItems = item -> true;
        wantTodaysItems = item -> item.getDeadline().equals(LocalDate.now());
        //using a SortedList to automatically sort via date using Comparator comparing, using method expressions, using
        // LocalDate default compareTo method
        filteredList = new FilteredList<>(TodoData.getInstance().getToDoItems(), wantAllItems);
        SortedList<TodoItem> sortedList = new SortedList<>(filteredList, Comparator.comparing(TodoItem::getDeadline));
        //        setAll is an ObservableArrayList method so any data has be created in a ObservableArrayList FXCollections.
        //        Because we change the To do-data to do items to an observable list, FXCollections like observableList
        //         detect when changes are made and will automatically update.
        todoListView.setItems(sortedList);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                ListCell<TodoItem> cell = new ListCell<TodoItem>() {

                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);

                        } else {
                            setText(item.getShortDescription());
                            if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                            }
                        }
                    }

                };
                //                this adds a context menu to every cell in the cell factory that isn't empty, and
                //                will do it
                //                everytime something new is added or removed, as a change listener is listening for
                //                change
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(listContextMenu);
                    }
                });
                return cell;
            }

        });
    }

    @FXML
     void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new Todo item");
        dialog.setHeaderText("Use this dialog to create a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //            use the controller that you created when loading the fxml, using reference.getController()
            DialogController dialogController = fxmlLoader.getController();
            TodoItem newItem = dialogController.processResults();
            //            todoListView.getItems().setAll(TodoData.getInstance().getToDoItems());
            todoListView.getSelectionModel().select(newItem);
        } else {
            System.out.println("Cancel Pressed");
        }

    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }

    @FXML
    public void handleClickListView() {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());

    }

    public void deleteItem(TodoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete item: " + item.getShortDescription());
        alert.setContentText("Are you sure? Press oK to confirm, or cancel to Back out");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            TodoData.getInstance().deleteTodoItem(item);
        }
    }

    public void handleFilterButton() {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (filterToggleButton.isSelected()) {
            filteredList.setPredicate(wantTodaysItems);
//            when you click button, it selects either the same item as previous if present on sorted list, or first
            //            one, or nothing if empty
            if (filteredList.isEmpty()) {
                itemDetailsTextArea.clear();
                deadlineLabel.setText("");
            } else if (filteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            } else{
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            filteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }

    public void handleExit() {
        Platform.exit();
    }
}

