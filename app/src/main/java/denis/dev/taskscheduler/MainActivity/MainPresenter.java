package denis.dev.taskscheduler.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        mModel.deleteItem("Ху");
    }

    public void appClosing() {
        mModel.closeRealm();
    }

    public void onItemClicked(View view, @NonNull int position) {
        CheckBox chbDone = (CheckBox)view.findViewById(R.id.chbDone);
        if (chbDone.isChecked()) {
            Log.d(TAG, "onItemClicked: CHECKED pos : " + position);
            try {
                Log.d(TAG, "onItemClicked: position = " + position);
                getViewState().setDoneItem(position);
            } catch (NullPointerException e) {}
            
        } else {
            //Todo third activity after adding
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

    public void addNewItem(String newName, String newDate, String newTime, String newDescription) {
        Date newTimeDate = null;
        Date newDateDate = null;
        Log.d(TAG, "addNewItem: newDate string " + newDate);
        Log.d(TAG, "addNewItem: newTime string " + newTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            newTimeDate = dateFormat.parse(newTime);
            newDateDate = timeFormat.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Task newTask = new Task(newName, newDateDate, newTimeDate, newDescription);
        mModel.addNewItem(newTask);
        getViewState().refreshListView();
    }
}
