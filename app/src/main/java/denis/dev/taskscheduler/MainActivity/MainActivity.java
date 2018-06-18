package denis.dev.taskscheduler.MainActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import denis.dev.taskscheduler.AddingActivity.AddingActivity;
import denis.dev.taskscheduler.Common.Data;
import denis.dev.taskscheduler.Common.NotificationPublisher;
import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.Common.TaskAdapter;
import denis.dev.taskscheduler.R;
import denis.dev.taskscheduler.TaskActivity.TaskActivity;

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

    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        ButterKnife.bind(this);
        IModel mModel = new MainRepository(this);
        mPresenter.init(mModel);
        taskAdapter = new TaskAdapter(this, R.layout.task_layout, mPresenter.getItems(), mPresenter.onCheckBoxListener);
        lvTasks.setAdapter(taskAdapter);
    }

    @Override
    public void refreshListView() {
        Log.d(TAG, "refreshListView: refreshing listView");
        taskAdapter = new TaskAdapter(this, R.layout.task_layout, mPresenter.getItems(), mPresenter.onCheckBoxListener);
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
    public void makeNewToast(String text, final String newName, final int newDay, final int newMonth, final int newYear, final int newHour, final int newMinute, final String newDescription) {
        Snackbar.make(lvTasks, text, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void startActivityTask(Task task) {
        Intent intent = createIntent(task);
        startActivityForResult(intent, 2);
    }

	@Override
	public void createNotification(Task newTask, Calendar newDateTime) {
		Intent contentIntent = createIntent(newTask);
		PendingIntent pendingContentIntent = PendingIntent.getActivity(this, Data.NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		Notification notification = new Notification.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher_foreground)
				.setContentTitle("Reminder")
				.setContentText(newTask.getName())
				.setWhen(System.currentTimeMillis())
				.setAutoCancel(true)
				.setContentIntent(pendingContentIntent)
				.build();

		Intent notificationIntent = new Intent(this, NotificationPublisher.class);
		notificationIntent.putExtra("Notification", notification);
		notificationIntent.putExtra("Notification id", Data.NOTIFICATION_ID);
		PendingIntent pendingNotificationIntent = PendingIntent.getBroadcast(this, Data.NOTIFICATION_ID++, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, newDateTime.getTimeInMillis(), pendingNotificationIntent);

	}

	public Intent createIntent(Task task) {
        Calendar date = Calendar.getInstance();
        Calendar time = Calendar.getInstance();
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("name", task.getName());
        date.setTime(task.getDate());
        time.setTime(task.getTime());
        intent.putExtra("year", date.get(Calendar.YEAR));
        intent.putExtra("month", date.get(Calendar.MONTH));
        intent.putExtra("day", date.get(Calendar.DAY_OF_MONTH));
        intent.putExtra("hour", time.get(Calendar.HOUR_OF_DAY));
        intent.putExtra("minute", time.get(Calendar.MINUTE));
        intent.putExtra("description", task.getDescription());
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.ActivityFinished(requestCode, data);
    }
}