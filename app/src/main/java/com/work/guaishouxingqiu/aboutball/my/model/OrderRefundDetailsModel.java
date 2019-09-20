package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/24 10:16
 * 更新时间: 2019/5/24 10:16
 * 描述:退款进度详情model
 */
public class OrderRefundDetailsModel extends BaseModel{

    public void checkRefundDetails(long orderId, BaseObserver<ResultRefundDetailsBean> observer) {
        mRetrofitManger.create(MyService.class)
                .getCheckRefund(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
