package com.work.guaishouxingqiu.aboutball.home.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.home.contract.ScheduleContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.SchedulePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/11 9:35
 * 更新时间: 2019/6/11 9:35
 * 描述:赛程Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SCHEDULE)
public class ScheduleActivity extends BaseActivity<SchedulePresenter> implements ScheduleContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
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
    protected SchedulePresenter createPresenter() {
        return new SchedulePresenter(this);
    }


}
