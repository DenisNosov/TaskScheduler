package denis.dev.taskscheduler.MainActivity;

import com.arellomobile.mvp.MvpView;

import denis.dev.taskscheduler.Common.Task;

public interface MainView extends MvpView{
    void initView();
    void refreshListView();
    void setDoneItem(int position);
    void startActivityAdd();
    void makeNewToast(String text, String newName, int newDay, int newMonth, int newYear, int newHour, int newMinute, String newDescription);
    void startActivityTask(Task task);
}