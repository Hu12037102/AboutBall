package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDataContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameDataModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:41
 * 更新时间: 2019/3/25 9:41
 * 描述:比赛数据P
 */
public class GameDataPresenter extends BasePresenter<GameDataContract.View,GameDataModel>
implements GameDataContract.Presenter{
    public GameDataPresenter(@NonNull GameDataContract.View view) {
        super(view);
    }

    @Override
    protected GameDataModel createModel() {
        return new GameDataModel();
    }

    @Override
    public void start() {

    }
}
