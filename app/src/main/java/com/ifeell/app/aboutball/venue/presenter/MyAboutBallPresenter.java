package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.venue.contract.MyAboutBallContract;
import com.ifeell.app.aboutball.venue.model.MyAboutBallModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 17:09
 * 更新时间: 2019/5/22 17:09
 * 描述:
 */
public class MyAboutBallPresenter extends BasePresenter<MyAboutBallContract.View,MyAboutBallModel>
implements MyAboutBallContract.Presenter{
    public MyAboutBallPresenter(@NonNull MyAboutBallContract.View view) {
        super(view);
    }

    @Override
    protected MyAboutBallModel createModel() {
        return new MyAboutBallModel();
    }

    @Override
    public void start() {

    }
}
