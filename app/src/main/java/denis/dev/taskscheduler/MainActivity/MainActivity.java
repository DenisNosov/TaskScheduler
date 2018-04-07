package denis.dev.taskscheduler.MainActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.R;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    private static final String TAG = "MainActivity";
    @InjectPresenter
    MainPresenter mPresenter;

    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasks.add(new Task("task1", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"lmao"));
        tasks.add(new Task("task2", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"rofl"));
        tasks.add(new Task("task3", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"kek"));
        tasks.add(new Task("task4", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"lol"));
        tasks.add(new Task("task5", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"lmao1"));

    }
}
