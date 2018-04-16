package denis.dev.taskscheduler.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import denis.dev.taskscheduler.AddingActivity.AddingActivity;
import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.R;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private static final String TAG = "MainPresenter";
    private IModel mModel;

    public MainPresenter() {
    }

    void init(IModel model) {
        this.mModel = model;
        mModel.initModel();
//        mModel.clear();
    }

    public void appClosing() {
        mModel.closeRealm();
    }

    public void onItemClicked(View view, @NonNull int position) {
        CheckBox chbDone = (CheckBox)view.findViewById(R.id.chbDone);
        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        if (chbDone.isChecked()) {
            Log.d(TAG, "onItemClicked: CHECKED pos : " + position);
            try {
                Log.d(TAG, "onItemClicked: position = " + position);
                getViewState().setDoneItem(position);
            } catch (NullPointerException e) {}
        } else {
            Log.d(TAG, "onItemClicked: " + mModel.find(tvName.getText().toString()).getDate().toString());
            getViewState().startActivityTask(mModel.find(tvName.getText().toString()));
        }
    }

    public void onChbClicked(int position) {
        Log.d(TAG, "onItemClicked: position = " + position);
        getViewState().setDoneItem(position);
    }

    public void onItemDone(Task item) {
        Log.d(TAG, "onItemDone: Deleting item " + item.getName());
        mModel.deleteItem(item.getName());
        Log.d(TAG, "onItemDone: item " + item.getName() + " deleted");
        getViewState().refreshListView();
    }

    public ArrayList<Task> getItems() {
        return mModel.getItems();
    }

    public void addNew() {
        getViewState().startActivityAdd();
    }

    public void addNewItem(String newName, int newDay, int newMonth, int newYear, int newHour, int newMinute, String newDescription) {
        if (mModel.exists(newName)) {
            getViewState().makeNewToast("Task " + newName + " already exists.");
        } else {
            Calendar newDate = Calendar.getInstance();
            Calendar newTime = Calendar.getInstance();
            newDate.set(newYear, newMonth, newDay);
            newTime.set(0, 0, 0, newHour, newMinute);
            Task newTask = new Task(newName, newDate.getTime(), newTime.getTime(), newDescription);
            mModel.addNewItem(newTask);
            getViewState().refreshListView();
        }
    }
}