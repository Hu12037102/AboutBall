package com.ifeell.app.aboutball.good.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.good.GoodService;
import com.ifeell.app.aboutball.good.bean.ResultMyGoodBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 14:57
 * 更新时间: 2019/9/19 14:57
 * 描述:我的订单model
 */
public class MyGoodModel extends BaseModel {
    public void loadMyGoodList(int pageNum, int pageSize, int status, BaseObserver<List<ResultMyGoodBean>> observer) {
        mRetrofitManger.create(GoodService.class)
                .loadMyGoodList(pageNum, pageSize, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
