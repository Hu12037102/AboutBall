package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.MyTicketsContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyTicketsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:10
 * 更新时间: 2019/9/4 17:10
 * 描述:我的契约P
 */
public class MyTicketsPresenter extends BasePresenter<MyTicketsContract.View, MyTicketsModel> implements MyTicketsContract.Presenter {
    public MyTicketsPresenter(@NonNull MyTicketsContract.View view) {
        super(view);
    }

    @Override
    protected MyTicketsModel createModel() {
        return new MyTicketsModel();
    }

    @Override
    public void start() {

    }
}
