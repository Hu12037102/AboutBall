package com.work.guaishouxingqiu.aboutball.my.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.contract.MyTicketsChildContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyTicketsChildPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:38
 * 更新时间: 2019/9/4 17:38
 * 描述:我的门票fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY_TICKETS)
public class MyTicketsFragment extends DelayedFragment<MyTicketsChildPresenter> implements MyTicketsChildContract.View {
    @Override
    protected void initDelayedView() {

    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_tickets_child;
    }

    @Override
    protected MyTicketsChildPresenter createPresenter() {
        return new MyTicketsChildPresenter(this);
    }
}
