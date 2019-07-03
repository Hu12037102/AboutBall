package com.work.guaishouxingqiu.aboutball.venue.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.commonality.model.LoginOrShareModel;
import com.work.guaishouxingqiu.aboutball.venue.VenueService;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallDetailsBean;

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

    public void cancelAboutBall(long agreeId,BaseObserver<Long> observer) {
        mRetrofitManger.create(VenueService.class)
                .cancelAboutBall(agreeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
