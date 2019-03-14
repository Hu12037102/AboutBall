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
public class RecommendedPresenter extends BasePresenter<RecommendedContract.View, RecommendedModel>
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

    @Override
    public void loadNewsData() {
        if (isRefresh) {
            pageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadData(pageNum, pageSize, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultNewsBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultNewsBean>> bean) {
                if (bean.code == IApi.Code.SUCCEED) {
                    pageNum++;
                    if (mView != null) {
                        mView.resultNewsData(bean);
                    }

                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
