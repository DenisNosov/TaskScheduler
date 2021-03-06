package denis.dev.taskscheduler.Common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import denis.dev.taskscheduler.R;

public class TaskAdapter extends ArrayAdapter<Task> {
   // MainPresenter mPresenter = new MainPresenter();
    OnCheckBoxListener mListener;

    private static final String TAG = "SubjectsArrayAdapter";

    private Context mContext;
    private int mResource;
    private ArrayList<Task> mObjects;

    private SimpleDateFormat simpleDateFormat, simpleTimeFormat;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects, OnCheckBoxListener onCheckBoxListener) {
        super(context, resource, objects);
        mObjects = objects;
        mContext = context;
        mResource = resource;
        Log.d(TAG, "SubjectsArrayAdapter: created");
        mListener = onCheckBoxListener;
    }

	public void updateList(ArrayList<Task> items) {
    	mObjects.clear();
    	mObjects.addAll(items);
    	this.notifyDataSetChanged();
	}

	public interface OnCheckBoxListener {
        void onChbClicked(int position);
    }

	@Override
	public int getCount() {
		return mObjects.size();
	}

	@Nullable
	@Override
	public Task getItem(int position) {
		return mObjects.get(position);
	}

	@NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        simpleTimeFormat = new SimpleDateFormat("hh:mm a");
        String name = getItem(position).getName();
        String date = simpleDateFormat.format(getItem(position).getDate());
        String time = simpleTimeFormat.format(getItem(position).getTime());
        String description = getItem(position).getDescription();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
        TextView tvDate = (TextView)convertView.findViewById(R.id.tvDate);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);
        TextView tvDescription = (TextView)convertView.findViewById(R.id.tvDescription);

        tvName.setText(name);
        tvDate.setText(date);
        tvTime.setText(time);
        tvDescription.setText(description);

        Log.d(TAG, "getView: Everything set");

        CheckBox chbDone = (CheckBox)convertView.findViewById(R.id.chbDone);
        chbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: gone to listener");
                mListener.onChbClicked(position);
            }
        });

        return convertView;
    }
}