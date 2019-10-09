package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultRefundDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/24 10:16
 * 更新时间: 2019/5/24 10:16
 * 描述:退款进度详情model
 */
public class OrderRefundDetailsModel extends BaseModel{

    public void checkGoodRefundDetails(long orderId, BaseObserver<ResultRefundDetailsBean> observer) {
        mRetrofitManger.create(MyService.class)
                .getGoodCheckRefund(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void checkOrderRefundDetails(long orderId, BaseObserver<ResultRefundDetailsBean> observer) {
        mRetrofitManger.create(MyService.class)
                .getOrderCheckRefund(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
