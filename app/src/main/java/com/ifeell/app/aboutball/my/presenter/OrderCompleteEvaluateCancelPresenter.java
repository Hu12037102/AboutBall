package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.my.contract.OrderCompleteEvaluateCancelContract;
import com.ifeell.app.aboutball.my.model.OrderCompleteEvaluateCancelModel;
import com.ifeell.app.aboutball.venue.presenter.BaseOrderPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 18:11
 * 更新时间: 2019/5/14 18:11
 * 描述:订单完成和取消P
 */
public class OrderCompleteEvaluateCancelPresenter extends BaseOrderPresenter<OrderCompleteEvaluateCancelContract.View,
        OrderCompleteEvaluateCancelModel>implements OrderCompleteEvaluateCancelContract.Presenter{
    public OrderCompleteEvaluateCancelPresenter(@NonNull OrderCompleteEvaluateCancelContract.View view) {
        super(view);
    }

    @Override
    protected OrderCompleteEvaluateCancelModel createModel() {
        return new OrderCompleteEvaluateCancelModel();
    }

    @Override
    public void start() {

    }
}
