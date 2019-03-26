package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDetailsBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDataContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameDataModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:41
 * 更新时间: 2019/3/25 9:41
 * 描述:比赛数据P
 */
public class GameDataPresenter extends BasePresenter<GameDataContract.View, GameDataModel>
        implements GameDataContract.Presenter {
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

    @Override
    public void loadGameDetails(int gameId) {
        mModel.loadGameDetails(gameId, new BaseObserver<>(this, new BaseObserver.Observer<ResultGameDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultGameDetailsBean> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultGameDetails(t.result.matchData);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
