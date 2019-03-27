package com.work.guaishouxingqiu.aboutball.venue.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenueDetailsPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/27 13:15
 * 更新时间: 2019/3/27 13:15
 * 描述:场馆详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_VENUE_DETAILS)
public class VenueDetailsActivity extends BaseActivity<VenueDetailsPresenter> implements VenueDetailsContract.View{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_details;
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
    protected VenueDetailsPresenter createPresenter() {
        return new VenueDetailsPresenter(this);
    }
}
