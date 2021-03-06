package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.game.contract.GameTeachContract;
import com.ifeell.app.aboutball.game.model.GameTeachModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:41
 * 更新时间: 2019/3/15 10:41
 * 描述:比赛-教学-P
 */
public class GameTeachPresenter extends BasePresenter<GameTeachContract.View, GameTeachModel>
        implements GameTeachContract.Presenter {
    public GameTeachPresenter(@NonNull GameTeachContract.View view) {
        super(view);
    }

    @Override
    protected GameTeachModel createModel() {
        return new GameTeachModel();
    }

    @Override
    public void start() {

    }
}
