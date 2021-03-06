package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.game.contract.GameContract;
import com.ifeell.app.aboutball.game.model.GameModel;

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
