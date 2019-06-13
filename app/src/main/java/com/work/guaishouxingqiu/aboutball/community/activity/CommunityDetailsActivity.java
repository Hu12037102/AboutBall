package com.work.guaishouxingqiu.aboutball.community.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityDetailsContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 18:32
 * 更新时间: 2019/6/13 18:32
 * 描述:动态详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_COMMUNITY_DETAILS)
public class CommunityDetailsActivity extends BaseActivity<CommunityDetailsPresenter> implements
        CommunityDetailsContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_details;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected CommunityDetailsPresenter createPresenter() {
        return null;
    }
}
