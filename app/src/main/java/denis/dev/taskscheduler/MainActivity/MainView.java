package denis.dev.taskscheduler.MainActivity;

import com.arellomobile.mvp.MvpView;

import denis.dev.taskscheduler.Common.Task;

public interface MainView extends MvpView{
    void initView();
    void refreshListView();
    void setDoneItem(int position);
    void startActivityAdd();
    void makeNewToast(String text);
    void startActivityTask(Task task);
}