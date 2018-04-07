package denis.dev.taskscheduler.Common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import denis.dev.taskscheduler.R;

public class TaskAdapter extends ArrayAdapter<Task> {

    private static final String TAG = "SubjectsArrayAdapter";

    private Context mContext;
    private int mResource;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        Log.d(TAG, "SubjectsArrayAdapter: created");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String date = getItem(position).getDate().toString();
        String time = getItem(position).getTime().toString();
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

        return convertView;
    }
}
