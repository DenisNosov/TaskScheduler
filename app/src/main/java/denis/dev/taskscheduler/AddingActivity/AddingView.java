package denis.dev.taskscheduler.AddingActivity;

import com.arellomobile.mvp.MvpView;

import denis.dev.taskscheduler.Common.Task;

public interface AddingView extends MvpView {
    void onFinish(String name, String newDate, String newTime, String description);
}
