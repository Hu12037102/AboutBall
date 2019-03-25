package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameDetailsModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 15:02
 * 更新时间: 2019/3/22 15:02
 * 描述:比赛详情P
 */
public class GameDetailsPresenter extends BasePresenter<GameDetailsContract.View, GameDetailsModel> implements
        GameDetailsContract.Presenter {


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

    @Override
    public void loadGameSimple(int matchId) {
        mModel.loadMatchSimple(matchId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultGameSimpleBean>() {
            @Override
            public void onNext(BaseBean<ResultGameSimpleBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultGameSimple(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}