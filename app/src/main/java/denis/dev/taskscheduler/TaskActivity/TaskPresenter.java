package denis.dev.taskscheduler.TaskActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;

import denis.dev.taskscheduler.R;

@InjectViewState
public class TaskPresenter extends MvpPresenter<TaskView>{
    private static final String TAG = "TaskPresenter";
    Calendar currentDate = Calendar.getInstance();
    Calendar date = Calendar.getInstance();
    Calendar time = Calendar.getInstance();

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void init(Intent intent) {
		Calendar date = Calendar.getInstance();
		String name = intent.getStringExtra("name");
		String description = intent.getStringExtra("description");
		int year = intent.getIntExtra("year", date.get(Calendar.YEAR));
		int month = intent.getIntExtra("month", date.get(Calendar.MONTH));
		int day = intent.getIntExtra("day", date.get(Calendar.DAY_OF_MONTH));
		int hour = intent.getIntExtra("hour", date.get(Calendar.HOUR_OF_DAY));
		int minute = intent.getIntExtra("minute", date.get(Calendar.MINUTE));
		date.set(year, month, day, hour, minute);
		getViewState().setDate(date);
		getViewState().setTime(date);
		getViewState().setName(name);
		getViewState().setDescription(description);
	}

    public void tvTaskNameClicked(TaskActivity taskActivity, View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(taskActivity);
        final EditText edittext = new EditText(taskActivity);
        TextView tvTaskName = v.findViewById(R.id.tvTaskName);
        alert.setMessage("Enter new name: ");
        alert.setTitle("Renaming");
        edittext.setText(tvTaskName.getText());
        alert.setView(edittext);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newName = edittext.getText().toString();
                getViewState().setName(newName);
            }
        });
        alert.show();
    }

    public void btnChangeDateClicked(TaskActivity taskActivity) {
        new DatePickerDialog(taskActivity, d,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            currentDate.set(Calendar.YEAR, year);
            currentDate.set(Calendar.MONTH, monthOfYear);
            currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            getViewState().setDate(currentDate);
            setDate(currentDate);
            currentDate = Calendar.getInstance();
        }
    };

    public void btnChangeTimeClicked(TaskActivity taskActivity) {
        new TimePickerDialog(taskActivity, t,
                currentDate.get(Calendar.HOUR_OF_DAY),
                currentDate.get(Calendar.MINUTE), true)
                .show();
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            currentDate.set(Calendar.MINUTE, minute);
            getViewState().setTime(currentDate);
            setTime(currentDate);
            Log.d(TAG, "onTimeSet: " + currentDate.get(Calendar.HOUR_OF_DAY));
            Log.d(TAG, "onTimeSet: " + currentDate.get(Calendar.MINUTE));
            Log.d(TAG, "onTimeSet: " + currentDate.get(Calendar.AM_PM));
            currentDate = Calendar.getInstance();
        }
    };

    public void btnTaskOkClicked() {
        Log.d(TAG, "btnTaskOkClicked: " + time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE));
        getViewState().onFinish(true, true, date, time);
    }

    public void btnTaskDeleteClicked() {
        getViewState().onFinish(false, true, null, null);
    }

    public void onBackPressed() {
        getViewState().onFinish(false, false, null, null);
    }
}
