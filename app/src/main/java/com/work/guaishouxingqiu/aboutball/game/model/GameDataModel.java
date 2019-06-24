package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataInfoBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDetailsBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:41
 * 更新时间: 2019/3/25 9:41
 * 描述:比赛数据Model
 */
public class GameDataModel extends BaseModel{
    public void loadGameHeadDetails(int gameId, BaseObserver<ResultGameDataInfoBean> observer){
        mRetrofitManger.create(GameService.class)
                .loadGameHeadDetails(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadGameResultDetails(int gameId, BaseObserver<List<ResultGameDataResultBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameDataResult(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
