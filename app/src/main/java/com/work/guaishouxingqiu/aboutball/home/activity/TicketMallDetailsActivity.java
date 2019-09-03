package com.work.guaishouxingqiu.aboutball.home.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.home.contract.TicketMallDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.TicketMallDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 11:08
 * 更新时间: 2019/9/3 11:08
 * 描述:购票详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_TICKET_MALL_DETAILS)
public class TicketMallDetailsActivity extends BaseActivity<TicketMallDetailsPresenter> implements TicketMallDetailsContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket_mall_details;
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
    protected TicketMallDetailsPresenter createPresenter() {
        return new TicketMallDetailsPresenter(this);
    }
}
