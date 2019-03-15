package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameBaseContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameBaseModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:11
 * 更新时间: 2019/3/15 10:11
 * 描述:
 */
public abstract class GameBasePresenter<V extends GameBaseContract.View, M extends GameBaseModel> extends BasePresenter<V, M> implements
        GameBaseContract.Presenter {


    public GameBasePresenter(@NonNull V view) {
        super(view);
    }

    @Override
    public void loadGameData(int type) {

        mModel.loadGameData(type, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultGameBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameBean>> baseBean) {
                if (mView != null) {
                    mView.resultGameData(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadGameRefreshOrMoreData(int gameType, @NonNull String date) {
        if (isRefresh) {
            mModel.loadGameRefreshData(gameType, date, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultGameBean>>() {
                @Override
                public void onNext(BaseBean<List<ResultGameBean>> bean) {
                    if (mView != null) {
                        mView.resultGameRefreshOrMoreData(bean);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
        } else {
            mModel.loadGameMoreData(gameType, date, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultGameBean>>() {
                @Override
                public void onNext(BaseBean<List<ResultGameBean>> bean) {
                    if (mView != null) {
                        mView.resultGameRefreshOrMoreData(bean);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
        }
    }


}
