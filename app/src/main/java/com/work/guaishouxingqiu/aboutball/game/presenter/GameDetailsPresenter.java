package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.GameContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 15:02
 * 更新时间: 2019/3/22 15:02
 * 描述:
 */
public class GameDetailsPresenter extends BasePresenter<GameDetailsContract.View,GameDetailsModel>implements
        GameDetailsContract.Presenter{


    public GameDetailsPresenter(@NonNull GameDetailsContract.View view) {
        super(view);
    }

    @Override
    protected GameDetailsModel createModel() {
        return new GameDetailsModel();
    }

    @Override
    public void start() {

    }
}
