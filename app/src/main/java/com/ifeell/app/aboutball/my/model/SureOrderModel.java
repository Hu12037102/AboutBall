package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.good.GoodService;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultConfirmOrderBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/11 10:19
 * 更新时间: 2019/9/11 10:19
 * 描述:确认订单model
 */
public class SureOrderModel extends BaseModel {
    public void loadConfirmOrder(long spuId, String params, int num, BaseObserver<ResultConfirmOrderBean> observer){
        mRetrofitManger.create(MyService.class)
                .getUserConfirmOrder(spuId,params,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void checkOutGoodStatus(long spuId, String params, int num,BaseObserver<String> observer){
        mRetrofitManger.create(GoodService.class)
                .checkOutGoodStatus(spuId,params,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
