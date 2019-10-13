package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.contract.MyRefereeRecordContract;
import com.ifeell.app.aboutball.my.model.MyRefereeRecordModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 14:52
 * 更新时间: 2019/5/7 14:52
 * 描述:我的裁判记录P
 */
public class MyRefereeRecordPresenter extends BasePresenter<MyRefereeRecordContract.View, MyRefereeRecordModel>
        implements MyRefereeRecordContract.Presenter {
    public MyRefereeRecordPresenter(@NonNull MyRefereeRecordContract.View view) {
        super(view);
    }

    @Override
    protected MyRefereeRecordModel createModel() {
        return new MyRefereeRecordModel();
    }

    @Override
    public void start() {

    }
}
