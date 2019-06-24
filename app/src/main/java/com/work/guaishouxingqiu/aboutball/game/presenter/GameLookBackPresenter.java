package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.GameLookBackContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameLookBackModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 11:04
 * 更新时间: 2019/6/24 11:04
 * 描述:比赛-回顾- P
 */
public class GameLookBackPresenter extends BasePresenter<GameLookBackContract.View,GameLookBackModel>
implements GameLookBackContract.Presenter{
    public GameLookBackPresenter(@NonNull GameLookBackContract.View view) {
        super(view);
    }

    @Override
    protected GameLookBackModel createModel() {
        return new GameLookBackModel();
    }

    @Override
    public void start() {

    }
}
