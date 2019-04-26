package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 15:43
 * 更新时间: 2019/4/25 15:43
 * 描述:球队信息model
 */
public class BallTeamDetailsModel extends BaseModel{
    public void exitBallTeam(Long teamId, Long playerId, BaseObserver<BaseDataBean<String> > observer) {
        mRetrofitManger.create(MyService.class)
                .exitBallTeam(teamId,playerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
