package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCollectionContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCommentContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameCollectionModel;
import com.work.guaishouxingqiu.aboutball.game.model.GameCommentModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 10:14
 * 更新时间: 2019/3/25 10:14
 * 描述:比赛-集锦P
 */
public class GameCollectionPresenter extends BasePresenter<GameCollectionContract.View,
        GameCollectionModel>implements GameCollectionContract.Presenter {
    public GameCollectionPresenter(@NonNull GameCollectionContract.View view) {
        super(view);
    }

    @Override
    protected GameCollectionModel createModel() {
        return new GameCollectionModel();
    }

    @Override
    public void start() {

    }
}
