package com.work.guaishouxingqiu.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.AboutBallDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.model.AboutBallDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 15:27
 * 更新时间: 2019/5/16 15:27
 * 描述:约球详情契约
 */
public class AboutBallDetailsPresenter extends LoginOrSharePresenter<AboutBallDetailsContract.View,
        AboutBallDetailsModel> implements AboutBallDetailsContract.Presenter {
    public AboutBallDetailsPresenter(@NonNull AboutBallDetailsContract.View view) {
        super(view);
    }

    @Override
    protected AboutBallDetailsModel createModel() {
        return new AboutBallDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadDetails(long offerId) {
        mModel.loadDetails(offerId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultAboutBallDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultAboutBallDetailsBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void playReferee(long offerId) {
        mModel.playReferee(offerId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultPlayReferee();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void cancelAboutBall(long agreeId) {
        mModel.cancelAboutBall(agreeId, new BaseObserver<>(true, this, new BaseObserver.Observer<String>() {
            @Override
            public void onNext(BaseBean<String> t) {
                if (DataUtils.isResultSureResultNull(t)) {
                    mView.resultCancelAboutBall(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
