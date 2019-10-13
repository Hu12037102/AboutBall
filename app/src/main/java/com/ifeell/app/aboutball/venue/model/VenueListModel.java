package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:41
 * 更新时间: 2019/3/18 10:41
 * 描述:场馆列表
 */
public class VenueListModel extends BaseModel {
    public void loadBallListType(BaseObserver<List<ResultTypeBean>> observable) {
        mRetrofitManger.create(VenueService.class)
                .getBallTypeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);
    }
    public void loadVenueList(int pageNum, int pageSize, RequestVenueListBean bean, BaseObserver<List<ResultVenueData>> observer) {
        mRetrofitManger.create(VenueService.class)
                .getVenueList(pageNum, pageSize, bean.typeId, bean.longitude, bean.latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
