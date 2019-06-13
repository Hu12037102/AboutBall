package com.work.guaishouxingqiu.aboutball.community.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityDetailsContract;
import com.work.guaishouxingqiu.aboutball.community.model.CommunityDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 18:34
 * 更新时间: 2019/6/13 18:34
 * 描述:动态详情P
 */
public class CommunityDetailsPresenter extends BasePresenter<CommunityDetailsContract.View,
        CommunityDetailsModel>implements CommunityDetailsContract.Presenter{
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
}
