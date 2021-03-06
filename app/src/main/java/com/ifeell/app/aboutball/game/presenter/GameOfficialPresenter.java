package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.game.contract.GameOfficialContract;
import com.ifeell.app.aboutball.game.model.GameOfficialModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 9:22
 * 更新时间: 2019/3/15 9:22
 * 描述: 官方比赛Presenter
 */
public class GameOfficialPresenter extends GameBasePresenter<GameOfficialContract.View,GameOfficialModel>
implements GameOfficialContract.Presenter{
    public GameOfficialPresenter(@NonNull GameOfficialContract.View view) {
        super(view);
    }

    @Override
    protected GameOfficialModel createModel() {
        return new GameOfficialModel();
    }

    @Override
    public void start() {

    }
}
