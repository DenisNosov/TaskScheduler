package denis.dev.taskscheduler.MainActivity;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView{
    void onAddBtnClick();
    void refreshListView();
}
