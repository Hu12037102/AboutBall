package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.MyOrderContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyOrderModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 9:58
 * 更新时间: 2019/5/14 9:58
 * 描述:
 */
public class MyOrderPresenter extends BasePresenter<MyOrderContract.View, MyOrderModel> implements
        MyOrderContract.Presenter {
    public MyOrderPresenter(@NonNull MyOrderContract.View view) {
        super(view);
    }

    @Override
    protected MyOrderModel createModel() {
        return new MyOrderModel();
    }

    @Override
    public void start() {

    }
}
