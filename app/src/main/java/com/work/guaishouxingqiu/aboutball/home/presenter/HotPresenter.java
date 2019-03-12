package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.HotContract;
import com.work.guaishouxingqiu.aboutball.home.model.HotModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:42
 * 更新时间: 2019/3/12 17:42
 * 描述: 热点P
 */
public class HotPresenter extends BasePresenter<HotContract.View,HotModel> implements HotContract.Presenter{
    public HotPresenter(@NonNull HotContract.View view) {
        super(view);
    }

    @Override
    protected HotModel createModel() {
        return new HotModel();
    }

    @Override
    public void start() {

    }
}
