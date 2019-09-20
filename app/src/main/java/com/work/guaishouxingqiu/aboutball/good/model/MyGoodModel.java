package com.work.guaishouxingqiu.aboutball.good.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.good.GoodService;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultMyGoodBean;

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
