package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataInfoBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDataContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameDataModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

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
    public void loadGameHeadDetails(int gameId) {
        mModel.loadGameHeadDetails(gameId, new BaseObserver<>(this, new BaseObserver.Observer<ResultGameDataInfoBean>() {
            @Override
            public void onNext(BaseBean<ResultGameDataInfoBean> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultHeadGameDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadGameResultDetails(int gameId) {
        mModel.loadGameResultDetails(gameId, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultGameDataResultBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameDataResultBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    if (t.result.size() > 0) {
                        t.result.add(0,new ResultGameDataResultBean(true));
                    }
                    mView.resultGameResultDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
