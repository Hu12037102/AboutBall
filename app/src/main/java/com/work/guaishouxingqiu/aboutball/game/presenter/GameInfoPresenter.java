package com.work.guaishouxingqiu.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameFiltrateBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameGroupBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameInfoOtherBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameInfoScoreboardBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameInfoContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameInfoModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/25 11:32
 * 更新时间: 2019/6/25 11:32
 * 描述:比赛数据P
 */
public class GameInfoPresenter extends BasePresenter<GameInfoContract.View, GameInfoModel>
        implements GameInfoContract.Presenter {
    public GameInfoPresenter(@NonNull GameInfoContract.View view) {
        super(view);
    }

    @Override
    protected GameInfoModel createModel() {
        return new GameInfoModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMatchFiltrateData() {
        mModel.loadMatchFiltrateData(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultGameFiltrateBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameFiltrateBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMatchFiltrateData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadMatchGroupData(long gameId) {
        mModel.loadMatchGroupData(gameId, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultGameGroupBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameGroupBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMatchGroupData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadMatchScoreboardData(long requestGameId, Long requestGroupId) {
        mModel.loadMatchScoreboardData(requestGameId, requestGroupId, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultGameInfoScoreboardBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameInfoScoreboardBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMatchScoreboardDat(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadOtherData(long requestGameId, int requestAction) {
        mModel.loadOtherData(requestGameId, requestAction, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultGameInfoOtherBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameInfoOtherBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMatchOtherData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
