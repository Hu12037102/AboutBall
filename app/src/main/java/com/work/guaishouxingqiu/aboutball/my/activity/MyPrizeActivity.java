package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.MyPrizeContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyPrizePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 18:27
 * 更新时间: 2019/4/8 18:27
 * 描述:我的奖品Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_PRIZE)
public class MyPrizeActivity extends BaseActivity<MyPrizePresenter> implements MyPrizeContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_prize;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String[] awardArray = getResources().getStringArray(R.array.award_status_array);
        for (int i = 0; i < awardArray.length; i++) {
            mTabTitle.addTab(mTabTitle.newTab().setText(awardArray[i]), i == 0);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected MyPrizePresenter createPresenter() {
        return new MyPrizePresenter(this);
    }


}
