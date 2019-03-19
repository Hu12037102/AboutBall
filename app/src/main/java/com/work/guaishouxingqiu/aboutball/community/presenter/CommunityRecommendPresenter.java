package com.work.guaishouxingqiu.aboutball.community.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityRecommendContract;
import com.work.guaishouxingqiu.aboutball.community.model.CommunityRecommendModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:56
 * 更新时间: 2019/3/19 13:56
 * 描述:社区-推荐P
 */
public class CommunityRecommendPresenter extends BasePresenter<CommunityRecommendContract.View,
        CommunityRecommendModel>implements CommunityRecommendContract.Presenter{
    public CommunityRecommendPresenter(@NonNull CommunityRecommendContract.View view) {
        super(view);
    }

    @Override
    protected CommunityRecommendModel createModel() {
        return new CommunityRecommendModel();
    }

    @Override
    public void start() {

    }
}
