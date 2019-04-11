package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;

import java.util.List;

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
}
