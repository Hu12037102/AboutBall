package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.HomeContract;
import com.work.guaishouxingqiu.aboutball.home.model.HomeModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:01
 * 更新时间: 2019/3/6 16:01
 * 描述:首页P
 */
public class HomePresenter extends BasePresenter<HomeContract.View,HomeModel> implements HomeContract.Presenter {
    public HomePresenter(@NonNull HomeContract.View view) {
        super(view);
    }

    @Override
    protected HomeModel createModel() {
        return new HomeModel();
    }

    @Override
    public void start() {

    }
}
