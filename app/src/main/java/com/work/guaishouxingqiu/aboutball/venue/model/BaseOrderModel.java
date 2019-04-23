package com.work.guaishouxingqiu.aboutball.venue.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.venue.VenueService;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;

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
}
