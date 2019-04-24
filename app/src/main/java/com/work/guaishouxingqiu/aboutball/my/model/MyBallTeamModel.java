package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 9:45
 * 更新时间: 2019/4/24 9:45
 * 描述:我的球队model
 */
public class MyBallTeamModel extends BaseModel {
    public void loadMyBallTeam(BaseObserver<List<ResultMyBallBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyBallTeam()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
