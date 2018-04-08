package denis.dev.taskscheduler.MainActivity;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.R;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private static final String TAG = "MainPresenter";
    private IModel mModel;

    public MainPresenter() {
    }

    public MainPresenter(MainView mView) {
    }

    void init(IModel model) {
        this.mModel = model;
        mModel.initModel();
    }

    public void appClosing() {
        mModel.closeRealm();
    }

    public void onItemClicked(View view, @NonNull int position) {
        CheckBox chbDone = (CheckBox)view.findViewById(R.id.chbDone);
        if (chbDone.isChecked()) {
            Log.d(TAG, "onItemClicked: chbChecked pos : " + position);
            try {
                Log.d(TAG, "onItemClicked: position = " + position);
                getViewState().setDoneItem(position);
            } catch (NullPointerException e) {
                Log.d(TAG, "onItemClicked: setdoneitem doesn't work");
                Log.d(TAG, String.valueOf(e));
            }
            
        } else {
            //Todo third activity after adding
        }
    }

    public void onItemDone(Task item) {
        Log.d(TAG, "onItemDone: Deleting item");
        mModel.deleteItem(item.getName());
        mModel.rofl();
        getViewState().refreshListView();
    }

    public void logRealm() {
        mModel.logRealm();
    }
}
