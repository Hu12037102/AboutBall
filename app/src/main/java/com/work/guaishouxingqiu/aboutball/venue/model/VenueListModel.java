package com.work.guaishouxingqiu.aboutball.venue.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.venue.VenueService;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueListBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;

import java.util.List;

import io.reactivex.Observable;
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
