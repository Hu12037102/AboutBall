package com.work.guaishouxingqiu.aboutball.community.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityDetailsContract;
import com.work.guaishouxingqiu.aboutball.community.model.CommunityDetailsModel;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 18:34
 * 更新时间: 2019/6/13 18:34
 * 描述:动态详情P
 */
public class CommunityDetailsPresenter extends BasePresenter<CommunityDetailsContract.View,
        CommunityDetailsModel> implements CommunityDetailsContract.Presenter {
    public CommunityDetailsPresenter(@NonNull CommunityDetailsContract.View view) {
        super(view);
    }

    @Override
    protected CommunityDetailsModel createModel() {
        return new CommunityDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadCommentData(long tweetId) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadCommentData(mPageNum, mPageSize, tweetId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultNewsMessageBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultNewsMessageBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultCommentData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
