package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyTicketsBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:36
 * 更新时间: 2019/9/4 17:36
 * 描述:我的契约model
 */
public class MyTicketsChildModel extends BaseModel {
    public void loadMyTicketsList(int pageNum, int pageSize, int exchange, BaseObserver<List<ResultMyTicketsBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyTicketsList(pageNum,pageSize,exchange)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
