package com.ifeell.app.aboutball.home.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.home.HomeService;
import com.ifeell.app.aboutball.home.bean.ResultTicketMallBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/30 9:38
 * 更新时间: 2019/8/30 9:38
 * 描述:售票商城fragmentModel
 */
public class TicketMallChildModel extends BaseModel {
    public void loadTickMallList(int statusId, BaseObserver<List<ResultTicketMallBean>> observer) {
        mRetrofitManger.create(HomeService.class)
                .getTicketMallList(statusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
