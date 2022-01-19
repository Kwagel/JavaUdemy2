package JavaFXToDoList;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoData {
    private static final TodoData instance = new TodoData();
    private static final String filename = "TodoListItems.txt";

    private List<ToDoItem> toDoItems;
    private final DateTimeFormatter formatter;

    private TodoData() {
        formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    }

    public static TodoData getInstance() {
        return instance;
    }

    public List<ToDoItem> getToDoItems() {
        return toDoItems;
    }

//    public void setToDoItems(List<ToDoItem> toDoItems) {
//        this.toDoItems = toDoItems;
//    }

    public void loadTodoItems() throws IOException {
        toDoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);

                ToDoItem todoItem = new ToDoItem(shortDescription, details, date);
                toDoItems.add(todoItem);
            }
        }
    }

    public void storeTodoItems() throws IOException {
        Path path = Paths.get(filename);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (ToDoItem item : toDoItems) {
                bw.write(String.format("%s\t%s\t%s", item.getShortDescription(), item.getDetails(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }
        }


    }
}
