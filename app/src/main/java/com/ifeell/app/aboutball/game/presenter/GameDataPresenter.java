package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.game.bean.ResultGameDataInfoBean;
import com.ifeell.app.aboutball.game.contract.GameDataContract;
import com.ifeell.app.aboutball.game.model.GameDataModel;

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
                mView.resultHeadGameDetails(t.result);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }


}
