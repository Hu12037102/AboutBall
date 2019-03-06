package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.GameContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:10
 * 更新时间: 2019/3/6 13:10
 * 描述: 比赛P
 */
public class GamePresenter extends BasePresenter<GameContract.View,GameModel>{
    public GamePresenter(@NonNull GameContract.View view) {
        super(view);
    }

    @Override
    protected GameModel createModel() {
        return new GameModel();
    }

    @Override
    public void start() {

    }
}
