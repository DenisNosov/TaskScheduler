package denis.dev.taskscheduler.TaskActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import denis.dev.taskscheduler.R;

public class TaskActivity extends MvpAppCompatActivity implements TaskView {
    @InjectPresenter
    TaskPresenter taskPresenter;

    @BindView(R.id.tvTaskName)
    TextView tvTaskName;

    @BindView(R.id.tvTaskTime)
    TextView tvTaskTime;

    @BindView(R.id.tvTaskDate)
    TextView tvTaskDate;

    @BindView(R.id.etTaskDescription)
    EditText etTaskDescription;

    @OnClick(R.id.tvTaskName)
    void tvTaskNameClick() {
        taskPresenter.tvTaskNameClicked(this);
    }

    @OnClick(R.id.btnChangeDate)
    void btnChangeDateClick() {
        taskPresenter.btnChangeDateClicked(this);
    }

    @OnClick(R.id.btnChangeTime)
    void btnChangeTimeClick() {
        taskPresenter.btnChangeTimeClicked(this);
    }

    @OnClick(R.id.btnTaskOk)
    void btnTaskOkClick(View view) {
        taskPresenter.btnTaskOkClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        Calendar date = Calendar.getInstance();
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        int year = getIntent().getIntExtra("year", date.get(Calendar.YEAR));
        int month = getIntent().getIntExtra("month", date.get(Calendar.MONTH));
        int day = getIntent().getIntExtra("day", date.get(Calendar.DAY_OF_MONTH));
        int hour = getIntent().getIntExtra("hour", date.get(Calendar.HOUR_OF_DAY));
        int minute = getIntent().getIntExtra("minute", date.get(Calendar.MINUTE));
        date.set(year, month, day, hour, minute);
        taskPresenter.init(name, date, description);
        etTaskDescription.setText(description);
    }


    @Override
    public void setDate(Calendar date) {
        tvTaskDate.setText(DateUtils.formatDateTime(this, date.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    public void setTime(Calendar date) {
        tvTaskTime.setText(DateUtils.formatDateTime(this, date.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    @Override
    public void setName(String name) {
        tvTaskName.setText(name);
    }

    @Override
    public void onFinish(Calendar date, Calendar time) {
        Intent intent = new Intent();
        intent.putExtra("newName", tvTaskName.getText().toString());
        intent.putExtra("newDay", date.get(Calendar.DAY_OF_MONTH));
        intent.putExtra("newMonth", date.get(Calendar.MONTH));
        intent.putExtra("newYear", date.get(Calendar.YEAR));
        intent.putExtra("newHour", time.get(Calendar.HOUR_OF_DAY));
        intent.putExtra("newMinute", time.get(Calendar.MINUTE));
        intent.putExtra("newIsAm", time.get(Calendar.AM_PM));
        intent.putExtra("newDescription", etTaskDescription.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
