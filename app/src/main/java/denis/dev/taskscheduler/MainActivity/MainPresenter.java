package denis.dev.taskscheduler.MainActivity;

import android.content.Context;

import com.arellomobile.mvp.MvpPresenter;

public class MainPresenter extends MvpPresenter<MainView> {
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
}
