package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.BallTeamDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.model.BallTeamDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/17 9:53
 * 更新时间: 2019/5/17 9:53
 * 描述:球队详情P
 */
public class BallTeamDetailsPresenter extends BasePresenter<BallTeamDetailsContract.View,BallTeamDetailsModel>implements BallTeamDetailsContract.Presenter{
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
    public void loadDetails(long mTeamId) {
        mModel.loadDetails(mTeamId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultBallDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultBallDetailsBean> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
