package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.game.bean.ResultGameSimpleBean;
import com.ifeell.app.aboutball.game.contract.GameDetailsContract;
import com.ifeell.app.aboutball.game.model.GameDetailsModel;
import com.ifeell.app.aboutball.http.IApi;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 15:02
 * 更新时间: 2019/3/22 15:02
 * 描述:比赛详情P
 */
public class GameDetailsPresenter extends LoginOrSharePresenter<GameDetailsContract.View, GameDetailsModel> implements
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
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultGameSimple(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
