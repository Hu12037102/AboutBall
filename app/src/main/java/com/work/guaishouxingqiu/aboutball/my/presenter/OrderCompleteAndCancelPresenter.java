package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.my.contract.OrderCompleteAndCancelContract;
import com.work.guaishouxingqiu.aboutball.my.model.OrderCompleteAndCancelModel;
import com.work.guaishouxingqiu.aboutball.venue.presenter.BaseOrderPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 18:11
 * 更新时间: 2019/5/14 18:11
 * 描述:订单完成和取消P
 */
public class OrderCompleteAndCancelPresenter extends BaseOrderPresenter<OrderCompleteAndCancelContract.View,
        OrderCompleteAndCancelModel>implements OrderCompleteAndCancelContract.Presenter{
    public OrderCompleteAndCancelPresenter(@NonNull OrderCompleteAndCancelContract.View view) {
        super(view);
    }

    @Override
    protected OrderCompleteAndCancelModel createModel() {
        return new OrderCompleteAndCancelModel();
    }

    @Override
    public void start() {

    }
}
