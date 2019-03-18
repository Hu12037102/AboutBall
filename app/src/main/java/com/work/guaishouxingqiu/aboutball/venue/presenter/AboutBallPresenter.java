package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.AboutBallContract;
import com.work.guaishouxingqiu.aboutball.venue.model.AboutBallModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:50
 * 更新时间: 2019/3/18 10:50
 * 描述:场馆-约球P
 */
public class AboutBallPresenter extends BasePresenter<AboutBallContract.View, AboutBallModel>
        implements AboutBallContract.Presenter {
    public AboutBallPresenter(@NonNull AboutBallContract.View view) {
        super(view);
    }

    @Override
    protected AboutBallModel createModel() {
        return new AboutBallModel();
    }

    @Override
    public void start() {
        if (isRefresh) {
            mPageNum = 1;
        }
        mModel.getAboutList(mPageNum, mPageSize, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultAboutBallBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultAboutBallBean>> bean) {
                if (DataUtils.isResultSure(bean)) {
                    mPageNum++;
                    mView.resultAboutBallData(bean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
