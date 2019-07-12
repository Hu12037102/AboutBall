package com.work.guaishouxingqiu.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityContract;
import com.work.guaishouxingqiu.aboutball.community.model.CommunityModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:42
 * 更新时间: 2019/3/6 15:42
 * 描述:社区P
 */
public class CommunityPresenter extends BasePresenter<CommunityContract.View,CommunityModel> implements CommunityContract.Presenter{


    public CommunityPresenter(@NonNull CommunityContract.View view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    protected CommunityModel createModel() {
        return null;
    }
}
