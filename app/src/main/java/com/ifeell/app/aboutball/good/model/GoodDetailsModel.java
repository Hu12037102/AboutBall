package com.ifeell.app.aboutball.good.model;

import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.bean.RequestSureOrderBean;
import com.ifeell.app.aboutball.good.GoodService;
import com.ifeell.app.aboutball.good.bean.ResultOrderDetailsBean;
import com.ifeell.app.aboutball.venue.model.BaseOrderModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:03
 * 更新时间: 2019/9/12 14:03
 * 描述:订单详情model
 */
public class GoodDetailsModel extends BaseOrderModel {
    public void loadOrderDetails(RequestSureOrderBean bean, BaseObserver<ResultOrderDetailsBean> observer) {
        mRetrofitManger.create(GoodService.class)
                .loadOrderDetails(bean.spuId, bean.params, bean.num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void loadGoodDetails(long orderId, BaseObserver<ResultOrderDetailsBean> observer) {
        mRetrofitManger.create(GoodService.class)
                .loadGoodDetails(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
