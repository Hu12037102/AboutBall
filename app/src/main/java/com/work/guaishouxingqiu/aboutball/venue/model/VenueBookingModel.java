package com.work.guaishouxingqiu.aboutball.venue.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.venue.VenueService;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueOrderBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueBookBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/18 13:55
 * 更新时间: 2019/4/18 13:55
 * 描述:
 */
public class VenueBookingModel extends BaseModel {
    public void loadBookList(int areaId, String date, BaseObserver<List<ResultVenueBookBean>> observer) {
        mRetrofitManger.create(VenueService.class)
                .getVenueBookList(areaId, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadWaitBookList(int areaId, String date, BaseObserver<List<ResultVenueBookBean>> observer) {
        mRetrofitManger.create(VenueService.class)
                .getVenueWaitBookList(areaId, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void createOrder(RequestVenueOrderBean bean, BaseObserver<BaseDataBean<Long>> observer) {
        mRetrofitManger.create(VenueService.class)
                .createOrderId(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
