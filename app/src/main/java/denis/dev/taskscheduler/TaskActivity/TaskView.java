package denis.dev.taskscheduler.TaskActivity;

import com.arellomobile.mvp.MvpView;

import java.util.Calendar;

public interface TaskView extends MvpView {
    void setDate(Calendar date);
    void setTime(Calendar date);
    void setName(String name);
    void setDescription(String description);
    void onFinish(boolean b, boolean b1, Calendar date, Calendar time);
}
