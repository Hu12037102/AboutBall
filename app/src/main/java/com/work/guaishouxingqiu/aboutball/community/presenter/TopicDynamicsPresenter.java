package com.work.guaishouxingqiu.aboutball.community.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.TopicDynamicsContract;
import com.work.guaishouxingqiu.aboutball.community.model.TopicDynamicsModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/20 13:45
 * 更新时间: 2019/6/20 13:45
 * 描述:话题动态P
 */
public class TopicDynamicsPresenter extends BasePresenter<TopicDynamicsContract.View, TopicDynamicsModel>
        implements TopicDynamicsContract.Presenter {
    public TopicDynamicsPresenter(@NonNull TopicDynamicsContract.View view) {
        super(view);
    }

    @Override
    protected TopicDynamicsModel createModel() {
        return new TopicDynamicsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadRecommendedTopic(long topicId) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadRecommendedTopic(topicId, mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultCommunityDataBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultCommunityDataBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultTopicData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadNewTopic(long topicId) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadNewTopic(topicId,  mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultCommunityDataBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultCommunityDataBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultTopicData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
