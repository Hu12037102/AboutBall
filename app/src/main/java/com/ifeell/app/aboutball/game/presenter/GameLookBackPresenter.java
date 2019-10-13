package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.game.bean.ResultGameLiveDetailsBean;
import com.ifeell.app.aboutball.game.contract.GameLookBackContract;
import com.ifeell.app.aboutball.game.model.GameLookBackModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 11:04
 * 更新时间: 2019/6/24 11:04
 * 描述:比赛-回顾- P
 */
public class GameLookBackPresenter extends BasePresenter<GameLookBackContract.View, GameLookBackModel>
        implements GameLookBackContract.Presenter {
    public GameLookBackPresenter(@NonNull GameLookBackContract.View view) {
        super(view);
    }

    @Override
    protected GameLookBackModel createModel() {
        return new GameLookBackModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadLiveDetails(int matchId) {
        mModel.loadLiveDetails(matchId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultGameLiveDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultGameLiveDetailsBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultLiveDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
