package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultPrizeBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 17:01
 * 更新时间: 2019/4/11 17:01
 * 描述:基类兑换奖品
 */
public class BasePrizeModel extends BaseModel {
    public void loadMyPrize(int status, BaseObserver<ResultPrizeBean> observer) {
        mRetrofitManger.create(MyService.class)
                .getMyPrize(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void hasAddress(BaseObserver<String> observer) {
        mRetrofitManger.create(MyService.class)
                .hasAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
