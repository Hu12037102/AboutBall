package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.ResultMyAboutBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 18:01
 * 更新时间: 2019/5/22 18:01
 * 描述:我的约球fragment model
 */
public class MyAboutBallChildModel extends BaseModel{
    public void loadData(int flag, BaseObserver<List<ResultMyAboutBean>>observer) {
        mRetrofitManger.create(VenueService.class)
                .getMyAboutBean(flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
