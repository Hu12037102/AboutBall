package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.BallTeamDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 15:44
 * 更新时间: 2019/4/25 15:44
 * 描述:球队信息Presenter
 */
public class BallTeamDetailsPresenter extends BasePresenter<BallTeamDetailsContract.View,
        BallTeamDetailsModel> implements BallTeamDetailsContract.Presenter{
    public BallTeamDetailsPresenter(@NonNull BallTeamDetailsContract.View view) {
        super(view);
    }

    @Override
    protected BallTeamDetailsModel createModel() {
        return new BallTeamDetailsModel();
    }

    @Override
    public void start() {

    }
}
