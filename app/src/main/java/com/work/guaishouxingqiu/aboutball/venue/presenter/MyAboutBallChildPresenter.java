package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.venue.contract.MyAboutBallChildContract;
import com.work.guaishouxingqiu.aboutball.venue.model.MyAboutBallChildModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 18:02
 * 更新时间: 2019/5/22 18:02
 * 描述:
 */
public class MyAboutBallChildPresenter extends BasePresenter<MyAboutBallChildContract.View, MyAboutBallChildModel>
        implements MyAboutBallChildContract.Presenter {
    public MyAboutBallChildPresenter(@NonNull MyAboutBallChildContract.View view) {
        super(view);
    }

    @Override
    protected MyAboutBallChildModel createModel() {
        return null;
    }

    @Override
    public void start() {

    }
}
