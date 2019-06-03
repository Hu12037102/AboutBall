package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.commonality.model.LoginOrShareModel;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 14:55
 * 更新时间: 2019/3/22 14:55
 * 描述:比赛详情model
 */
public class GameDetailsModel extends LoginOrShareModel {
    public void loadMatchSimple(int matchId, BaseObserver<ResultGameSimpleBean> observer){
        mRetrofitManger.create(GameService.class)
                .loadGameSimple(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
