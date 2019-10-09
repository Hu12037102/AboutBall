package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.home.activity.MainActivity;
import com.ifeell.app.aboutball.home.contract.MainContract;
import com.ifeell.app.aboutball.home.model.MainModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 14:14
 * 更新时间: 2019/3/4 14:14
 * 描述:
 */
public class MainPresenter extends BasePresenter<MainContract.View,MainModel> implements MainContract.Presenter{
    @Override
    protected MainModel createModel() {
        return new MainModel();
    }

    public MainPresenter(@NonNull MainContract.View view) {
        super(view);
    }

    @Override
    public void start() {

    }




    @Override
    public void loadMainTab() {
        if (mView != null && mView instanceof MainActivity && mModel != null) {
            String[] tabNameArray = ((MainActivity) mView).getResources().getStringArray(R.array.main_tab_array);
            mModel.loadMainTab(tabNameArray, data -> {
                if (mView != null) {
                    mView.loadMainTabResult(data);
                }
            });
        }
    }
}
