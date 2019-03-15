package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.home.contract.RecommendedContract;
import com.work.guaishouxingqiu.aboutball.home.model.RecommendedModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;

import java.util.List;

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
