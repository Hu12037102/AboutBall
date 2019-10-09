package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultMyOrderBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 10:24
 * 更新时间: 2019/5/14 10:24
 * 描述:我的订单fragmentModel
 */
public class MyOrderFragmentModel extends BaseModel {

    public void lordMyOrder(int orderStatus, int pageNum, int pageSize, BaseObserver<List<ResultMyOrderBean>>observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyOrder(orderStatus,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
    public void cancelOrder(long orderId, BaseObserver<BaseDataBean<String>>observer) {
        mRetrofitManger.create(MyService.class)
                .cancelOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
