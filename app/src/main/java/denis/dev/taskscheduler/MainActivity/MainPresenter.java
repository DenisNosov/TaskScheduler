package denis.dev.taskscheduler.MainActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import denis.dev.taskscheduler.Common.Data;
import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.Common.TaskAdapter;
import denis.dev.taskscheduler.Common.TaskAdapter.OnCheckBoxListener;
import denis.dev.taskscheduler.R;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>{
    private static final String TAG = "MainPresenter";
    private IModel mModel;
	public OnCheckBoxListener onCheckBoxListener;

    public MainPresenter() {
    }

    void init(IModel model) {
        this.mModel = model;
        mModel.initModel();

		onCheckBoxListener = new OnCheckBoxListener() {
			@Override
			public void onChbClicked(int position) {
				Log.d(TAG, "onChbClicked: position = " + position);
				getViewState().setDoneItem(position);
			}
		};
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
            Log.d(TAG, "onItemClicked: " + mModel.find(tvName.getText().toString()));
            getViewState().startActivityTask(mModel.find(tvName.getText().toString()));
        }
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
            getViewState().makeNewToast("Task " + newName + " already exists.", newName, newDay, newMonth, newYear, newHour, newMinute, newDescription);
        } else {
            addFinallyItem(newName, newDay, newMonth, newYear, newHour, newMinute, newDescription);
        }
    }

    public void addFinallyItem(String newName, int newDay, int newMonth, int newYear, int newHour, int newMinute, String newDescription) {
        Calendar newDateTime = Calendar.getInstance();
        newDateTime.set(newYear, newMonth, newDay, newHour, newMinute);
        Task newTask = new Task(newName, newDateTime.getTime(), newDateTime.getTime(), newDescription);
        mModel.addNewItem(newTask);

        //NOTIFICATIONS

		getViewState().createNotification(newTask, newDateTime);

		//NOTIFICATIONS_END

        getViewState().refreshListView();
    }

    public void ActivityFinished(int requestCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (data.getBooleanExtra("addingNew", false)) {
					String newName = data.getStringExtra("name");
					int newDay = data.getIntExtra("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
					int newMonth = data.getIntExtra("month", Calendar.getInstance().get(Calendar.MONTH));
					int newYear = data.getIntExtra("year", Calendar.getInstance().get(Calendar.YEAR));
					int newHour = data.getIntExtra("hour", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
					int newMinute = data.getIntExtra("minute", Calendar.getInstance().get(Calendar.MINUTE));
					String newDescription = data.getStringExtra("description");
					addNewItem(newName, newDay, newMonth, newYear, newHour, newMinute, newDescription);
				}
                break;
            case 2:
                boolean addingNew = data.getBooleanExtra("addingNew", false);
                boolean deletingOld = data.getBooleanExtra("deletingOld", false);
                if (addingNew) {
                    String newName1 = data.getStringExtra("newName");
                    int newDay1 = data.getIntExtra("newDay", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    int newMonth1 = data.getIntExtra("newMonth", Calendar.getInstance().get(Calendar.MONTH));
                    int newYear1 = data.getIntExtra("newYear", Calendar.getInstance().get(Calendar.YEAR));
                    int newHour1 = data.getIntExtra("newHour", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    int newMinute1 = data.getIntExtra("newMinute", Calendar.getInstance().get(Calendar.MINUTE));
                    int newIsAm1 = data.getIntExtra("newIsAm", Calendar.getInstance().get(Calendar.AM_PM));
                    String oldName = data.getStringExtra("oldName");
                    String newDescription1 = data.getStringExtra("newDescription");
                    if (deletingOld) mModel.deleteItem(oldName);
                    addFinallyItem(newName1, newDay1, newMonth1, newYear1, newHour1, newMinute1, newDescription1);
                    break;
                } else {
                    String oldName = data.getStringExtra("oldName");
                    if (deletingOld) mModel.deleteItem(oldName);
                    getViewState().refreshListView();
                }
        }
    }
}