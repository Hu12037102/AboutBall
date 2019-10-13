package com.ifeell.app.aboutball.game.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.game.GameService;
import com.ifeell.app.aboutball.game.bean.ResultGameDataInfoBean;

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


}
