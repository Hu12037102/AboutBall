package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderEvaluateContract;
import com.work.guaishouxingqiu.aboutball.my.model.OrderEvaluateModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 13:10
 * 更新时间: 2019/5/15 13:10
 * 描述:订单评价P
 */
public class OrderEvaluatePresenter extends BasePresenter<OrderEvaluateContract.View, OrderEvaluateModel> implements
        OrderEvaluateContract.Presenter {
    public OrderEvaluatePresenter(@NonNull OrderEvaluateContract.View view) {
        super(view);
    }

    @Override
    protected OrderEvaluateModel createModel() {
        return new OrderEvaluateModel();
    }

    @Override
    public void start() {

    }
}
