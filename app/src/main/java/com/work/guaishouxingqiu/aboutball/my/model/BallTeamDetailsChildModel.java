package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 16:28
 * 更新时间: 2019/4/25 16:28
 * 描述:
 */
public class BallTeamDetailsChildModel extends BaseModel{
    public void loadDetails(long teamId, BaseObserver<ResultBallDetailsBean> observer) {
        mRetrofitManger.create(MyService.class)
                .loadBallTeamDetails(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}