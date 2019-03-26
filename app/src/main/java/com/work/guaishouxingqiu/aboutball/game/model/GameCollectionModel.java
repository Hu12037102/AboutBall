package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCollectionBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 10:14
 * 更新时间: 2019/3/25 10:14
 * 描述:比赛-集锦Model
 */
public class GameCollectionModel extends BaseModel{
    public void loadData(int gameId, BaseObserver<List<ResultGameCollectionBean>> observer){
        mRetrofitManger.create(GameService.class)
                .loadCollectionList(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
