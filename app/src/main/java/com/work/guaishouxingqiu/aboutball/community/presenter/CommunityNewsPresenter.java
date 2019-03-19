package com.work.guaishouxingqiu.aboutball.community.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityNewsContract;
import com.work.guaishouxingqiu.aboutball.community.model.CommunityNewsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 14:04
 * 更新时间: 2019/3/19 14:04
 * 描述:社区-最新P
 */
public class CommunityNewsPresenter extends BasePresenter<CommunityNewsContract.View,CommunityNewsModel>
implements CommunityNewsContract.Presenter{
    public CommunityNewsPresenter(@NonNull CommunityNewsContract.View view) {
        super(view);
    }

    @Override
    protected CommunityNewsModel createModel() {
        return new CommunityNewsModel();
    }

    @Override
    public void start() {

    }
}
