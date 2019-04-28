package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.LauncherBallContract;
import com.work.guaishouxingqiu.aboutball.venue.model.LauncherBallModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 14:19
 * 更新时间: 2019/4/23 14:19
 * 描述:发起约球Presenter
 */
public class LauncherBallPresenter extends BasePresenter<LauncherBallContract.View, LauncherBallModel>
        implements LauncherBallContract.Presenter {
    public LauncherBallPresenter(@NonNull LauncherBallContract.View view) {
        super(view);
    }

    @Override
    protected LauncherBallModel createModel() {
        return new LauncherBallModel();
    }

    @Override
    public void start() {
        mModel.loadRefereeList(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultRefereeBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultRefereeBean>> t) {
                if (DataUtils.isResultSure(t)){
                    mView.resultRefereeList(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}