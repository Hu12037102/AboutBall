package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.ResultOrderDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 16:33
 * 更新时间: 2019/4/22 16:33
 * 描述:订单基类Model
 */
public class BaseOrderModel extends BaseModel{
    public void loadOrderDetails(long orderId, BaseObserver<ResultOrderDetailsBean> observer){
        mRetrofitManger.create(VenueService.class)
                .getOrderDetails(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void bandPhoneNumber(long orderId, String phoneNumber, BaseObserver<BaseDataBean<String>> observer){
        mRetrofitManger.create(VenueService.class)
                .bandOrderPhoneNumber(orderId,phoneNumber)
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
