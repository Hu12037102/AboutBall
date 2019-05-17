package com.work.guaishouxingqiu.aboutball.venue.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/17 9:52
 * 更新时间: 2019/5/17 9:52
 * 描述:
 */
public class BallTeamDetailsModel extends BaseModel{
    public void loadDetails(long teamId, BaseObserver<ResultBallDetailsBean> observer) {
        mRetrofitManger.create(MyService.class)
                .loadBallTeamDetails(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
