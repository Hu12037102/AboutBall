package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestRefundBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/23 13:42
 * 更新时间: 2019/5/23 13:42
 * 描述:申请退款model
 */
public class RefundModel extends BaseModel {
    public void refundOrder(RequestRefundBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .refundOrder(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
