package denis.dev.taskscheduler.MainActivity;

import android.content.Context;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView{
    void initView();
    void refreshListView();
    void setDoneItem(int position);
}
