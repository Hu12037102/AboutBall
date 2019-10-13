package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.home.bean.RequestRecommendDataBean;
import com.ifeell.app.aboutball.home.bean.ResultRecommendDataBean;
import com.ifeell.app.aboutball.home.contract.RecommendedContract;
import com.ifeell.app.aboutball.home.model.RecommendedModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:35
 * 更新时间: 2019/3/12 17:35
 * 描述:推荐P
 */
public class RecommendedPresenter extends HomeBasePresenter<RecommendedContract.View, RecommendedModel>
        implements RecommendedContract.Presenter {
    public RecommendedPresenter(@NonNull RecommendedContract.View view) {
        super(view);
    }

    @Override
    protected RecommendedModel createModel() {
        return new RecommendedModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadHead(RequestRecommendDataBean registerBean) {
        mModel.loadHead(registerBean, new BaseObserver<>(this, new BaseObserver.Observer<ResultRecommendDataBean>() {
            @Override
            public void onNext(BaseBean<ResultRecommendDataBean> t) {
                mView.resultBannerData(t);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }


}
