package denis.dev.taskscheduler.MainActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.Common.TaskAdapter;
import denis.dev.taskscheduler.R;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    private static final String TAG = "MainActivity";
    @InjectPresenter
    MainPresenter mPresenter;

    @BindView(R.id.lvTasks)
    ListView lvTasks;

    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tasks.add(new Task("task1", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"lmao"));
        tasks.add(new Task("task2", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"rofl"));
        tasks.add(new Task("task3", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"kek"));
        tasks.add(new Task("task4", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"lol"));

        taskAdapter = new TaskAdapter(this, R.layout.task_layout, tasks);
        lvTasks.setAdapter(taskAdapter);
    }
}
