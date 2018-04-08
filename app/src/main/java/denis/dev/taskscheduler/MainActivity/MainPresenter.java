package denis.dev.taskscheduler.MainActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.arellomobile.mvp.MvpPresenter;

import denis.dev.taskscheduler.Common.Task;
import denis.dev.taskscheduler.R;

public class MainPresenter extends MvpPresenter<MainView> {
    private static final String TAG = "MainPresenter";
    private MainView mView;
    private IModel mModel;

    public MainPresenter() {
    }

    public MainPresenter(MainView mView) {
        this.mView = mView;
    }

    void init(IModel model) {
        this.mModel = model;
        mModel.initModel();
    }

    public void appClosing() {
        mModel.closeRealm();
    }

    public void onItemClicked(View view, int position) {
        CheckBox chbDone = (CheckBox)view.findViewById(R.id.chbDone);
        if (chbDone.isChecked()) {
            Log.d(TAG, "onItemClicked: chbChecked pos : " + position);
            mView.setDoneItem(position);
        } else {

        }
    }

    public void onItemDone(Task item) {
        Log.d(TAG, "onItemDone: Deleting item");
        mModel.deleteItem(item.getName());
        mView.refreshListView();
    }
}
