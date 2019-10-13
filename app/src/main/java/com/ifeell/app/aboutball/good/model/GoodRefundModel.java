package com.ifeell.app.aboutball.good.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.good.GoodService;
import com.ifeell.app.aboutball.good.bean.ResultGoodRefundDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/20 10:53
 * 更新时间: 2019/9/20 10:53
 * 描述: 申请退款model
 */
public class GoodRefundModel extends BaseModel {
    public void loadRefundDetail(long orderId, BaseObserver<ResultGoodRefundDetailsBean> observer) {
        mRetrofitManger.create(GoodService.class)
                .loadGoodRefundDetail(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void applyRefund(long orderId, String reason,BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(GoodService.class)
                .applyGoodRefund(orderId,reason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
