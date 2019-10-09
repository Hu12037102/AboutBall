package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.venue.bean.ResultVenueDetailsBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/27 13:18
 * 更新时间: 2019/3/27 13:18
 * 描述:场馆详情Model
 */
public class VenueDetailsModel extends BaseModel {
    public void loadDetails(long stadiumId, BaseObserver<ResultVenueDetailsBean> observer) {
        mRetrofitManger.create(VenueService.class)
                .getVenueDetails(stadiumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadVenueData(RequestVenueListBean bean, BaseObserver<List<ResultVenueData>> observer) {
        mRetrofitManger.create(VenueService.class)
                .getVenueRecommendList(bean.stadiumId,bean.longitude,bean.latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
