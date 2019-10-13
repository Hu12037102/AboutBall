package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.bean.RequestLauncherBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;
import com.ifeell.app.aboutball.venue.bean.ResultRefereeBean;
import com.ifeell.app.aboutball.venue.contract.LauncherBallContract;
import com.ifeell.app.aboutball.venue.model.LauncherBallModel;

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
                if (DataUtils.isResultSure(t)) {
                    mView.resultRefereeList(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void launcherBall(RequestLauncherBallBean requestBean) {
        mModel.launcherBall(requestBean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<Long>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<Long>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t) && t.result.result != null) {
                    mView.launcherBallSucceed(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void createOrder(RequestVenueOrderBean bean) {
        mModel.createOrder(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<Long>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<Long>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null &&
                        t.result.code == IApi.Code.SUCCEED) {
                    mView.resultOrderId(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
