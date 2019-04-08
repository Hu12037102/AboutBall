package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.MyPrizeContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyPrizeModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 18:25
 * 更新时间: 2019/4/8 18:25
 * 描述:我的奖品P
 */
public class MyPrizePresenter extends BasePresenter<MyPrizeContract.View,MyPrizeModel>
implements MyPrizeContract.Presenter{
    public MyPrizePresenter(@NonNull MyPrizeContract.View view) {
        super(view);
    }

    @Override
    protected MyPrizeModel createModel() {
        return new MyPrizeModel();
    }

    @Override
    public void start() {

    }
}
