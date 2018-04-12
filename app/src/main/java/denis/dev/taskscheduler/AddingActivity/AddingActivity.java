package denis.dev.taskscheduler.AddingActivity;

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

    @BindView(R.id.dpDate)
    DatePicker dpDate;

    @BindView(R.id.tpTime)
    TimePicker tpTime;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etDescription)
    EditText etDescription;

    @OnClick(R.id.btnAdd)
    void onAddClick() {
        addingPresenter.onAddClicked(etName.getText().toString(), etDescription.getText().toString());
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
        addingPresenter.setDate(simpleDateFormat.format(today.getTime()));
        addingPresenter.setTime(simpleTimeFormat.format(today.getTime()));
        dpDate.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                addingPresenter.dateChanged(year, monthOfYear, dayOfMonth);
            }
        });
        tpTime.setHour(today.get(Calendar.HOUR_OF_DAY));
        tpTime.setMinute(today.get(Calendar.MINUTE));
        tpTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                addingPresenter.timeChanged(hourOfDay, minute);
            }
        });
    }

    @Override
    public void onFinish(String name, String newDate, String newTime, String description) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("date", newDate);
        intent.putExtra("time", newTime);
        intent.putExtra("description", description);
        setResult(RESULT_OK, intent);
        finish();
    }
}
