package denis.dev.taskscheduler.AddingActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import denis.dev.taskscheduler.Common.Task;

import static android.content.ContentValues.TAG;

@InjectViewState
public class AddingPresenter extends MvpPresenter<AddingView> {
    private static final String TAG = "AddingPresenter";
    int newDay, newMonth, newYear;
    int newHour, newMinute;

    public void setNewMonth(int newMonth) {
        this.newMonth = newMonth;
    }

    public void setNewYear(int newYear) {
        this.newYear = newYear;
    }

    public void setNewHour(int newHour) {
        this.newHour = newHour;
    }

    public void setNewMinute(int newMinute) {
        this.newMinute = newMinute;
    }

    public void setNewDay(int day) {
        newDay = day;
    }

	public int getNewDay() {
		return newDay;
	}

	public int getNewMonth() {
		return newMonth;
	}

	public int getNewYear() {
		return newYear;
	}

	public int getNewHour() {
		return newHour;
	}

	public int getNewMinute() {
		return newMinute;
	}

	public void dateChanged(int year, int month, int day) {
        setNewDay(day);
        setNewMonth(month);
        setNewYear(year);
        getViewState().setTvAddDate(day + "." + month + "." + year);
    }

    public void timeChanged(int hourOfDay, int minute) {
        setNewHour(hourOfDay);
        setNewMinute(minute);
        getViewState().setTvAddTime(hourOfDay + ":" + minute);
    }

    public void onAddClicked(String name, String description) {
        getViewState().onFinish(name, newDay, newMonth, newYear, newHour, newMinute, description);
    }



	public void onAddTimeClicked(AddingActivity addingActivity) {
    	new TimePickerDialog(addingActivity, t,
				getNewHour(), getNewMinute(), true)
				.show();
	}

	TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			timeChanged(hourOfDay, minute);
		}
	};

	public void onAddDateClicked(AddingActivity addingActivity) {
		new DatePickerDialog(addingActivity, d,
				getNewYear(), getNewMonth(), getNewDay())
				.show();
	}

	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
			dateChanged(year, month, dayOfMonth);
		}
	};
}
