package denis.dev.taskscheduler.AddingActivity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import denis.dev.taskscheduler.Common.Task;

@InjectViewState
public class AddingPresenter extends MvpPresenter<AddingView> {

    String newDate;
    String newTime;

    public void setTime(String time) {
        newTime = time;
    }

    public void setDate(String date) {
        newDate = date;
    }


    public void dateChanged(int year, int month, int day) {
        String strDate = String.valueOf(day) + "." + String.valueOf(month+1) + "." + String.valueOf(year);
        setDate(strDate);
    }

    public void timeChanged(int hourOfDay, int minute) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        String strTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
        setTime(strTime);
    }

    public void onAddClicked(String name, String description) {
        getViewState().onFinish(name, newDate, newTime, description);
    }
}
