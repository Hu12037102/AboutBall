package com.work.guaishouxingqiu.aboutball.good.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.good.GoodService;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultGoodRefundDetailsBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
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
