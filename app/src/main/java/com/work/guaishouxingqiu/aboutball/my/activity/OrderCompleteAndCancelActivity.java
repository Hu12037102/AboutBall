package com.work.guaishouxingqiu.aboutball.my.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderCompleteAndCancelContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.OrderCompleteAndCancelPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 18:15
 * 更新时间: 2019/5/14 18:15
 * 描述:订单完成和取消activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ORDER_COMPLETE_AND_CANCEL)
public class OrderCompleteAndCancelActivity extends BaseActivity<OrderCompleteAndCancelPresenter> implements
        OrderCompleteAndCancelContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_complete_and_cancel;
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
    protected OrderCompleteAndCancelPresenter createPresenter() {
        return new OrderCompleteAndCancelPresenter(this);
    }

    @Override
    public void resultOrderDetails(ResultOrderDetailsBean bean) {

    }

    @Override
    public void resultBandPhoneNumber() {

    }
}
