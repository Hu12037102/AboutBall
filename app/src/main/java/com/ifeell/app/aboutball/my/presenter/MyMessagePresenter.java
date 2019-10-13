package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.contract.MyMessageContract;
import com.ifeell.app.aboutball.my.model.MyMessageModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/31 15:00
 * 更新时间: 2019/7/31 15:00
 * 描述:我的消息P
 */
public class MyMessagePresenter extends BasePresenter<MyMessageContract.View, MyMessageModel>
implements MyMessageContract.Presenter{
    public MyMessagePresenter(@NonNull MyMessageContract.View view) {
        super(view);
    }

    @Override
    protected MyMessageModel createModel() {
        return new MyMessageModel();
    }

    @Override
    public void start() {

    }
}
