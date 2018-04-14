package denis.dev.taskscheduler.MainActivity;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView{
    void initView();
    void refreshListView();
    void setDoneItem(int position);
    void startActivityAdd();
    void makeNewToast(String text);
}
