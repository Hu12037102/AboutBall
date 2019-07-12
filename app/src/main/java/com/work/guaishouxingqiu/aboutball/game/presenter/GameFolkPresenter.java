package com.work.guaishouxingqiu.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.game.contract.GameFolkContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameFolkModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:28
 * 更新时间: 2019/3/15 10:28
 * 描述:比赛-官方-P
 */
public class GameFolkPresenter extends GameBasePresenter<GameFolkContract.View,GameFolkModel>
implements GameFolkContract.Presenter{
    public GameFolkPresenter(@NonNull GameFolkContract.View view) {
        super(view);
    }

    @Override
    protected GameFolkModel createModel() {
        return new GameFolkModel();
    }

    @Override
    public void start() {

    }
}
