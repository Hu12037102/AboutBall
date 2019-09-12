package com.work.guaishouxingqiu.aboutball.order.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.order.OrderService;
import com.work.guaishouxingqiu.aboutball.order.bean.ResultOrderDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:03
 * 更新时间: 2019/9/12 14:03
 * 描述:订单详情model
 */
public class OrderDetailsModel extends BaseModel {
    public void loadOrderDetails(RequestSureOrderBean bean, BaseObserver<ResultOrderDetailsBean> observer) {
        mRetrofitManger.create(OrderService.class)
                .loadOrderDetails(bean.spuId, bean.params, bean.num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
