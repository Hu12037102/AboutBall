package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.MyTicketsChildContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyTicketsChildModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:37
 * 更新时间: 2019/9/4 17:37
 * 描述:我的契约P
 */
public class MyTicketsChildPresenter extends BasePresenter<MyTicketsChildContract.View, MyTicketsChildModel>
        implements MyTicketsChildContract.Presenter {
    public MyTicketsChildPresenter(@NonNull MyTicketsChildContract.View view) {
        super(view);
    }

    @Override
    protected MyTicketsChildModel createModel() {
        return new MyTicketsChildModel();
    }

    @Override
    public void start() {

    }
}
