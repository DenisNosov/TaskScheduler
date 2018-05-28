package denis.dev.taskscheduler.AddingActivity;

import com.arellomobile.mvp.MvpView;

import denis.dev.taskscheduler.Common.Task;

public interface AddingView extends MvpView {
    void onFinish(String name, int newDay, int newMonth, int newYear, int newHour, int newMinute, String description);
    void setTvAddDate(String date);
    void setTvAddTime(String time);
}
