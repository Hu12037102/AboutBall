package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
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
    public void loadGameDetails(int gameId, BaseObserver<ResultGameDetailsBean> observer){
        mRetrofitManger.create(GameService.class)
                .loadGameDetails(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
