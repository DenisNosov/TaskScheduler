package denis.dev.taskscheduler.MainActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.Common.TaskAdapter;
import denis.dev.taskscheduler.R;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    private static final String TAG = "MainActivity";
    @InjectPresenter
    MainPresenter mPresenter;

    @BindView(R.id.lvTasks)
    ListView lvTasks;

    @OnClick(R.id.fabAdd)
    void logRealmAll() {
        mPresenter.logRealm();
    }

    @OnItemClick(R.id.lvTasks)
    void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: item " + position + " clicked");
        mPresenter.onItemClicked(view, position);
    }

    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks.add(new Task("task1", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"lmao"));
        tasks.add(new Task("task2", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"rofl"));
        tasks.add(new Task("task3", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"kek"));
        tasks.add(new Task("task4", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"lol"));

        initView();
    }

    public void initView() {
        ButterKnife.bind(this);
        IModel mModel = new MainModel(this);
        mPresenter.init(mModel);
        for (Task task : tasks) {
            mModel.addNewItem(task);
        }
        taskAdapter = new TaskAdapter(this, R.layout.task_layout, mModel.getItems());
        lvTasks.setAdapter(taskAdapter);
    }

    @Override
    public void refreshListView() {
        Log.d(TAG, "refreshListView: refreshing listView");
        TaskAdapter newTaskAdapter = new TaskAdapter(this, R.layout.task_layout, mPresenter.getItems());
        lvTasks.setAdapter(newTaskAdapter);
        Log.d(TAG, "refreshListView: listView refreshed");
    }

    @Override
    public void setDoneItem(int position) {
        Log.d(TAG, "setDoneItem: Item set done");
        taskAdapter.getItem(position).setDone();
        mPresenter.onItemDone(taskAdapter.getItem(position));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.appClosing();
    }
}
