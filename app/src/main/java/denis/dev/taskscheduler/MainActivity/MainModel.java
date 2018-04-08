package denis.dev.taskscheduler.MainActivity;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import denis.dev.taskscheduler.Common.Task;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainModel implements IModel{
    MainPresenter mPresenter;
    Context mContext;
    Realm realm;

    public MainModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void initModel() {
        Realm.init(mContext);
        realm = Realm.getDefaultInstance();

    }

    @Override
    public void addNewItem(Task task) {
        realm.beginTransaction();
        final Task managedTask = realm.copyToRealm(task);
        realm.commitTransaction();
    }

    @Override
    public void deleteItem(String name) {
        realm.beginTransaction();
        RealmResults<Task> tasksToDelete = realm.where(Task.class).equalTo("name", name).findAll();
        if (!tasksToDelete.isEmpty()) {
            for (int i = tasksToDelete.size() - 1; i >= 0; i--) {
                tasksToDelete.get(i).deleteFromRealm();
            }
        }
    }

    @Override
    public ArrayList<Task> getItems() {
        RealmResults<Task> taskRealmResults = realm.where(Task.class).findAll();
        List<Task> taskList = realm.copyFromRealm(taskRealmResults);
        ArrayList<Task> taskArrayList = new ArrayList<Task>(taskList);
        return taskArrayList;
    }

    @Override
    public void closeRealm() {
        realm.close();
    }
}
