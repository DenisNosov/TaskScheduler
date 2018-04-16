package denis.dev.taskscheduler.MainActivity;

import java.util.ArrayList;

import denis.dev.taskscheduler.Common.Task;

public interface IModel {
    void initModel();
    void addNewItem(Task task);
    void deleteItem(String name);
    ArrayList<Task> getItems();
    void closeRealm();
    void clear();
    boolean exists(String newName);
    Task find(String text);
}
