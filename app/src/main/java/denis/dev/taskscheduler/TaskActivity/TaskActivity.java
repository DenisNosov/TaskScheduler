package denis.dev.taskscheduler.TaskActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import denis.dev.taskscheduler.Common.Task;
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

    String oldName;
    Intent intent;

    @OnClick(R.id.tvTaskName)
    void tvTaskNameClick(View v) {
        taskPresenter.tvTaskNameClicked(this, v);
    }

    @OnClick(R.id.tvTaskDate)
    void btnChangeDateClick() {
        taskPresenter.btnChangeDateClicked(this);
    }

    @OnClick(R.id.tvTaskTime)
    void btnChangeTimeClick() {
        taskPresenter.btnChangeTimeClicked(this);
    }

    @OnClick(R.id.btnTaskOk)
    void btnTaskOkClick(View view) {
        taskPresenter.btnTaskOkClicked();
    }

    @OnClick(R.id.btnTaskDelete)
    void btnTaskDeleteClick() {
        taskPresenter.btnTaskDeleteClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        oldName = getIntent().getStringExtra("name");
        taskPresenter.init(getIntent());
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
	public void setDescription(String description) {
		etTaskDescription.setText(description);
	}

	@Override
    public void onFinish(boolean addingNew, boolean deletingOld, Calendar date, Calendar time) {
        if (addingNew) {
            intent = new Intent();
            intent.putExtra("addingNew", addingNew);
            intent.putExtra("newName", tvTaskName.getText().toString());
            intent.putExtra("newDay", date.get(Calendar.DAY_OF_MONTH));
            intent.putExtra("newMonth", date.get(Calendar.MONTH));
            intent.putExtra("newYear", date.get(Calendar.YEAR));
            intent.putExtra("newHour", time.get(Calendar.HOUR_OF_DAY));
            intent.putExtra("newMinute", time.get(Calendar.MINUTE));
            intent.putExtra("newIsAm", time.get(Calendar.AM_PM));
            intent.putExtra("newDescription", etTaskDescription.getText().toString());
            intent.putExtra("deletingOld", deletingOld);
            intent.putExtra("oldName", oldName);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            intent = new Intent();
            intent.putExtra("addingNew", addingNew);
            intent.putExtra("oldName", oldName);
            intent.putExtra("deletingOld", deletingOld);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        taskPresenter.onBackPressed();
    }
}
