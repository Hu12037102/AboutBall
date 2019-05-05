package com.work.guaishouxingqiu.aboutball.venue.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.venue.VenueService;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 9:30
 * 更新时间: 2019/4/24 9:30
 * 描述:选择球队model
 */
public class SelectorBallTeamModel extends BaseModel{
    public void loadMyBallTeam(BaseObserver<List<ResultMyBallTeamBean>> observer) {
        mRetrofitManger.create(VenueService.class)
                .loadMyBallTeam()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
