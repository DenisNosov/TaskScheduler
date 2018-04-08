package denis.dev.taskscheduler.MainActivity;

import android.content.Context;

import java.util.ArrayList;

import denis.dev.taskscheduler.Common.Task;

public interface IModel {
    void initModel();
    void addNewItem(Task task);
    void deleteItem(String name);
    ArrayList<Task> getItems();
}
