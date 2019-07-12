package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.BallTeamDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 15:44
 * 更新时间: 2019/4/25 15:44
 * 描述:球队信息Presenter
 */
public class BallTeamDetailsPresenter extends BasePresenter<BallTeamDetailsContract.View,
        BallTeamDetailsModel> implements BallTeamDetailsContract.Presenter {
    public BallTeamDetailsPresenter(@NonNull BallTeamDetailsContract.View view) {
        super(view);
    }

    @Override
    protected BallTeamDetailsModel createModel() {
        return new BallTeamDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void exitBallTeam(Long teamId, Long playerId) {
        mModel.exitBallTeam(teamId, playerId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null && t.result.code == IApi.Code.SUCCEED) {
                    mView.resultBallTeamSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void dissolutionBallTeam(long teamId) {
        mModel.dissolutionBallTeam(teamId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null && t.result.code == IApi.Code.SUCCEED) {
                    mView.resultBallTeamSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
