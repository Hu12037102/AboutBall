package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.PUT;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:10
 * 更新时间: 2019/3/15 10:10
 * 描述:
 */
public class GameBaseModel extends BaseModel {
    public void loadGameData(int gameType, BaseObserver<List<ResultGameBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameData(gameType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 赛事信息下拉刷新，返回已结束
     */
    public void loadGameRefreshData(int gameType, String date, BaseObserver<List<ResultGameBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameRefreshData(gameType,date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 赛事信息上拉加载，未开始
     */
    public void loadGameMoreData(int gameType, String date, BaseObserver<List<ResultGameBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameMoreData(gameType,date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
