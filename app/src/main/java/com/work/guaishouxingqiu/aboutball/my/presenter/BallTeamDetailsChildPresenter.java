package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsChildContract;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.BallTeamDetailsChildModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 16:30
 * 更新时间: 2019/4/25 16:30
 * 描述:球队信息fragmentP
 */
public class BallTeamDetailsChildPresenter extends BasePresenter<BallTeamDetailsChildContract.View,
        BallTeamDetailsChildModel> implements BallTeamDetailsChildContract.Presenter {


    public BallTeamDetailsChildPresenter(@NonNull BallTeamDetailsChildContract.View view) {
        super(view);
    }

    @Override
    protected BallTeamDetailsChildModel createModel() {
        return new BallTeamDetailsChildModel();
    }

    @Override
    public void start() {

    }
}
