package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.model.NewsDetailsModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 15:02
 * 更新时间: 2019/3/21 15:02
 * 描述:首页-资讯详情P
 */
public class NewDetailsPresenter extends BasePresenter<NewsDetailsContract.View,
        NewsDetailsModel> implements NewsDetailsContract.Presenter {
    public NewDetailsPresenter(@NonNull NewsDetailsContract.View view) {
        super(view);
    }

    @Override
    protected NewsDetailsModel createModel() {
        return new NewsDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadNewsContent(long newsId) {
        mModel.loadNewData(newsId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {


            @Override
            public void onNext(BaseBean<BaseDataBean<String>> baseBean) {
                if (baseBean.code == IApi.Code.SUCCEED && baseBean.result != null) {
                    mView.resultNewsContent(baseBean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadMessage(long newsId) {
        if (isRefresh) {
            mPageNum = 0;
        }
        mModel.loadMessageData(mPageNum, mPageSize, newsId, new BaseObserver<>(true,
                this, new BaseObserver.Observer<List<ResultNewsMessageBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultNewsMessageBean>> bean) {
                if (bean.code == IApi.Code.SUCCEED) {
                    mPageNum++;
                    mView.resultMessageData(bean.result);
                }

            }

            @Override
            public void onError(Throwable e) {

            }
        }));

    }

    @Override
    public void sendNewsMessage(RequestSendMessageBean bean) {
        mModel.sendNewsMessage(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> bean) {
                if (bean.code == IApi.Code.SUCCEED) {
                    mView.resultSendNewsMessage();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
