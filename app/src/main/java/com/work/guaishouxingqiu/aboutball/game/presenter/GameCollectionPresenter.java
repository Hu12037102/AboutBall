package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCollectionBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCollectionContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCommentContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameCollectionModel;
import com.work.guaishouxingqiu.aboutball.game.model.GameCommentModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 10:14
 * 更新时间: 2019/3/25 10:14
 * 描述:比赛-集锦P
 */
public class GameCollectionPresenter extends BasePresenter<GameCollectionContract.View,
        GameCollectionModel> implements GameCollectionContract.Presenter {
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

    @Override
    public void loadData(int gameId) {
        mModel.loadData(gameId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultGameCollectionBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameCollectionBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
