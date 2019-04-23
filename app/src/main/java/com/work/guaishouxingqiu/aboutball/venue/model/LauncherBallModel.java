package com.work.guaishouxingqiu.aboutball.venue.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.venue.VenueService;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 14:17
 * 更新时间: 2019/4/23 14:17
 * 描述:发起约球model
 */
public class LauncherBallModel extends BaseModel {

    public void loadRefereeList(BaseObserver<List<ResultRefereeBean>> observer) {
        mRetrofitManger.create(VenueService.class)
                .loadRefereeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
