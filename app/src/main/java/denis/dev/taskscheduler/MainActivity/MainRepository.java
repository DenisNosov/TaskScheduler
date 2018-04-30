package denis.dev.taskscheduler.MainActivity;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import denis.dev.taskscheduler.Common.Task;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainRepository implements IModel{
    Context mContext;
    Realm realm;
    private static final String TAG = "MainRepository";

    public MainRepository(Context context) {
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
        Log.d(TAG, "deleteItem: transaction began, deleting item " + name);
        RealmResults<Task> tasksToDelete = realm.where(Task.class).equalTo("name", name).findAll();
        if (!tasksToDelete.isEmpty()) {
            tasksToDelete.get(0).deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public ArrayList<Task> getItems() {
        RealmResults<Task> taskRealmResults = realm.where(Task.class).findAll();
        List<Task> taskList = realm.copyFromRealm(taskRealmResults);
        return new ArrayList<Task>(taskList);
    }

    @Override
    public void closeRealm() {
        realm.close();
    }

    @Override
    public void clear() {
        realm.beginTransaction();
        RealmResults<Task> toDelete = realm.where(Task.class).findAll();
        if (!toDelete.isEmpty()) {
            for (int i = toDelete.size() - 1; i >= 0; i--) {
                Log.d(TAG, "deleteItem: item found " + toDelete.get(0).getName());
                toDelete.get(i).deleteFromRealm();
            }
        }
        realm.commitTransaction();
    }

    @Override
    public boolean exists(String newName) {
        RealmResults<Task> exists = realm.where(Task.class).equalTo("name", newName).findAll();
        if (exists.isEmpty()) return false;
        else return true;
    }

    @Override
    public Task find(String text) {
        RealmResults<Task> findTask = realm
                .where(Task.class).equalTo("name", text).findAll();
        return findTask.get(0);
    }
}