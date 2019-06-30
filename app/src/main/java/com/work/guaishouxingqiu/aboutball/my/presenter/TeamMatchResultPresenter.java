package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.TeamMatchResultContract;
import com.work.guaishouxingqiu.aboutball.my.model.TeamMatchResultModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/30
 * 更新时间: 2019/6/30
 * 描述:赛况P
 */
public class TeamMatchResultPresenter extends BasePresenter<TeamMatchResultContract.View, TeamMatchResultModel>
implements TeamMatchResultContract.Presenter{
    public TeamMatchResultPresenter(@NonNull TeamMatchResultContract.View view) {
        super(view);
    }

    @Override
    protected TeamMatchResultModel createModel() {
        return new TeamMatchResultModel();
    }

    @Override
    public void start() {

    }
}
