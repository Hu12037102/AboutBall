package com.work.guaishouxingqiu.aboutball.home.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.home.contract.TicketMallContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.TicketMallPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/27 11:22
 * 更新时间: 2019/8/27 11:22
 * 描述:售票商城Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_TICKET_MALL)
public class TicketMallActivity extends BaseActivity<TicketMallPresenter> implements TicketMallContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket_mall;
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
    protected TicketMallPresenter createPresenter() {
        return new TicketMallPresenter(this);
    }
}
