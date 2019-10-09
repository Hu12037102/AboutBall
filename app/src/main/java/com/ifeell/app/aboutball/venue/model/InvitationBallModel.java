package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.RequestInvitationBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/21 13:24
 * 更新时间: 2019/5/21 13:24
 * 描述:应邀约球model
 */
public class InvitationBallModel extends BaseModel {
    public void createOrder(RequestVenueOrderBean bean, BaseObserver<BaseDataBean<Long>> observer) {
        mRetrofitManger.create(VenueService.class)
                .createOrderId(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void invitationBall(RequestInvitationBallBean requestBean, BaseObserver<BaseDataBean<Long>> observer) {
        mRetrofitManger.create(VenueService.class)
                .invitationBall(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
