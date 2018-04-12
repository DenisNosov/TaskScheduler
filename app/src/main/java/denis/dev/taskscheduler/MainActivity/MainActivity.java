package denis.dev.taskscheduler.MainActivity;

import android.content.Intent;
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
import denis.dev.taskscheduler.AddingActivity.AddingActivity;
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
    void addNew() {
        mPresenter.addNew();
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
        IModel mModel = new MainRepository(this);
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
        taskAdapter = new TaskAdapter(this, R.layout.task_layout, mPresenter.getItems());
        lvTasks.setAdapter(taskAdapter);
        Log.d(TAG, "refreshListView: listView refreshed");
    }

    @Override
    public void setDoneItem(int position) {
        taskAdapter.getItem(position).setDone();
        Log.d(TAG, "setDoneItem: Item " + taskAdapter.getItem(position).getName() + " setting done");
        Log.d(TAG, "setDoneItem: Item set done pos: " + position);
        mPresenter.onItemDone(taskAdapter.getItem(position));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.appClosing();
    }

    public void startActivityAdd() {
        Intent intent = new Intent(this, AddingActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newName = data.getStringExtra("name");
        String newDate = data.getStringExtra("date");
        String newTime = data.getStringExtra("time");
        String newDescription = data.getStringExtra("description");
        mPresenter.addNewItem(newName, newDate, newTime, newDescription);
    }
}
