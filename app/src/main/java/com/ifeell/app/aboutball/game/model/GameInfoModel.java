package com.ifeell.app.aboutball.game.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.game.GameService;
import com.ifeell.app.aboutball.game.bean.ResultGameFiltrateBean;
import com.ifeell.app.aboutball.game.bean.ResultGameGroupBean;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoOtherBean;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoScoreboardBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/25 11:31
 * 更新时间: 2019/6/25 11:31
 * 描述:比赛数据model
 */
public class GameInfoModel extends BaseModel {
    public void loadMatchFiltrateData(BaseObserver<List<ResultGameFiltrateBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameInfoFiltrateData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void loadMatchGroupData(long gameId, BaseObserver<List<ResultGameGroupBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadMatchGroupData(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadMatchScoreboardData(long requestGameId, Long requestGroupId, BaseObserver<List<ResultGameInfoScoreboardBean>>observer) {
        mRetrofitManger.create(GameService.class)
                .loadMatchScoreboardData(requestGameId,requestGroupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadOtherData(long requestGameId, int requestAction, BaseObserver<List<ResultGameInfoOtherBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameInfoOtherData(requestGameId,requestAction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
