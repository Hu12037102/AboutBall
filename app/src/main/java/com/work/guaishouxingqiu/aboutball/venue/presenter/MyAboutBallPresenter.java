package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.venue.contract.MyAboutBallContract;
import com.work.guaishouxingqiu.aboutball.venue.model.MyAboutBallModel;

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
