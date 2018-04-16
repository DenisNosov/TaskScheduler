package denis.dev.taskscheduler.TaskActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;

@InjectViewState
public class TaskPresenter extends MvpPresenter<TaskView> {

    Calendar currentDate = Calendar.getInstance();

    public void init(String name, Calendar date, String description) {
        getViewState().setDate(date);
        getViewState().setTime(date);
        getViewState().setName(name);
    }

    public void tvTaskNameClicked(TaskActivity taskActivity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(taskActivity);
        final EditText edittext = new EditText(taskActivity);
        alert.setMessage("Enter new name: ");
        alert.setTitle("Renaming");
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
            currentDate = Calendar.getInstance();
        }
    };
}
