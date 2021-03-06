package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.ResultAboutBallBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:50
 * 更新时间: 2019/3/18 10:50
 * 描述:场馆-约球Model
 */
public class AboutBallModel extends BaseModel {
    public void getAboutList(int pageNum, int pageSize, BaseObserver<List<ResultAboutBallBean>> observer) {
        mRetrofitManger.create(VenueService.class)
                .getAboutBallList(pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
