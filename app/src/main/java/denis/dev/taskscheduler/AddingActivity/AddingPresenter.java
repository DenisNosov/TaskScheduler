package denis.dev.taskscheduler.AddingActivity;

import android.util.Log;

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


    public void dateChanged(int year, int month, int day) {
        setNewDay(day);
        setNewMonth(month);
        setNewYear(year);
    }

    public void timeChanged(int hourOfDay, int minute) {
        setNewHour(hourOfDay);
        setNewMinute(minute);
    }

    public void onAddClicked(String name, String description) {
        getViewState().onFinish(name, newDay, newMonth, newYear, newHour, newMinute, description);
    }
}
