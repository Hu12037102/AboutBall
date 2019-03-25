package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCommentContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameCommentModel;
import com.work.guaishouxingqiu.aboutball.game.model.GameModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:49
 * 更新时间: 2019/3/25 9:49
 * 描述:比赛-评论P
 */
public class GameCommentPresenter extends BasePresenter<GameCommentContract.View,GameCommentModel>
implements GameCommentContract.Presenter{
    public GameCommentPresenter(@NonNull GameCommentContract.View view) {
        super(view);
    }

    @Override
    protected GameCommentModel createModel() {
        return new GameCommentModel();
    }

    @Override
    public void start() {

    }
}
