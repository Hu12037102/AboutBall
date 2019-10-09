package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.home.contract.DrillContract;
import com.ifeell.app.aboutball.home.model.DrillModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/10 15:02
 * 更新时间: 2019/5/10 15:02
 * 描述:训练P
 */
public class DrillPresenter extends HomeBasePresenter<DrillContract.View,DrillModel>
    implements DrillContract.Presenter{
    public DrillPresenter(@NonNull DrillContract.View view) {
        super(view);
    }

    @Override
    protected DrillModel createModel() {
        return new DrillModel();
    }

    @Override
    public void start() {

    }
}
