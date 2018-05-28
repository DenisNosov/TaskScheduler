package denis.dev.taskscheduler.AddingActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.R;

public class AddingActivity extends MvpAppCompatActivity implements AddingView {
    @InjectPresenter
    AddingPresenter addingPresenter;

    @BindView(R.id.tvAddDate)
    TextView tvAddDate;

    @BindView(R.id.tvAddTime)
    TextView tvAddTime;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etDescription)
    EditText etDescription;

    @OnClick(R.id.btnAdd)
    void onAddClick() {
        addingPresenter.onAddClicked(etName.getText().toString(), etDescription.getText().toString());
    }

    @OnClick(R.id.tvAddTime)
	void onAddTimeClick() {
    	addingPresenter.onAddTimeClicked(this);
	}

	@OnClick(R.id.tvAddDate)
	void onAddDateClick() {
    	addingPresenter.onAddDateClicked(this);
	}

    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleTimeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        initView();
    }

    private void initView() {
		ButterKnife.bind(this);
		simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		simpleTimeFormat = new SimpleDateFormat("hh:mm a");
		Calendar today = Calendar.getInstance();
		addingPresenter.setNewDay(today.get(Calendar.DAY_OF_MONTH));
		addingPresenter.setNewMonth(today.get(Calendar.MONTH));
		addingPresenter.setNewYear(today.get(Calendar.YEAR));
		addingPresenter.setNewHour(today.get(Calendar.HOUR_OF_DAY));
		addingPresenter.setNewMinute(today.get(Calendar.MINUTE));

		tvAddDate.setText(today.get(Calendar.DAY_OF_MONTH) + "." + today.get(Calendar.MONTH) + "." + today.get(Calendar.YEAR));
		tvAddTime.setText(today.get(Calendar.HOUR_OF_DAY) + ":" + today.get(Calendar.MINUTE));
	}

	@Override
	public void setTvAddDate(String date) {
    	tvAddDate.setText(date);
	}

	@Override
	public void setTvAddTime(String time) {
    	tvAddTime.setText(time);
	}

    @Override
    public void onFinish(String name, int newDay, int newMonth, int newYear, int newHour, int newMinute, String description) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("day", newDay);
        intent.putExtra("month", newMonth);
        intent.putExtra("year", newYear);
        intent.putExtra("hour", newHour);
        intent.putExtra("minute", newMinute);
        intent.putExtra("description", description);
        setResult(RESULT_OK, intent);
        finish();
    }
}
