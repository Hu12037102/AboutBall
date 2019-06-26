package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:33
 * 更新时间: 2019/3/25 9:33
 * 描述:赛况model
 */
public class MatchResultModel extends BaseModel {
    public void loadData(int gameId, int pageNum, int pageSize, BaseObserver<ResultGameDataBean> observer){
        mRetrofitManger.create(GameService.class)
                .loadGameResultData(gameId,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
