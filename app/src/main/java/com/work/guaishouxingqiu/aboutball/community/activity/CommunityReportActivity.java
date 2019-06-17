package com.work.guaishouxingqiu.aboutball.community.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityReportContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityReportPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 11:42
 * 更新时间: 2019/6/17 11:42
 * 描述:动态举报Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_COMMUNITY_REPORT)
public class CommunityReportActivity extends BaseActivity<CommunityReportPresenter> implements CommunityReportContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_report;
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
    protected CommunityReportPresenter createPresenter() {
        return new CommunityReportPresenter(this);
    }
}
