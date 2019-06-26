package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameLiveDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 11:04
 * 更新时间: 2019/6/24 11:04
 * 描述:比赛-回顾-model
 */
public class GameLookBackModel extends BaseModel{
    public void loadLiveDetails(int matchId, BaseObserver< ResultGameLiveDetailsBean> observer) {
        mRetrofitManger.create(GameService.class)
                .loadLookBackDetails(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
