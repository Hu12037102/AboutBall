package com.work.guaishouxingqiu.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.venue.contract.CreateBallContract;
import com.work.guaishouxingqiu.aboutball.venue.model.CreateBallModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/5 14:19
 * 更新时间: 2019/8/5 14:19
 * 描述:创建约球P
 */
public class CreateBallPresenter extends BasePresenter<CreateBallContract.View, CreateBallModel> implements
        CreateBallContract.Presenter {
    public CreateBallPresenter(@NonNull CreateBallContract.View view) {
        super(view);
    }

    @Override
    protected CreateBallModel createModel() {
        return new CreateBallModel();
    }

    @Override
    public void start() {

    }
}
