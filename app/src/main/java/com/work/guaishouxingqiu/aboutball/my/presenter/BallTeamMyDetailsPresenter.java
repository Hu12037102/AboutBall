package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.BallTeamMyDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 15:29
 * 更新时间: 2019/4/28 15:29
 * 描述:对内个人信息P
 */
public class BallTeamMyDetailsPresenter extends BasePresenter<BallTeamMyDetailsContract.View,
        BallTeamMyDetailsModel> implements BallTeamMyDetailsContract.Presenter {
    public BallTeamMyDetailsPresenter(@NonNull BallTeamMyDetailsContract.View view) {
        super(view);
    }

    @Override
    protected BallTeamMyDetailsModel createModel() {
        return new BallTeamMyDetailsModel();
    }

    @Override
    public void start() {

    }
}
