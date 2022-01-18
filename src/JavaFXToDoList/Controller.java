package JavaFXToDoList;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItem> toDoItems;

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

        toDoItems = new ArrayList<ToDoItem>();
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);
    }

}

