package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.model.LoginOrShareModel;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.ResultAboutBallDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 15:26
 * 更新时间: 2019/5/16 15:26
 * 描述:约球详情model
 */
public class AboutBallDetailsModel extends LoginOrShareModel {
    public void loadDetails(long offerId, BaseObserver<ResultAboutBallDetailsBean> observer) {
        mRetrofitManger.create(VenueService.class)
                .loadAboutBallDetails(offerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void playReferee(long offerId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(VenueService.class)
                .playReferee(offerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void cancelAboutBall(long agreeId,BaseObserver<String> observer) {
        mRetrofitManger.create(VenueService.class)
                .cancelAboutBall(agreeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
